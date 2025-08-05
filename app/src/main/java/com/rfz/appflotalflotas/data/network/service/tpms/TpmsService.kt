package com.rfz.appflotalflotas.data.network.service.tpms

import com.rfz.appflotalflotas.data.model.tpms.MonitorDataRequest
import com.rfz.appflotalflotas.data.network.client.tpms.TpmsClient
import com.rfz.appflotalflotas.data.network.requestHelper
import javax.inject.Inject

class TpmsService @Inject constructor(private val assemblyClient: TpmsClient) {

    suspend fun doGetConfigurationByIdMonitor(idMonitor: Int) {
        requestHelper(endpointName = "GetConfigurationByIdMonitor") {
            assemblyClient.getConfigurationByIdMonitor(idMonitor)
        }
    }

    suspend fun doGetDataMonitorById(idMonitor: Int, fldDate: String) {
        requestHelper(endpointName = "GetDataMonitorById") {
            assemblyClient.getDataMonitorById(idMonitor, fldDate)
        }
    }

    suspend fun doGetDiagramMonitor(idMonitor: Int) {
        requestHelper(endpointName = "GetDiagramMonitor") {
            assemblyClient.getDiagramMonitor(idMonitor)
        }
    }

    suspend fun doSendMonitorData(monitorDataResponse: MonitorDataRequest) {
        requestHelper(endpointName = "SendDataMonitor") {
            assemblyClient.sendDataMonitor(
                monitorDataResponse
            )
        }
    }
}