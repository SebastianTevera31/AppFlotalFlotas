package com.rfz.appflotalflotas.data.repository.login


import com.rfz.appflotalflotas.data.model.login.dto.LoginDto
import com.rfz.appflotalflotas.data.model.login.response.LoginResponse
import com.rfz.appflotalflotas.data.network.service.login.LoginService
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginService: LoginService) {
    suspend fun doLogin(usuario: String, password: String): Response<LoginResponse> {
        return loginService.doLogin(LoginDto(usuario, password))
    }
}