package com.rfz.appflotalflotas.data.network.client.tpms

import com.rfz.appflotalflotas.data.model.tpms.ConfigurationByIdMonitorResponse
import com.rfz.appflotalflotas.data.model.tpms.DiagramMonitorResponse
import com.rfz.appflotalflotas.data.model.tpms.MonitorDataByIdResponse
import com.rfz.appflotalflotas.data.model.tpms.MonitorDataRequest
import com.rfz.appflotalflotas.data.model.tpms.MonitorDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TpmsClient {

    @GET("api/Tpms/ConfigurationByIdMonitor")
    suspend fun getConfigurationByIdMonitor(
        @Query("id_monitor") idMonitor: Int
    ): Response<List<ConfigurationByIdMonitorResponse>>

    @GET("api/Tpms/GetDataMonitorById")
    suspend fun getDataMonitorById(
        @Query("id_monitor") idMonitor: Int,
        @Query("fld_date") fldDate: String
    ): Response<List<MonitorDataByIdResponse>>

    @GET("api/Tpms/GetDiagramMonitor")
    suspend fun getDiagramMonitor(
        @Query("id_monitor") idMonitor: Int
    ): Response<List<DiagramMonitorResponse>>

    @POST("api/Tpms/MonitorData")
    suspend fun sendDataMonitor(@Body monitorData: MonitorDataRequest): Response<List<MonitorDataResponse>>
}