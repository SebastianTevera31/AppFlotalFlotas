package com.rfz.appflotalflotas.data.model.assembly

import com.google.gson.annotations.SerializedName

data class MonitorDataResponse(
    @SerializedName("fld_frame") val fldFrame: String,
    @SerializedName("id_vehicle_fk_1") val idVehicle: Int,
    @SerializedName("language") val language: String,
    @SerializedName("id_fleet") val idFleet: Int,
    @SerializedName("fld_dateData") val fldDateData: String,
)