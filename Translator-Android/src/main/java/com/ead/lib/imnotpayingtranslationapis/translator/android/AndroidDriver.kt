package com.ead.lib.imnotpayingtranslationapis.translator.android

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.ead.lib.imnotpayingtranslationapis.translator.android.core.DriverView
import com.ead.lib.imnotpayingtranslationapis.translator.android.core.extensions.evaluateJavascript
import com.ead.lib.imnotpayingtranslationapis.translator.android.scripts.translationScript
import com.ead.lib.imnotpayingtranslationapis.translator.android.util.Thread.letOnUi
import com.ead.lib.imnotpayingtranslationapis.translator.android.util.Thread.onUi
import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Language
import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Source
import com.ead.lib.imnotpayingtranslationapis.translator.core.core.system.extensions.isGenderResponse
import com.ead.lib.imnotpayingtranslationapis.translator.core.models.Driver
import com.ead.lib.imnotpayingtranslationapis.translator.core.models.GenderResponse
import com.ead.lib.imnotpayingtranslationapis.translator.core.models.Response
import com.ead.lib.imnotpayingtranslationapis.translator.core.scripts.INITIALIZATION_SCRIPT
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import kotlin.coroutines.resume

/**
 * The driver used to translate text
 * in android platform
 */
class AndroidDriver(
    /**
     * @param context The context to use
     */
    private val context: Context
) : Driver {

    /**
     * The language to translate from
     */
    override var _from: Language? = null

    /**
     * The language to translate to
     */
    override var _to: Language? = null


    /**
     * flag to check if the driver has been initialized
     */
    private var initializedDriver = false


    /**
     * The language to translate from
     */
    val from get() = _from ?: throw IllegalStateException("from config not initialized")

    /**
     * The language to translate to
     */
    val to get() = _to ?: throw IllegalStateException("to config not initialized")

    /**
     * The json to use to parse the response
     */
    private val json = Json { ignoreUnknownKeys = true }

    /**
     * The driver view to use
     * translation process in android
     */
    val _driverView: DriverView by lazy {
        letOnUi {
            DriverView(context).let { instance ->
                runBlocking {
                    delay(300)
                    instance
                }
            }
        }
    }

    /**
     * The javascript bridge to use
     */
    private val bridge = JavaScriptBridge { result -> Log.d("translator", "result: $result") }

    /**
     * initialization process for the translator
     * in android platform
     */
    @SuppressLint("SetJavaScriptEnabled")
    override suspend fun init(from: Language, to: Language) {
        this@AndroidDriver._from = from
        this@AndroidDriver._to = to

        suspendCancellableCoroutine { continuation ->
            onUi {
                _driverView.apply {
                    settings.let {
                        it.javaScriptEnabled = true
                        it.domStorageEnabled = true
                        it.cacheMode = WebSettings.LOAD_DEFAULT
                        it.userAgentString = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
                    }

                    addJavascriptInterface(bridge, "Android")

                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            if (initializedDriver) {
                                return
                            }
                            initializedDriver = true
                            evaluateJavascript(INITIALIZATION_SCRIPT)
                            continuation.resume(Unit)
                        }
                    }
                    loadUrl(Source.getTranslateMode(from,to))

                    continuation.invokeOnCancellation {
                        release()
                    }
                }
            }
        }
    }

    /**
     * @param from The language to translate from
     * @param to The language to translate to
     */
    private suspend fun onLanguageChanged(from: Language?, to: Language?) {
        if (from == null || to == null) return

        if (this@AndroidDriver._from != from || this@AndroidDriver._to != to) {
            init(from, to)
        }
    }


    /**
     * @param text The text to translate
     * @param from The language to translate from
     * @param to The language to translate to
     */
    override suspend fun translate(text: String, from: Language?, to: Language?): String {
        onLanguageChanged(from, to)

        return suspendCancellableCoroutine { continuation ->
            bridge.setOnResult { result ->

                when {
                    result == null -> {
                        continuation.resume("null")
                    }
                    result.isGenderResponse() -> {
                        val response = json.decodeFromString<GenderResponse>(result)
                        continuation.resume(
                            response.masculine
                        )
                    }
                    else -> {
                        val response = json.decodeFromString<Response>(result)

                        continuation.resume(
                            response.result
                        )
                    }
                }
            }
            onUi { _driverView.evaluateJavascript(translationScript(text)) }
        }
    }

    /**
     * releases all resources used by the driver
     */
    override fun release() {
        onUi { _driverView.release() }
    }

    /**
     * The javascript bridge to use
     * in android case
     */
    class JavaScriptBridge(private var onResult: (String?) -> Unit) {

        /**
         * The javascript interface that the evaluate
         * script call in bridge
         */
        @Suppress("unused")
        @android.webkit.JavascriptInterface
        fun onComplete(result: String?) {
            onResult(result)
        }

        /**
         * Sets the on result callback
         * @param callback The callback to set
         * collect the result
         */
        fun setOnResult(callback: (String?) -> Unit) {
            onResult = callback
        }
    }
}