package com.rfz.appflotalflotas.core.util

import com.rfz.appflotalflotas.data.repository.bluetooth.BluetoothSignalQuality
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun validateBluetoothConnectivity(quality: BluetoothSignalQuality): Boolean {
    return quality in listOf(BluetoothSignalQuality.Excelente, BluetoothSignalQuality.Aceptable)
}


fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(Date())
}