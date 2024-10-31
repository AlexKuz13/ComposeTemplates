package com.alexkuz.composetemplates.generator.service

import com.alexkuz.composetemplates.generator.model.AuthState
import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "TemplateGenerationConfiguration", storages = [
    Storage(value = "templateGenerationConfiguration.xml")
])
class AuthService: PersistentStateComponent<AuthState> {

    private var authState = AuthState()

    override fun getState() = authState

    override fun loadState(state: AuthState) {
        authState = state
    }

    companion object {
        fun getInstance(project: Project): AuthService = project.service()
    }
}