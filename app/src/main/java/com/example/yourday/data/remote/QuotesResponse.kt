package com.example.yourday.data.remote

import com.google.gson.annotations.SerializedName

data class QuotesResponse(
    @SerializedName("q") val quote: String,
    @SerializedName("a") val author: String,
    @SerializedName("h") val html: String
)
