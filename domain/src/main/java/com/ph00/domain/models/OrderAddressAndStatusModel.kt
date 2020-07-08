package com.ph00.domain.models

import com.ph00.domain.Constants


data class OrderAddressAndStatusModel(
    val address: String = "",
    val status: String = Constants.ORDER_STATUS_CLOSED
)