package com.rfz.appflotalflotas.data.network.service.assembly

import android.util.Log
import com.rfz.appflotalflotas.data.model.assembly.MonitorDataRequest
import com.rfz.appflotalflotas.data.network.client.assembly.AssemblyClient
import javax.inject.Inject

class AssemblyService @Inject constructor(private val assemblyClient: AssemblyClient) {
    suspend fun doGetDataMonitorByIdVehicle(idVehicle: Int, fldDate: String) {
        try {
            val response = assemblyClient.getDataMonitorByIdVehicle(idVehicle, fldDate)
            Log.d("AssemblyService", "${response.code()}")
            if (response.isSuccessful) {
                Log.d("AssemblyService - DataMonitorByIdVehicle", response.message())
                Log.d("AssembyService", "${response.body()}")
            }
        } catch (e: Exception) {
            Log.e("AssemblyService", "${e.message}")
        }
    }

    suspend fun doSendMonitorData(monitorDataResponse: MonitorDataRequest) {
        try {
            val response = assemblyClient.sendDataMonitor(monitorDataResponse)
            if (response.isSuccessful) {
                Log.d("AssemblyService - MonitorData", response.message())
                Log.d("AssembyService", "${response.body()}")
            }
        } catch (e: Exception) {
            Log.e("AssemblyService", "${e.message}")
        }
    }
}