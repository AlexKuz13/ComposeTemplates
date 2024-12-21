package com.alexkuz.composetemplates.generator.utils

import com.android.tools.idea.insights.isAndroidApp
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.modules
import org.jetbrains.android.dom.manifest.Manifest
import org.jetbrains.android.facet.AndroidFacet

fun Project.getAppPackage(): String {
    val androidFacet = AndroidFacet.getInstance(modules.first { it.isAndroidApp })
    return Manifest.getMainManifest(androidFacet)?.`package`?.value?.substringBefore(".app").orEmpty()
}