package com.sonnt.fpdriver.network

class Endpoint {
    companion object {
        val BASE_URL = "https://dca6-2402-800-6172-89f3-d92-a372-41e8-18c9.ap.ngrok.io"
        val WS_BASE_URL = "$BASE_URL/stomp"

        val newOrderRequest = "/users/ws/driver/newOrderRequest"
    }
}