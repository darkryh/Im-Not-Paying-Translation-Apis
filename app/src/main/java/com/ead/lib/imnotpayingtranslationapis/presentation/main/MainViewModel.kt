package com.ead.lib.imnotpayingtranslationapis.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ead.lib.imnotpayingtranslationapis.presentation.main.event.MainEvent
import com.ead.lib.imnotpayingtranslationapis.presentation.main.intent.MainIntent
import com.ead.lib.imnotpayingtranslationapis.presentation.main.state.MainState
import com.ead.lib.imnotpayingtranslationapis.presentation.util.QueryValidator
import com.ead.lib.imnotpayingtranslationapis.translator.android.AndroidDriver
import com.ead.lib.imnotpayingtranslationapis.translator.core.Translator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    private lateinit var translator : Translator

    private var queryJob: Job? = null
    private var lastQuery: String? = null

    fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.InitTranslator -> {
                viewModelScope.launch(Dispatchers.IO) {
                    translator = Translator(AndroidDriver(intent.context))
                    translator.init(intent.from, intent.to)
                }
            }
            is MainIntent.EnteredSearch -> {
                _state.value = state.value.copy(
                    isLoading = true,
                    textToTranslate = state.value.textToTranslate.copy(
                        textField = intent.value,
                        isHintVisible = state.value.textToTranslate.textField.text.isBlank()
                    )
                )
                onSearchInput(intent.value.text)
            }
            is MainIntent.ChangeSearchFocus -> {
                _state.value = state.value.copy(
                    textToTranslate = state.value.textToTranslate.copy(
                        isHintVisible = !intent.isFocused && state.value.textToTranslate.textField.text.isBlank()
                    )
                )
            }
            is MainIntent.LoadTranslation -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val translatedText = translator.translate(intent.query ?: return@launch)
                    _state.value = state.value.copy(
                        translatedText = translatedText,
                        isLoading = false
                    )
                }
            }
            is MainIntent.OnNavigate -> {

            }
        }
    }

    private fun onSearchInput(query: String) {
        queryJob?.cancel()
        queryJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500L)
            QueryValidator.execute(query, lastQuery)?.let { validatedQuery ->
               executePagingQuery(validatedQuery)
            }
        }
    }

    private fun executePagingQuery(query: String?) {
        if (QueryValidator.execute(query, lastQuery) == null) return

        lastQuery = query?.trim()

        onIntent(MainIntent.LoadTranslation(lastQuery))
    }
}