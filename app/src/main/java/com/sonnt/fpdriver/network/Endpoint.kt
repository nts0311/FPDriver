package com.sonnt.fpdriver.network

class Endpoint {
    companion object {
        val BASE_URL = "https://797c-2402-800-6172-89f3-1dc5-b2bd-c3a5-b193.ap.ngrok.io"
        val WS_BASE_URL = "$BASE_URL/stomp"

        val newOrderRequest = "/users/ws/driver/newOrderRequest"
    }
}