package com.rfz.appflotalflotas.domain.tpms

import com.rfz.appflotalflotas.data.repository.tpms.AssemblyRepository
import javax.inject.Inject

class TpmsUseCase @Inject constructor(private val assemblyRepository: AssemblyRepository) {

    suspend fun doGetConfigurationByIdMonitor(idMonitor: Int) {
        assemblyRepository.doGetConfigurationByIdMonitor(idMonitor)
    }

    suspend fun doGetDataMonitorById(
        idMonitor: Int,
        fldDate: String
    ) {
        assemblyRepository.doGetDataMonitorById(idMonitor, fldDate)
    }

    suspend fun doGetDiagramMonitor(idMonitor: Int) {
        assemblyRepository.doGetDiagramMonitor(idMonitor)
    }

    suspend fun doSendMonitorData(
        fldFrame: String,
        idMonitor: Int,
        language: String,
        idFleet: Int,
        fldDate: String
    ) {
        assemblyRepository.doSendMonitorData(
            fldFrame = fldFrame,
            idMonitor = idMonitor,
            language = language,
            idFleet = idFleet,
            fldDate = fldDate
        )
    }
}