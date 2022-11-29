package com.sonnt.fpdriver.network.service

import com.sonnt.fpdriver.features.main.GetActiveOrderResponse
import com.sonnt.fpdriver.features.orders.AcceptOrderRequest
import com.sonnt.fpdriver.features.orders_step2.ArrivedAtMerchantRequest
import com.sonnt.fpdriver.features.orders_step4.ConfirmReceiveOrderRequest
import com.sonnt.fpdriver.features.orders_step5.ArrivedAtCustomerRequest
import com.sonnt.fpdriver.features.orders_step7.ConfirmCompletedOrderRequest
import com.sonnt.fpdriver.network.dto.request.AuthRequest
import com.sonnt.fpdriver.network.dto.response.AuthenticationResponse
import com.sonnt.fpdriver.network.dto.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderService {
    @GET("/driver/order/get-active-order")
    suspend fun getActiveOrder(): Response<GetActiveOrderResponse>

    @POST("driver/order/accept-order")
    suspend fun acceptOrder(@Body body: AcceptOrderRequest): Response<BaseResponse>

    @POST("driver/order/arrived-at-merchant")
    suspend fun arrivedAtMerchant(@Body body: ArrivedAtMerchantRequest): Response<BaseResponse>

    @POST("driver/order/confirm-received-order")
    suspend fun confirmReceivedOrder(@Body body: ConfirmReceiveOrderRequest): Response<BaseResponse>

    @POST("driver/order/arrived-at-customer")
    suspend fun arrivedAtCustomer(@Body body: ArrivedAtCustomerRequest): Response<BaseResponse>

    @POST("driver/order/confirm-completed-order")
    suspend fun confirmCompletedOrder(@Body body: ConfirmCompletedOrderRequest): Response<BaseResponse>

    @GET("merchant/order/cancel-order")
    suspend fun cancelOrder(@Query("orderId") orderId: Long): Response<BaseResponse>
}