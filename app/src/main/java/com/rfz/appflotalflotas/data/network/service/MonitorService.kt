package com.rfz.appflotalflotas.data.network.service

import android.Manifest
import android.app.ForegroundServiceStartNotAllowedException
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat.startForeground
import androidx.core.content.PermissionChecker
import com.rfz.appflotalflotas.R
import com.rfz.appflotalflotas.core.util.getCurrentDate
import com.rfz.appflotalflotas.core.util.validateBluetoothConnectivity
import com.rfz.appflotalflotas.data.model.flotalSoft.SensorTpmsEntity
import com.rfz.appflotalflotas.data.repository.bluetooth.MonitorDataFrame
import com.rfz.appflotalflotas.data.repository.bluetooth.decodeDataFrame
import com.rfz.appflotalflotas.data.repository.wifi.NetworkStatus
import com.rfz.appflotalflotas.domain.assembly.AssemblyUseCase
import com.rfz.appflotalflotas.domain.bluetooth.BluetoothUseCase
import com.rfz.appflotalflotas.domain.database.SensorTableUseCase
import com.rfz.appflotalflotas.domain.wifi.WifiUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HombreCamionService : Service() {
    @Inject
    lateinit var bluetoothUseCase: BluetoothUseCase

    @Inject
    lateinit var assemblyUseCase: AssemblyUseCase

    @Inject
    lateinit var wifiUseCase: WifiUseCase

    @Inject
    lateinit var sensorTableUseCase: SensorTableUseCase
//    private lateinit var preferencesRepository: PreferencesRepository

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var oldestTimestamp: String? = null

    override fun onBind(p0: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        // Configurar e iniciar del servicio
        startForegroundService()

        // Habilitar observador WiFi
        wifiUseCase.doConnect()

        // Iniciar conexión Bluetooth
        initBluetoothConnection()

        readDataFromMonitor()

        // Reiniciando servicio
        Log.d("HombreCamionService", "Servicio iniciado")
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startForegroundService() {

        val bluetoothPermission =
            PermissionChecker.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
        if (bluetoothPermission != PermissionChecker.PERMISSION_GRANTED) {
            stopSelf()
            return
        }

        try {
            val notification = createNotification()

            if (Build.VERSION.SDK_INT >= 34) {
                startForeground(
                    /* service = */ this,
                    /* id = */ 100, // Cannot be 0
                    /* notification = */ notification,
                    /* foregroundServiceType = */
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC or ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
                )
            } else {
                startForeground(100, notification)
            }
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
                && e is ForegroundServiceStartNotAllowedException
            ) {
                Log.e("HombreCamionService", "${e.message}")
            }
        }
    }

    private fun createNotification(): Notification {
        val channelId = "bt_channel_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Bluetooth Service Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                setShowBadge(false)
            }

            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Servicio HombreCamion")
            .setContentText("Recibiendo datos del monitor")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private fun initBluetoothConnection() {
        coroutineScope.launch {
            Log.d("HombreCamionService", "Iniciando Bluetooth...")
            bluetoothUseCase.doConnect("80:F5:B5:70:5C:8F")
            bluetoothUseCase.doStartRssiMonitoring()
        }
    }

    private fun readDataFromMonitor() {
//        val currentUserId = preferencesRepository.userId.stateIn(
//            coroutineScope, started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = null
//        )

        coroutineScope.launch {
            bluetoothUseCase()
                .distinctUntilChangedBy { it.timestamp }
                .collect { data ->
                    val bluetoothSignalQuality = data.bluetoothSignalQuality

                    val dataFrame = data.dataFrame
                    Log.d("HombreCamionService", "Dataframe: $dataFrame, ")
                    if (validateBluetoothConnectivity(bluetoothSignalQuality) && dataFrame != null) {
                        val vehicleId = 48
                        if (vehicleId != null) {

                            Log.d("HombreCamionService", "UserId: $vehicleId")
                            val timestamp = getCurrentDate()

                            val sensorId = decodeDataFrame(dataFrame, MonitorDataFrame.SENSOR_ID)

                            sensorTableUseCase.doInsert(
                                SensorTpmsEntity(
                                    vehicleId = vehicleId,
                                    sensorId = sensorId,
                                    dataFrame = dataFrame,
                                    timestamp = timestamp,
                                    sent = false
                                )
                            )

                            // Enviar datos a API
                            sendDataToApi(dataFrame, timestamp, vehicleId)
                        }
                    }
                }
        }
    }

    private suspend fun sendDataToApi(dataFrame: String, timestamp: String, userId: Int) {
        val wifiStatus = wifiUseCase()
        Log.d("HombreCamionService", "WifiStatus: $wifiStatus")
        if (wifiStatus.value == NetworkStatus.Connected) {
            val localOldestTimestamp = oldestTimestamp
            if (localOldestTimestamp != null) {
                getUnsentRecords(userId)
                oldestTimestamp = null
            }

            assemblyUseCase.doSendMonitorData(
                fldFrame = dataFrame,
                idVehicle = 48,
                language = "es",
                idFleet = 24,
                fldDate = getCurrentDate()
            )

        } else if (wifiStatus.value == NetworkStatus.Disconnected && oldestTimestamp.isNullOrEmpty()) {
            oldestTimestamp = timestamp
        }
    }

    private suspend fun getUnsentRecords(userId: Int) {
        val records = sensorTableUseCase.doGetUnsentRecords(
            userId = userId,
        )

        records.forEach {
            if (it != null) {
//                assemblyUseCase.doSendMonitorData()
            }
        }
    }
}