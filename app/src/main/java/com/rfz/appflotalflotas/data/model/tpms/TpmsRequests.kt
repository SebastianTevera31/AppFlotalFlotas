package com.rfz.appflotalflotas.data.model.tpms

import com.google.gson.annotations.SerializedName

data class MonitorDataRequest(
    @SerializedName("fld_frame") val fldFrame: String,
    @SerializedName("id_monitor_fk_1") val idMonitor: Int,
    @SerializedName("language") val language: String,
    @SerializedName("id_fleet") val idFleet: Int,
    @SerializedName("fld_dateData") val fldDateData: String,
)