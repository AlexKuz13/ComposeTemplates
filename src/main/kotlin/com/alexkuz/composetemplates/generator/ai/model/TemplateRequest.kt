package com.alexkuz.composetemplates.generator.ai.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TemplateRequest(
    @Json(name = "modelUri") val modelUri: String,
    @Json(name = "completionOptions") val completionOptions: CompletionOptions = CompletionOptions(),
    @Json(name = "messages") val messages: List<Message>
)

@JsonClass(generateAdapter = true)
data class CompletionOptions(
    @Json(name = "stream") val stream: Boolean = false,
    @Json(name = "temperature") val temperature: Double = 0.5,
    @Json(name = "maxTokens") val maxTokens: String = "1000",
)