package com.sonnt.fpdriver.network

class Endpoint {
    companion object {
        val BASE_URL = "https://ff8e-2402-800-6172-89f3-3dd6-a772-6641-442a.ap.ngrok.io"
        val WS_BASE_URL = "$BASE_URL/stomp"

        val newOrderRequest = "/users/ws/driver/newOrderRequest"
    }
}