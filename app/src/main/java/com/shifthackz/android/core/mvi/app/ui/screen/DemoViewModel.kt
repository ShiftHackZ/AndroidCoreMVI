package com.shifthackz.android.core.mvi.app.ui.screen

import com.shifthackz.android.core.mvi.MviViewModel

class DemoViewModel : MviViewModel<DemoState, DemoIntent, DemoEffect>() {

    override val initialState = DemoState()

    override fun processIntent(intent: DemoIntent) {
        when (intent) {
            DemoIntent.IncrementNumber -> updateState { state ->
                val incremented = state.number + 1
                emitEffect(DemoEffect.CopyToClipboard(incremented))
                emitEffect(DemoEffect.ShowToast(incremented))
                state.copy(number = incremented)
            }
        }
    }
}
