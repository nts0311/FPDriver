package com.sonnt.fpdriver.data.dto.response

data class AuthenticationResponse(
    var userId: Long = 0L,
    var jwtToken: String = ""
) : BaseResponse()