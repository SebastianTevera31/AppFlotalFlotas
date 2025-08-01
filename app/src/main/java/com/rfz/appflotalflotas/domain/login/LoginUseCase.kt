package com.rfz.appflotalflotas.domain.login

import android.util.Log
import com.google.gson.Gson
import com.rfz.appflotalflotas.data.model.login.response.LoginErrorResponse
import com.rfz.appflotalflotas.data.model.login.response.LoginResponse
import com.rfz.appflotalflotas.data.model.login.response.Result
import com.rfz.appflotalflotas.data.repository.login.LoginRepository
import javax.inject.Inject
class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    private val gson = Gson()

    suspend operator fun invoke(
        usuario: String,
        password: String
    ): Result<LoginResponse> {
        return try {
            val response = repository.doLogin(usuario, password)
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    Result.Success(loginResponse)
                } ?: Result.Failure(Exception("Cuerpo de la respuesta vacío"))
            } else {
                val errorMsg = response.errorBody()?.string()
                val parsedError = try {
                    gson.fromJson(errorMsg, LoginErrorResponse::class.java).message.errorValue
                } catch (e: Exception) {
                    Log.e("LoginUseCase", "$e")
                    "Error desconocido del servidor"
                }
                Log.e("LoginUseCase", "$parsedError")
                Result.Failure(Exception(parsedError))
            }
        } catch (e: Exception) {
            Log.e("LoginUseCase", "$e")
            Result.Failure(e)
        }
    }
}
