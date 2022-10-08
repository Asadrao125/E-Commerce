package com.fictivestudios.imdfitness.webServices

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ApiResponse<T> : Serializable {

    @SerializedName("status")
    val status: Int? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("data")
    val data: T? = null

    @SerializedName("access_token")
    val access_token: String? = null

    @SerializedName("cart_count")
    val cart_count: String? = null

}