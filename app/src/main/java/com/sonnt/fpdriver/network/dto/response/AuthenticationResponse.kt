package com.sonnt.fpdriver.network.dto.response

data class AuthenticationResponse(
    var userId: Long = 0L,
    var jwtToken: String = ""
) : BaseResponse()