package com.rfz.appflotalflotas.domain.bluetooth

import com.rfz.appflotalflotas.data.repository.bluetooth.BluetoothData
import com.rfz.appflotalflotas.data.repository.bluetooth.BluetoothRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BluetoothUseCase @Inject constructor(private val bluetoothRepository: BluetoothRepository) {
    operator fun invoke(): StateFlow<BluetoothData> {
        return bluetoothRepository.sensorData
    }

    fun doConnect(mac: String = "80:F5:B5:70:5C:8F") = bluetoothRepository.connect(mac)

    suspend fun doStartRssiMonitoring() = bluetoothRepository.startRSSIMonitoring()
}