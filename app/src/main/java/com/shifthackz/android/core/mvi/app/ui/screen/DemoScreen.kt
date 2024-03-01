package com.shifthackz.android.core.mvi.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shifthackz.android.core.mvi.MviComponent

@Composable
fun DemoScreen(
    modifier: Modifier = Modifier,
) {
    val clipboardManager = LocalClipboardManager.current
    MviComponent(
        viewModel = viewModel<DemoViewModel>(),
        processEffect = { effect ->
            when (effect) {
                is DemoEffect.CopyToClipboard -> clipboardManager.setText(
                    AnnotatedString("${effect.number}")
                )
            }
        }
    ) { state, processIntent ->
        DemoScreenContent(
            modifier = modifier,
            state = state,
            processIntent = processIntent,
        )
    }
}

@Composable
@Preview
private fun DemoScreenContent(
    modifier: Modifier = Modifier,
    state: DemoState = DemoState(),
    processIntent: (DemoIntent) -> Unit = {},
) {
    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = { processIntent(DemoIntent.IncrementNumber) }) {
                Text(
                    text = "Increment number",
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "You have pressed button this many times:"
                )
                Text(
                    text = state.number.toString(),
                    fontSize = 48.sp,
                )
            }
        }
    }
}
