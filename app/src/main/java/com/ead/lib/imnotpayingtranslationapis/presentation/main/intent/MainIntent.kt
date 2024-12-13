package com.ead.lib.imnotpayingtranslationapis.presentation.main.intent

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Language

sealed class MainIntent {
    data class InitTranslator(val context: Context,val from: Language, val to: Language) : MainIntent()
    data class LoadTranslation(val query: String? = null) : MainIntent()
    data class EnteredSearch(val value: TextFieldValue): MainIntent()
    data class ChangeSearchFocus(val isFocused: Boolean): MainIntent()
    data class OnNavigate(val any: Any) : MainIntent()
}