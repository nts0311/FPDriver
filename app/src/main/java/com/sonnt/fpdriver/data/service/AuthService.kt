package com.sonnt.fpdriver.data.service

import com.sonnt.fpdriver.data.dto.request.AuthRequest
import com.sonnt.fpdriver.data.dto.request.UpdateFcmTokenRequest
import com.sonnt.fpdriver.data.dto.response.AuthenticationResponse
import com.sonnt.fpdriver.data.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body body: AuthRequest): Response<AuthenticationResponse>

    @POST("auth/update-fcm-token")
    suspend fun updateFcmToken(@Body body: UpdateFcmTokenRequest): Response<BaseResponse>
}