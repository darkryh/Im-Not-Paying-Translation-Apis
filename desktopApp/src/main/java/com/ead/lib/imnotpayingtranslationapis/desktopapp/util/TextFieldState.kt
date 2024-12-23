package com.ead.lib.imnotpayingtranslationapis.desktopapp.util

import androidx.compose.ui.text.input.TextFieldValue

data class TextFieldState(
    val textField: TextFieldValue = TextFieldValue(""),
    val hint: String,
    val isHintVisible: Boolean = true
)