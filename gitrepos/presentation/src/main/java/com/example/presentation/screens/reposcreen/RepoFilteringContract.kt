package com.example.presentation.screens.reposcreen

import com.example.presentation.uimodels.RepositoryUiModel

interface RepoFilteringContract{
    val unfilteredData: MutableList<RepositoryUiModel>
        get() = mutableListOf<RepositoryUiModel>()


    fun List<RepositoryUiModel>.refreshUnfiltered(){
        unfilteredData.clear()
        unfilteredData.addAll(this)
    }
}