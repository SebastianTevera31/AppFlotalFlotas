package com.rfz.appflotalflotas.data.network.service.login

import com.rfz.appflotalflotas.data.model.login.dto.LoginDto
import com.rfz.appflotalflotas.data.model.login.response.LoginResponse
import com.rfz.appflotalflotas.data.network.client.login.LoginClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient) {
    suspend fun doLogin(requestBody: LoginDto): Response<LoginResponse> {
        return withContext(Dispatchers.IO) {
            loginClient.doLogin(requestBody)
        }
    }
}