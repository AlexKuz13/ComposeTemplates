package com.alexkuz.composetemplates.generator.ai.injector

import com.alexkuz.composetemplates.generator.ai.api.TemplateApi
import com.alexkuz.composetemplates.generator.ai.model.AuthState
import com.alexkuz.composetemplates.generator.ai.repository.TemplateRepository
import com.alexkuz.composetemplates.generator.ai.repository.TemplateRepositoryImpl
import com.alexkuz.composetemplates.generator.ai.service.AuthService
import com.intellij.openapi.project.Project
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface TemplateGeneratorInjector {
    fun authState(project: Project): AuthState
    fun templateApi(project: Project): TemplateApi
    fun repository(project: Project): TemplateRepository
}

class TemplateGeneratorInjectorImpl : TemplateGeneratorInjector {

    override fun authState(project: Project) = AuthService.getInstance(project).state

    override fun templateApi(project: Project): TemplateApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val authState = authState(project)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Api-Key ${authState.token}")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-folder-id", authState.catalogId)
                    .addHeader("Accept", "*/*")
                    .build()
                chain.proceed(request)
            }
            .build()
        return Retrofit.Builder()
            .baseUrl("https://llm.api.cloud.yandex.net")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TemplateApi::class.java)
    }

    override fun repository(project: Project) = TemplateRepositoryImpl(templateApi(project), authState(project))
}