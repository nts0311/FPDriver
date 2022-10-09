package com.sonnt.fpdriver.network

class Endpoint {
    companion object {
        val BASE_URL = "https://7ad7-2402-800-6172-89f3-3c46-bff3-6963-f889.ap.ngrok.io"
        val WS_BASE_URL = "$BASE_URL/stomp"

        val newOrderRequest = "/users/ws/driver/newOrderRequest"
    }
}