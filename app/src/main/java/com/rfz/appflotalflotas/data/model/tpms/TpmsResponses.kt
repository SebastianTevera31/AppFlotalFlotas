package com.rfz.appflotalflotas.data.model.tpms

import com.google.gson.annotations.SerializedName

data class MonitorDataResponse(
    val verrorNumber: Int,
    val vresult: String
)

data class ConfigurationByIdMonitorResponse(
    @SerializedName("id_configuration") val idConfiguration: Int,
    @SerializedName("fld_description") val fldDescription: String,
    @SerializedName("fld_UrlImage") val fldUrlImage: String,
    @SerializedName("fld_svg") val fldSvg: String,
)

data class MonitorDataByIdResponse(
    @SerializedName("fld_lowBits") val fldLowBits: String,
    @SerializedName("fld_plates") val fldPlates: String,
    @SerializedName("fld_dateData") val fldDateData: String,
    @SerializedName("fld_temperature") val fldTemperture: Int,
    @SerializedName("fld_sensorId") val fldSensorId: String,
    @SerializedName("fld_position") val fldPosition: String,
    @SerializedName("fld_psi") val fldPsi: Float,
)

data class DiagramMonitorResponse(
    @SerializedName("id_diagramMonitor") val idDiagramaMonitor: Int,
    @SerializedName("monitor_id") val monitorId: Int,
    @SerializedName("fld_mac") val fldMac: String,
    @SerializedName("c_axis_fk_2") val axis: Int,
    @SerializedName("axis_description") val axisDescription: String,
    @SerializedName("sensor_position") val sensorPosition: Int,
    @SerializedName("position_description") val positionDescription: String,
    @SerializedName("sensor_id") val sensorId: Int,
    @SerializedName("sensor_name") val sensorName: String,
    @SerializedName("configuration_id") val confId: Int,
    @SerializedName("configuration_description") val confDescription: String,
    @SerializedName("PSI") val psi: Float,
    @SerializedName("temperature") val temperature: Float,
    @SerializedName("ultimalectura") val ultimaLectura: String,
    @SerializedName("fld_highPressure") val fldHighPressure: Boolean,
    @SerializedName("fld_highTemperature") val flHighTemperature: Boolean,
    @SerializedName("fld_lowPressure") val fldLowPressure: Boolean,
)