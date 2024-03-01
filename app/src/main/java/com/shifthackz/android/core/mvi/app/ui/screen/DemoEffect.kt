package com.shifthackz.android.core.mvi.app.ui.screen

import com.shifthackz.android.core.mvi.MviEffect

sealed interface DemoEffect : MviEffect {
    data class CopyToClipboard(val number: Int) : DemoEffect
}
