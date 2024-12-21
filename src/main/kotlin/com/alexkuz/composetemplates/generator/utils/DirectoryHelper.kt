package com.alexkuz.composetemplates.generator.utils

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager

object DirectoryHelper {

    private const val DEFAULT_ERROR = "Произошла ошибка. Попробуй еще раз."

    fun generateScreenOrError(
        project: Project,
        packageName: String,
        composableName: String,
        content: String,
    ): String? {
        if (packageName.isBlank() || packageName.endsWith(".")) return "Невалидный package"
        val packagePath = packageName.replace('.', '/')
        val basePath = project.basePath ?: return DEFAULT_ERROR
        val virtualFile = LocalFileSystem.getInstance().findFileByPath(basePath) ?: return DEFAULT_ERROR
        val directories = mutableListOf<PsiDirectory>()
        collectDirectoriesByPathParam(virtualFile, packagePath, project, directories)

        if (directories.isEmpty()) return "Заданной директории не найдено"

        WriteCommandAction.runWriteCommandAction(project) {
            with(directories.first().createFile("$composableName.kt")) {
                viewProvider.document.setText(content)
                FileEditorManager.getInstance(project).openFile(this.virtualFile, true)
            }
        }

        return null
    }

    private fun collectDirectoriesByPathParam(
        virtualFile: VirtualFile,
        packagePath: String,
        project: Project,
        directories: MutableList<PsiDirectory>
    ) {
        if (virtualFile.isDirectory && isCorrectPath(virtualFile)) {
            if (virtualFile.path.contains(packagePath)) {
                val psiDirectory = PsiManager.getInstance(project).findDirectory(virtualFile)
                if (psiDirectory != null) {
                    directories.add(psiDirectory)
                    return
                }
            }

            virtualFile.children.forEach {
                collectDirectoriesByPathParam(it, packagePath, project, directories)
            }
        }
    }

    private fun isCorrectPath(virtualFile: VirtualFile): Boolean {
        val path = virtualFile.path
        val forbiddenSubstrings = listOf(".", "/build/", "/res/", "/test/", "/androidTest/")
        return forbiddenSubstrings.all { !path.contains(it) }
    }
}