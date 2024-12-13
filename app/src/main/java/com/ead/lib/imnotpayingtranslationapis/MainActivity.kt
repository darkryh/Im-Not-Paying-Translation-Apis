package com.ead.lib.imnotpayingtranslationapis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ead.lib.imnotpayingtranslationapis.presentation.main.MainViewModel
import com.ead.lib.imnotpayingtranslationapis.presentation.main.components.TextField
import com.ead.lib.imnotpayingtranslationapis.presentation.main.intent.MainIntent
import com.ead.lib.imnotpayingtranslationapis.presentation.theme.ImNotPayingTranslationApisTheme
import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Language

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel = viewModel<MainViewModel>()

            ImNotPayingTranslationApisTheme {

                LaunchedEffect(Unit) {
                    viewModel.onIntent(
                        MainIntent.InitTranslator(this@MainActivity, Language.Spanish, Language.English)
                    )
                }

                val mainState = viewModel.state.collectAsState()

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
                            viewModel.onIntent(MainIntent.EnteredSearch(it))
                        },
                        onFocusChange = {
                            viewModel.onIntent(MainIntent.ChangeSearchFocus(it.isFocused))
                        }
                    )
                }
            }
        }
    }
}