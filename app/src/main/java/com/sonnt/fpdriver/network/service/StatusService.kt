package com.sonnt.fpdriver.network.service

import com.sonnt.fpdriver.model.Address
import com.sonnt.fpdriver.network.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StatusService {
    @POST("driver/status/update-location")
    suspend fun updateLastLocation(@Body location: Address): Response<BaseResponse>
}