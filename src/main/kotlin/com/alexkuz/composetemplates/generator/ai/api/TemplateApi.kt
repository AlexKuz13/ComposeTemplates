package com.alexkuz.composetemplates.generator.ai.api

import com.alexkuz.composetemplates.generator.ai.model.TemplateRequest
import com.alexkuz.composetemplates.generator.ai.model.TemplateResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface TemplateApi {

    @POST("/foundationModels/v1/completion")
    fun getTemplate(
        @Body request: TemplateRequest,
    ): Single<TemplateResponse>
}