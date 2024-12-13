package com.ead.lib.imnotpayingtranslationapis.translator.kotlin

import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Language
import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Source
import com.ead.lib.imnotpayingtranslationapis.translator.core.core.system.extensions.isGenderResponse
import com.ead.lib.imnotpayingtranslationapis.translator.core.models.Driver
import com.ead.lib.imnotpayingtranslationapis.translator.core.models.GenderResponse
import com.ead.lib.imnotpayingtranslationapis.translator.core.models.Response
import com.ead.lib.imnotpayingtranslationapis.translator.kotlin.core.Instance
import com.ead.lib.imnotpayingtranslationapis.translator.kotlin.scripts.translationScript
import dev.datlag.kcef.KCEF
import dev.datlag.kcef.KCEFBrowser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.browser.CefMessageRouter
import org.cef.browser.CefRendering
import org.cef.callback.CefQueryCallback
import org.cef.handler.CefLoadHandlerAdapter
import org.cef.handler.CefMessageRouterHandlerAdapter
import kotlin.coroutines.resume

/**
 * The driver used to translate text
 * in desktop platform
 */
class DesktopDriver : Driver {

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
     * The driver view to use
     * translation process in desktop
     */
    private lateinit var instance: KCEFBrowser

    /**
     * message router to use
     * as a bridge between the driver
     */
    private lateinit var messageRouter: CefMessageRouter

    /**
     * The json to use to parse the response
     */
    private val json = Json { ignoreUnknownKeys = true }

    /**
     * The javascript bridge to use
     */
    private val bridge = QueryBridge()


    /**
     * object interoperability to use
     * between the driver and the javascript
     */
    private val cefMessageRouterHandlerAdapter = object : CefMessageRouterHandlerAdapter() {
        override fun onQuery(
            browser: CefBrowser?,
            frame: CefFrame?,
            queryId: Long,
            request: String?,
            persistent: Boolean,
            callback: CefQueryCallback?
        ): Boolean {
            if (request != null) {

                bridge.emitResult(request)
                callback?.success(request)

                return true
            }
            return false
        }
    }


    /**
     * initialization process for the translator
     * in desktop platform
     */
    override suspend fun init(from: Language, to: Language) {
        this._from = from
        this._to = to

        suspendCancellableCoroutine { continuation ->
            runBlocking {
                var initialized = false
                var download = -1

                launch(Dispatchers.IO) {
                    KCEF.init(
                        builder = {
                            progress {
                                onInitialized {
                                    initialized = true
                                }
                                onDownloading {
                                    download = it.toInt()
                                }
                            }
                        },
                        onError = {
                            it?.printStackTrace()
                        }
                    )
                }

                while (!initialized) {
                    if (download > -1) { println("Downloading: $download%") }
                    else { println("Initializing please wait...") }
                    Thread.sleep(100)
                }

                messageRouter = CefMessageRouter.create()

                val client = KCEF.newClientBlocking()
                client.addMessageRouter(messageRouter)

                instance = client.createBrowser(
                    Source.getTranslateMode(from, to),
                    CefRendering.DEFAULT,
                    false
                )

                client.addLoadHandler(object : CefLoadHandlerAdapter() {
                    override fun onLoadEnd(browser: CefBrowser?, frame: CefFrame?, httpStatusCode: Int) {
                        super.onLoadEnd(browser, frame, httpStatusCode)
                        if (initializedDriver) return
                        initializedDriver = true
                        continuation.resume(Unit)
                    }
                })

                messageRouter.addHandler(cefMessageRouterHandlerAdapter,true)
            }

            Instance.doWork(instance)
        }
    }


    /**
     * @param from The language to translate from
     * @param to The language to translate to
     */
    override suspend fun translate(text: String, from: Language?, to: Language?): String {
        return suspendCancellableCoroutine { continuation ->
            bridge.setOnResult { result ->
                println("Result: $result")
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
            instance.evaluateJavaScript(translationScript(text)) {}
        }
    }


    /**
     * releases all resources used by the driver
     */
    override fun release() {
        messageRouter.removeHandler(cefMessageRouterHandlerAdapter)
        KCEF.disposeBlocking()
    }

    /**
     * The javascript bridge to use
     */
    class QueryBridge {
        /**
         * Sets the on result callback
         * @param callback The callback to set
         * collect the result
         */
        private var onResult: ((String?) -> Unit)? = null

        /**
         * Sets the on result callback
         * @param callback The callback to set
         * collect the result
         */
        fun setOnResult(callback: (String?) -> Unit) {
            onResult = callback
        }

        /**
         * Emits the result to the callback
         * @param result The result to emit
         */
        fun emitResult(result: String?) {
            onResult?.invoke(result)
        }
    }
}