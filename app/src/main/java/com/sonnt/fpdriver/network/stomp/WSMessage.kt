package com.sonnt.fpdriver.network.stomp

enum class WSMessageCode(val code: Int) {
    FINDING_DRIVER(0),
    NEW_ORDER(1),
    FOUND_DRIVER(2),
    CANCEL_ORDER(3)
}

data class WSMessage(val code: Int, val body: String) {
    constructor(code: WSMessageCode, body: String) : this(code.code, body)
}