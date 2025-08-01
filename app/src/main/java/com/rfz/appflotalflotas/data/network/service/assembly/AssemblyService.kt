package com.rfz.appflotalflotas.data.network.service.assembly

import android.util.Log
import com.rfz.appflotalflotas.data.model.assembly.MonitorDataResponse
import com.rfz.appflotalflotas.data.network.client.assembly.AssemblyClient
import javax.inject.Inject

class AssemblyService @Inject constructor(private val assemblyClient: AssemblyClient) {
    suspend fun doGetDataMonitorByIdVehicle(idVehicle: Int, fldDate: String) {
        try {
            assemblyClient.getDataMonitorByIdVehicle(idVehicle, fldDate)
        } catch (e: Exception) {
            Log.e("AssemblyService", "${e.message}")
        }
    }

    suspend fun doSendMonitorData(monitorDataResponse: MonitorDataResponse) {
        try {
            assemblyClient.sendDataMonitor(monitorDataResponse)
        } catch (e: Exception) {
            Log.e("AssemblyService", "${e.message}")
        }
    }
}