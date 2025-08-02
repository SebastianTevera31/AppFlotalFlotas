package com.rfz.appflotalflotas.data.model.assembly

import com.google.gson.annotations.SerializedName

data class MonitorDataRequest(
    @SerializedName("fld_frame") val fldFrame: String,
    @SerializedName("id_vehicle_fk_1") val idVehicle: Int,
    @SerializedName("language") val language: String,
    @SerializedName("id_fleet") val idFleet: Int,
    @SerializedName("fld_dateData") val fldDateData: String,
)

data class MonitorDataResponse(
    val verrorNumber: Int,
    val vresult: String
)

data class MonitorDataByVehicleResponse(
    @SerializedName("fld_lowBits") val fldLowBits: String,
    @SerializedName("fld_plates") val fldPlates: String,
    @SerializedName("fld_dateData") val fldDateData: String,
    @SerializedName("fld_temperature") val fldTemperture: Int,
    @SerializedName("fld_sensorId") val fldSensorId: String,
    @SerializedName("fld_position") val fldPosition: String,
    @SerializedName("fld_psi") val fldPsi: Float,
)