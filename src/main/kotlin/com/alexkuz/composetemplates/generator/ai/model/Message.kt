package com.alexkuz.composetemplates.generator.ai.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    @Json(name = "role") val role: String = "user",
    @Json(name = "text") val text: String
)