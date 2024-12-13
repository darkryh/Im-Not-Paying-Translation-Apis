package com.ead.lib.imnotpayingtranslationapis.presentation.util

import androidx.compose.ui.text.input.TextFieldValue

data class TextFieldState(
    val textField: TextFieldValue = TextFieldValue(""),
    val hint: String,
    val isHintVisible: Boolean = true
)