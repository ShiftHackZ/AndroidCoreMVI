package com.shifthackz.android.core.mvi.app.ui.screen

import com.shifthackz.android.core.mvi.MviEffect

sealed interface DemoEffect : MviEffect {
    data class ShowToast(val number: Int) : DemoEffect
}
