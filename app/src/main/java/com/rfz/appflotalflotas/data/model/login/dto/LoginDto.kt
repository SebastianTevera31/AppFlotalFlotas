package com.rfz.appflotalflotas.data.model.login.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("fld_username") var fld_username: String,
    @SerializedName("fld_password") var fld_password: String,
)