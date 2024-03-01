package com.shifthackz.android.core.mvi.app.ui.screen

import androidx.compose.runtime.Immutable
import com.shifthackz.android.core.mvi.MviState

@Immutable
data class DemoState(val number: Int = 0) : MviState
