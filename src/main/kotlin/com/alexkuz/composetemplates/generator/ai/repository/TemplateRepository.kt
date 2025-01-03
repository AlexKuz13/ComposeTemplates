package com.alexkuz.composetemplates.generator.ai.repository

import com.alexkuz.composetemplates.generator.ai.api.TemplateApi
import com.alexkuz.composetemplates.generator.ai.model.AuthState
import com.alexkuz.composetemplates.generator.ai.model.Message
import com.alexkuz.composetemplates.generator.ai.model.TemplateRequest
import hu.akarnokd.rxjava2.swing.SwingSchedulers
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface TemplateRepository {
    fun getTemplate(request: String): Single<String?>
}

class TemplateRepositoryImpl(
    private val templateApi: TemplateApi,
    authState: AuthState,
) : TemplateRepository {

    private val modelUri = "gpt://${authState.catalogId}/yandexgpt-lite"

    override fun getTemplate(request: String): Single<String?> {
        return templateApi.getTemplate(
            TemplateRequest(
                modelUri = modelUri,
                messages = listOf(Message(text = request))
            )
        )
            .map { it.result.alternatives.firstOrNull()?.message?.text }
            .subscribeOn(Schedulers.io())
            .observeOn(SwingSchedulers.edt())
    }
}