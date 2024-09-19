package com.alexkuz.composeteamplates.trello

import com.alexkuz.composeteamplates.trello.services.TrelloService
import com.intellij.openapi.project.Project
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface TrelloInjector {
    val repository: TrelloRepository
    val trelloServiceApi: TrelloServiceApi

    fun trelloState(project: Project): TrelloState
    fun trelloActionPresenter(view: TrelloFormView, project: Project): TrelloActionPresenter
}

class TrelloInjectorImpl: TrelloInjector {

    override val trelloServiceApi: TrelloServiceApi by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory( MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.trello.com/")
            .build()
            .create(TrelloServiceApi::class.java)
    }

    override val repository: TrelloRepository by lazy(LazyThreadSafetyMode.NONE) {
        TrelloRepositoryImpl(trelloServiceApi)
    }

    override fun trelloState(project: Project): TrelloState {
        return TrelloService.getInstance(project).state
    }

    override fun trelloActionPresenter(view: TrelloFormView, project: Project): TrelloActionPresenter {
        return TrelloActionPresenterImpl(view, repository, trelloState(project))
    }
}