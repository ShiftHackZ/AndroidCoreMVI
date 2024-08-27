package com.shifthackz.android.core.mvi.app.ui.screen

import com.shifthackz.android.core.mvi.MviViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DemoViewModel : MviViewModel<DemoState, DemoIntent, DemoEffect>() {

    override val initialState = DemoState()

    override val effectDispatcher: CoroutineDispatcher = Dispatchers.Main

    override fun processIntent(intent: DemoIntent) {
        when (intent) {
            DemoIntent.IncrementNumber -> updateState { state ->
                val incremented = state.number + 1
                emitEffect(DemoEffect.ShowToast(incremented))
                state.copy(number = incremented)
            }
        }
    }
}
