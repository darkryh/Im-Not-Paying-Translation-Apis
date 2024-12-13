package com.ead.lib.imnotpayingtranslationapis.translator.android.core.extensions

import android.webkit.WebView

fun WebView.evaluateJavascript(script: String) {
    evaluateJavascript(script,null)
}