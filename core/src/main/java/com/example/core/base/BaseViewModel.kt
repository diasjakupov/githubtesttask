package com.example.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


interface BaseViewModelContract<A: ViewModelActions, E: ViewModelEvents>{
    val actions: SharedFlow<A>
    val events: SharedFlow<E>

    fun handleActions(action: A)
}

fun interface ActionCallback<A: ViewModelActions> {

    fun sendAction(actions: A)
}

abstract class BaseViewModel<A: ViewModelActions, E: ViewModelEvents>: ViewModel(), BaseViewModelContract<A, E> {


    override val actions = MutableSharedFlow<A>()
    override val events = MutableSharedFlow<E>()

    init {
        viewModelScope.launch{
            actions.collect(::handleActions)
        }
    }

    protected suspend fun pushEvent(event: E){
        events.emit(event)
    }
}