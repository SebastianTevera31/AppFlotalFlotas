package com.rfz.appflotalflotas.data.repository.assembly

import com.rfz.appflotalflotas.data.model.assembly.MonitorDataRequest
import com.rfz.appflotalflotas.data.network.service.assembly.AssemblyService
import javax.inject.Inject

class AssemblyRepository @Inject constructor(private val assemblyService: AssemblyService) {
    suspend fun doGetDataMonitorByIdVehicle(
        idVehicle: Int,
        fldDate: String
    ) {
        assemblyService.doGetDataMonitorByIdVehicle(idVehicle, fldDate)
    }

    suspend fun doSendMonitorData(
        fldFrame: String,
        idVehicle: Int,
        language: String,
        idFleet: Int,
        fldDate: String
    ) {
        assemblyService.doSendMonitorData(
            MonitorDataRequest(
                fldFrame = fldFrame,
                idVehicle = idVehicle,
                language = language,
                idFleet = idFleet,
                fldDateData = fldDate
            )
        )
    }
}