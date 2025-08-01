package com.rfz.appflotalflotas.data.model.login.response

import com.google.gson.annotations.SerializedName
import com.rfz.appflotalflotas.data.model.flotalSoft.AppFlotalEntity
import javax.inject.Inject
import javax.inject.Singleton


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val userData: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

data class LoginResponse(
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("fld_username") val fldName: String,
    @SerializedName("fld_fullName") val fldUsername: String,
    @SerializedName("fld_email") val fldEmail: String,
    @SerializedName("c_typeUser_fk_1") val fldToken: Int,
    @SerializedName("c_fleets") val cFleets: List<Fleets>,
    @SerializedName("token") val token: String,
    @SerializedName("success") val success: Int,
    var fecha: String? = null
)

data class Fleets(
    @SerializedName("c_fleet_fk_2") val cFleet: Int,
    @SerializedName("fld_description") val fldDescription: String
)

data class LoginSuccessResponse(
    @SerializedName("id_user") val idUser: Int,
    @SerializedName("fld_name") val name: String,
    @SerializedName("fld_username") val username: String,
    @SerializedName("fld_email") val email: String,
    @SerializedName("fld_token") val token: String,
    @SerializedName("id") val statusCode: Int,
    @SerializedName("fld_active") val isActive: Boolean
)

data class LoginErrorResponse(
    @SerializedName("mensaje") val message: ErrorMessage
)

data class ErrorMessage(
    @SerializedName("name") val errorName: String,
    @SerializedName("value") val errorValue: String,
    @SerializedName("resourceNotFound") val resourceNotFound: Boolean,
    @SerializedName("searchedLocation") val searchedLocation: String
)

@Singleton
class AppFlotalMapper @Inject constructor() {
    fun fromLoginResponseToEntity(response: LoginResponse): AppFlotalEntity {
        return AppFlotalEntity(
            id = 0,
            id_user = response.idUser,
            fld_name = response.fldName,
            fld_email = response.fldEmail,
            fld_token = response.fldToken.toString(),
            fecha = response.fecha ?: ""
        )
    }
}


