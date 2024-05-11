package ui

import MainViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Tooltip(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
){
    val tip = PlainTooltipState()
    PlainTooltipBox(
        tooltip = { Text(text) },
        tooltipState = tip
    ){
        val scope = rememberCoroutineScope()
        Box(
            modifier = modifier.tooltipAnchor()
                .onPointerEvent(
                    PointerEventType.Enter
                ) {
                    scope.launch {
                        tip.show()
                    }
                }
                .onPointerEvent(
                    PointerEventType.Exit
                ) {
                    scope.launch {
                        tip.dismiss()
                    }
                }
        ){
            content()
        }
    }
}