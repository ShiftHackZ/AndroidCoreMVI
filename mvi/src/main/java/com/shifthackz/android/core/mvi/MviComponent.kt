package com.shifthackz.android.core.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * [MviComponent] is a composable component that observes [MviState] changes (states and effects)
 * from corresponding [MviViewModel].
 *
 * To process actions (intents) that happen on UI side [MviComponent] should pass an instance of
 * [MviIntent] to [viewModel].
 *
 * @param viewModel an instance of [MviViewModel] that holds the data for this component.
 * @param processEffect a callback function that should process [MviEffect] (an additional event
 * that should be processed on component side but does not affect UI state changes).
 * @param content a content that should be displayed for corresponding [MviState].
 */
@Composable
fun <S : MviState, I : MviIntent, E : MviEffect> MviComponent(
    viewModel: MviViewModel<S, I, E>,
    processEffect: (effect: E) -> Unit = {},
    content: @Composable (state: S) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            processEffect(effect)
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    content(state)
}

/**
 * [MviComponent] is a composable component that observes [MviState] changes (states and effects)
 * from corresponding [MviViewModel].
 *
 * To process actions (intents) that happen on UI side [MviComponent] should pass an instance of
 * [MviIntent] to [viewModel].
 *
 * @param viewModel an instance of [MviViewModel] that holds the data for this component.
 * @param processEffect a callback function that should process [MviEffect] (an additional event
 * that should be processed on component side but does not affect UI state changes).
 * @param content a content that should be displayed for corresponding [MviState] and call the
 * intents processor function to notify [MviViewModel] about [MviIntent] actions.
 */
@Composable
fun <S : MviState, I : MviIntent, E : MviEffect> MviComponent(
    viewModel: MviViewModel<S, I, E>,
    processEffect: (effect: E) -> Unit = {},
    content: @Composable (state: S, processIntent: (I) -> Unit) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            processEffect(effect)
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    content(state, viewModel::processIntent)
}
