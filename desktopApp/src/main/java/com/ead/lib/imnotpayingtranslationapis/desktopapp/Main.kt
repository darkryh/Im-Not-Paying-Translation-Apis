package com.ead.lib.imnotpayingtranslationapis.desktopapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ead.lib.imnotpayingtranslationapis.desktopapp.presentation.main.components.TextField
import com.ead.lib.imnotpayingtranslationapis.desktopapp.presentation.main.state.MainState
import com.ead.lib.imnotpayingtranslationapis.translator.core.Translator
import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Language
import com.ead.lib.imnotpayingtranslationapis.translator.kotlin.DesktopDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Sadly the Desktop version library still doesn't support composable
 * so when need to instance the translator
 * make sure to called before composable function
 */
fun main() = runBlocking {

    val driver = DesktopDriver()
    val translator = Translator(driver)

    var queryJob : Job? = null

    /**
     * Initialize the translator
     * make sure to called before composable function
     */
    translator.init(Language.Spanish, Language.English)

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Translator",
            icon = null
        ) {

            val mainState = remember { mutableStateOf(MainState()) }
            val scope = rememberCoroutineScope()

            Box(
                modifier = Modifier.size(500.dp)
            ) {
                Column (
                    modifier = Modifier.fillMaxSize()
                        .padding(
                            vertical = 64.dp,
                            horizontal = 24.dp
                        )
                ) {
                    Text(
                        text = "Text Translated"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = if (mainState.value.isLoading) {
                            "Translating"
                        }
                        else
                            mainState.value.translatedText
                    )
                    Text(
                        text = "Text in Spanish"
                    )
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = mainState.value.textToTranslate.textField,
                        hint = mainState.value.textToTranslate.hint,
                        isHintVisible = mainState.value.textToTranslate.isHintVisible,
                        onValueChange = {
                            mainState.value = mainState.value.copy(
                                textToTranslate = mainState.value.textToTranslate.copy(
                                    textField = it,
                                    isHintVisible = it.text.isBlank()
                                )
                            )

                            queryJob?.cancel()
                            queryJob = scope.launch(Dispatchers.IO) {
                                delay(500L)
                                val translatedText = translator.translate(it.text.ifEmpty { return@launch })
                                mainState.value = mainState.value.copy(
                                    translatedText = translatedText,
                                    isLoading = false
                                )
                            }
                        },
                        onFocusChange = {
                            mainState.value = mainState.value.copy(
                                textToTranslate = mainState.value.textToTranslate.copy(
                                    isHintVisible = !it.isFocused && mainState.value.textToTranslate.textField.text.isBlank()
                                )
                            )
                        }
                    )
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                translator.release()
            }
        }
    }
}