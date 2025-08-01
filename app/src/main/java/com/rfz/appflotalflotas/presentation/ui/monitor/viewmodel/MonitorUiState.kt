package com.rfz.appflotalflotas.presentation.ui.monitor.viewmodel

import com.rfz.appflotalflotas.data.repository.bluetooth.BluetoothSignalQuality

data class MonitorUiState(
    val sensorId: String = "N/A",
    val wheel: String = "N/A",
    val battery: String = "N/A",
    val pression: Pair<String, String> = Pair("N/A", "N/A"),
    val temperature: Pair<String, String> = Pair("N/A", "N/A"),
    val timestamp: String = "",
    val signalIntensity: Pair<BluetoothSignalQuality, String> = Pair(
        BluetoothSignalQuality.Desconocida,
        "N/A"
    )
)
