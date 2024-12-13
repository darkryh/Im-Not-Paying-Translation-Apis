package com.ead.lib.imnotpayingtranslationapis.desktopapp.presentation.main.state

import com.ead.lib.imnotpayingtranslationapis.desktopapp.util.TextFieldState

data class MainState(
    val textToTranslate: TextFieldState = TextFieldState(hint = "Text to translate..."),
    val translatedText: String = "Translated text...",
    val isTranslatorLoaded : Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)