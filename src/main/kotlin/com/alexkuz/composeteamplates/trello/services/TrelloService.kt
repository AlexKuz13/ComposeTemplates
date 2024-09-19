package com.alexkuz.composeteamplates.trello.services

import com.alexkuz.composeteamplates.trello.TrelloState
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "TrelloConfiguration", storages = [
    Storage(value = "trelloConfiguration.xml")
])
class TrelloService: PersistentStateComponent<TrelloState> {

    private var trelloState = TrelloState()

    override fun getState(): TrelloState = trelloState

    override fun loadState(state: TrelloState) {
        trelloState = state
    }

    companion object {
        fun getInstance(project: Project): TrelloService = project.service()
    }
}