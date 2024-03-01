@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.shifthackz.android.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * [MviViewModel] is a class that is responsible for preparing and managing the data for
 * an [MviComponent]. A [MviViewModel] is always created in association with a scope of [MviComponent] and will
 * be retained as long as the scope is alive.
 *
 * In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a
 * configuration change (e.g. rotation). The new owner instance just re-connects to the existing model.
 *
 * The purpose of the ViewModel is to acquire and keep the [MviState] that is necessary for rendering
 * [MviComponent] by to observing changes in the state.
 *
 * [MviViewModel]'s only responsibility is to manage the data for the UI. It should never access
 * your Composable components hierarchy or hold a reference back to the Activity.
 */
abstract class MviViewModel<S : MviState, I : MviIntent, E : MviEffect> : ViewModel() {

    private val _effect: Channel<E> = Channel()
    val effect: Flow<E> = _effect.receiveAsFlow()

    private val _state: MutableStateFlow<S> by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<S> by lazy { _state.asStateFlow() }

    /**
     * Property that returns current instance of [state].
     */
    protected val currentState: S
        get() = state.value

    /**
     * The initial instance of state that is used at [MviViewModel] initialization.
     */
    abstract val initialState: S

    /**
     * Callback function that supposed to receive an [intent] from UI layer.
     *
     * Should be overridden and implemented in
     */
    open fun processIntent(intent: I) = Unit

    /**
     * Updates the [state] atomically using the specified [mutation] of its value.
     * [mutation] may be evaluated multiple times, if value is being concurrently updated.
     *
     * @param mutation the mapping function to be applied to update the [state].
     */
    protected fun updateState(mutation: (S) -> S) = _state.update {
        mutation(it)
    }

    /**
     * Delivers a new value to the [state].
     *
     * Passing a new value that is [equal][Any.equals] to the [currentState] will not update the
     * [state] property.
     *
     * It is recommended to use [updateState] function to avoid race conditions.
     *
     * @param state a new instance of [S] to emit.
     */
    protected fun emitState(state: S) {
        _state.value = state
    }

    /**
     * Delivers a new value to the [effect].
     *
     * @param effect a new instance of [E] to emit.
     */
    protected fun emitEffect(effect: E) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _effect.send(effect)
        }
    }
}
