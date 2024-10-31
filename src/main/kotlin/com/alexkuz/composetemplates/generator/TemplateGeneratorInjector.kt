package com.alexkuz.composetemplates.generator

import com.alexkuz.composetemplates.generator.model.AuthState
import com.alexkuz.composetemplates.generator.service.AuthService
import com.intellij.openapi.project.Project

interface TemplateGeneratorInjector {
    fun authState(project: Project): AuthState
}

class TemplateGeneratorInjectorImpl: TemplateGeneratorInjector {
    override fun authState(project: Project) = AuthService.getInstance(project).state
}