package com.rfz.appflotalflotas.presentation.ui.monitor.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.rfz.appflotalflotas.core.util.validateBluetoothConnectivity
import com.rfz.appflotalflotas.data.repository.bluetooth.BluetoothSignalQuality
import com.rfz.appflotalflotas.data.repository.bluetooth.MonitorDataFrame
import com.rfz.appflotalflotas.data.repository.bluetooth.SensorAlertDataFrame
import com.rfz.appflotalflotas.data.repository.bluetooth.decodeAlertDataFrame
import com.rfz.appflotalflotas.data.repository.bluetooth.decodeDataFrame
import com.rfz.appflotalflotas.presentation.theme.ProyectoFscSoftTheme
import com.rfz.appflotalflotas.presentation.ui.monitor.viewmodel.MonitorViewModel

@Composable
fun MonitorScreen(
    monitorViewModel: MonitorViewModel,
    modifier: Modifier = Modifier
) {
    val monitorUiState = monitorViewModel.monitorUiState.collectAsState()

    val sensorId = monitorUiState.value.sensorId

    if (sensorId != "N/A") {
        MonitorScreenView(
            wheel = monitorUiState.value.wheel,
            id = monitorUiState.value.sensorId,
            battery = monitorUiState.value.battery,
            pression = monitorUiState.value.pression.first,
            pressionStatus = monitorUiState.value.pression.second,
            temperature = monitorUiState.value.temperature.first,
            temperatureStatus = monitorUiState.value.temperature.second,
            qualityBluetooth = monitorUiState.value.signalIntensity.first,
            measuredBluetooth = monitorUiState.value.signalIntensity.second,
            timestamp = monitorViewModel.getCurrentRecordDate(),
            modifier = modifier.fillMaxSize()
        )
    } else {
        EmptyMonitorData(
            qualityBluetooth = monitorUiState.value.signalIntensity.first,
            measuredBluetooth = monitorUiState.value.signalIntensity.second,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun MonitorScreenView(
    wheel: String,
    id: String,
    battery: String,
    pression: String,
    pressionStatus: String,
    temperature: String,
    temperatureStatus: String,
    qualityBluetooth: BluetoothSignalQuality,
    measuredBluetooth: String,
    timestamp: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "Llanta: $wheel",
                fontWeight = FontWeight.Black,
                fontSize = 23.sp
            )
            HCDataText(
                modifier = Modifier
                    .border(
                        2.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(8.dp)
                    ),
                text = "SENSOR ID: $id",
            )

            HCDataText(
                modifier = Modifier
                    .border(
                        2.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(60.dp),
                text = "Bateria: $battery",
            )


            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                HCDataText(
                    modifier = Modifier
                        .border(
                            2.dp,
                            color = Color.Blue,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .weight(1f)
                        .height(60.dp),
                    text = "Presión:  $pressionStatus",
                )
                HCDataText(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    text = "$pression PSI",
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                HCDataText(
                    modifier = Modifier
                        .border(
                            2.dp,
                            color = Color.Blue,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .weight(1f)
                        .height(60.dp),
                    text = if (temperatureStatus != "Normal") "Temp.: $temperatureStatus" else "Temperatura",
                )
                HCDataText(
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    text = "$temperature °C",
                )
            }

            HCDataText(
                "Señal Bluetooth: $qualityBluetooth \n" +
                        measuredBluetooth,
                modifier = Modifier
                    .border(
                        2.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(80.dp),
            )

            // Timestamp proviene del registro de la base de datos
            if (!validateBluetoothConnectivity(qualityBluetooth) && timestamp != null
            ) {
                HCDataText(
                    text = "Ultimo registro sensado\n$timestamp", Modifier
                        .border(
                            2.dp,
                            color = Color.Blue,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun EmptyMonitorData(
    qualityBluetooth: BluetoothSignalQuality,
    measuredBluetooth: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HCDataText(
            text = "Sin datos", Modifier
                .height(250.dp)
                .border(
                    2.dp,
                    color = Color.Blue,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        HCDataText(
            "Señal Bluetooth: $qualityBluetooth \n" +
                    measuredBluetooth,
            modifier = Modifier
                .border(
                    2.dp,
                    color = Color.Blue,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(80.dp),
        )
    }
}

@Composable
fun HCDataText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}