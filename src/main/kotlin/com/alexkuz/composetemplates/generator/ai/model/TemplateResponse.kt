package com.alexkuz.composetemplates.generator.ai.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TemplateResponse(
    @Json(name = "result") val result: Result
)

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "alternatives") val alternatives: List<Alternative>,
)

@JsonClass(generateAdapter = true)
data class Alternative(
    @Json(name = "message") val message: Message,
)