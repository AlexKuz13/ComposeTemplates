package com.alexkuz.composeteamplates.recipes.customActivity.src

import com.alexkuz.composeteamplates.recipes.customActivity.src.app_package.customActivity
import com.alexkuz.composeteamplates.recipes.customActivity.src.app_package.customActivityJava
import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.android.tools.idea.wizard.template.impl.activities.common.generateSimpleLayout

fun RecipeExecutor.customActivityRecipe(
    moduleData: ModuleTemplateData,
    activityClass: String,
    isLauncher: Boolean,
    addHilt: Boolean,
    generateLayout: Boolean,
    layoutName: String,
    packageName: String,
) {
    val (projectData, srcOut, resOut, manifestOut) = moduleData
    val ktOrJavaExt = projectData.language.extension
    val appCompatVersion = moduleData.apis.appCompatVersion
    val useAndroidX = projectData.androidXSupport

    addAllKotlinDependencies(moduleData)

    mergeXml(
        source = androidManifestXml(activityClass, moduleData.isLibrary, isLauncher, packageName),
        to = manifestOut.resolve("AndroidManifest.xml")
    )

    if (generateLayout) {
        generateSimpleLayout(moduleData, activityClass, layoutName, null)
    }

    addDependency("com.android.support:appcompat-v7:$appCompatVersion.+")

    if (addHilt) {
//        applyPlugin("dagger.hilt.android.plugin", AgpVersion(7,3))
//        applyPlugin("kotlin-kapt", AgpVersion(7,3))
        addClasspathDependency("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")
        addDependency("com.google.dagger:hilt-android:2.28-alpha")
        addDependency("com.google.dagger:hilt-android-compiler:2.28-alpha", "kapt")
    }

    val customActivity = when (projectData.language) {
        Language.Java -> customActivityJava(activityClass, generateLayout, layoutName, packageName, useAndroidX)
        Language.Kotlin -> customActivity(activityClass, generateLayout, layoutName, packageName, useAndroidX)
    }

    save(customActivity, srcOut.resolve("$activityClass.$ktOrJavaExt"))

    open(srcOut.resolve("$activityClass.$ktOrJavaExt"))
}