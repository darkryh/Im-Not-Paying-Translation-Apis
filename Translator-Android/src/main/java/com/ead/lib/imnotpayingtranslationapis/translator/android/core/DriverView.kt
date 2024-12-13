package com.ead.lib.imnotpayingtranslationapis.translator.android.core

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.ead.lib.imnotpayingtranslationapis.translator.android.util.Thread.onUi

class DriverView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyle : Int=0,
    defStylerRes: Int=0
) : WebView(context,attrs,defStyle,defStylerRes) {
    fun release() {
        onUi {
            stopLoading()
            loadUrl("about:blank")
            removeAllViews()
            destroy()
        }
    }
}