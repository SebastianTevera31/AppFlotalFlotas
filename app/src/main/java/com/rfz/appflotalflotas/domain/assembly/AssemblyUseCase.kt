package com.rfz.appflotalflotas.domain.assembly

import com.rfz.appflotalflotas.data.repository.assembly.AssemblyRepository
import javax.inject.Inject

class AssemblyUseCase @Inject constructor(private val assemblyRepository: AssemblyRepository) {
    suspend fun doGetDataMonitorByIdVehicle(
        idVehicle: Int,
        fldDate: String
    ) {
        assemblyRepository.doGetDataMonitorByIdVehicle(idVehicle, fldDate)
    }

    suspend fun doSendMonitorData(
        fldFrame: String,
        idVehicle: Int,
        language: String,
        idFleet: Int,
        fldDate: String
    ) {
        assemblyRepository.doSendMonitorData(
            fldFrame = fldFrame,
            idVehicle = idVehicle,
            language = language,
            idFleet = idFleet,
            fldDate = fldDate
        )
    }
}