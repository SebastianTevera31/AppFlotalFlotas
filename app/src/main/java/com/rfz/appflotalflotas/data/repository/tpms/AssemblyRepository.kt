package com.rfz.appflotalflotas.data.repository.tpms

import com.rfz.appflotalflotas.data.model.tpms.MonitorDataRequest
import com.rfz.appflotalflotas.data.network.service.tpms.TpmsService
import javax.inject.Inject

class AssemblyRepository @Inject constructor(private val assemblyService: TpmsService) {

    suspend fun doGetConfigurationByIdMonitor(idMonitor: Int) {
        assemblyService.doGetConfigurationByIdMonitor(idMonitor)
    }
    suspend fun doGetDataMonitorById(
        idMonitor: Int,
        fldDate: String
    ) {
        assemblyService.doGetDataMonitorById(idMonitor, fldDate)
    }

    suspend fun doGetDiagramMonitor(idMonitor: Int) {
        assemblyService.doGetDiagramMonitor(idMonitor)
    }

    suspend fun doSendMonitorData(
        fldFrame: String,
        idMonitor: Int,
        language: String,
        idFleet: Int,
        fldDate: String
    ) {
        assemblyService.doSendMonitorData(
            MonitorDataRequest(
                fldFrame = fldFrame,
                idMonitor = idMonitor,
                language = language,
                idFleet = idFleet,
                fldDateData = fldDate
            )
        )
    }
}