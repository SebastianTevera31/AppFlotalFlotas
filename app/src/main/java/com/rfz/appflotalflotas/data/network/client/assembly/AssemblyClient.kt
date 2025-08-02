package com.rfz.appflotalflotas.data.network.client.assembly

import com.rfz.appflotalflotas.data.model.assembly.MonitorDataByVehicleResponse
import com.rfz.appflotalflotas.data.model.assembly.MonitorDataRequest
import com.rfz.appflotalflotas.data.model.assembly.MonitorDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AssemblyClient {

    @GET("api/Assembly/GetDataMonitorByIdVehicle")
    suspend fun getDataMonitorByIdVehicle(
        @Query("id_vehicle") idVehicle: Int,
        @Query("fld_date") fldDate: String
    ): Response<List<MonitorDataByVehicleResponse>>

    @POST("api/Assembly/MonitorData")
    suspend fun sendDataMonitor(@Body monitorData: MonitorDataRequest): Response<List<MonitorDataResponse>>
}