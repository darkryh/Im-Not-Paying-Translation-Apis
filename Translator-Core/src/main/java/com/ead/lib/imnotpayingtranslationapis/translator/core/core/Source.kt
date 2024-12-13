package com.ead.lib.imnotpayingtranslationapis.translator.core.core

object Source {
    private const val BASE_URL = "https://translate.google.com/?sl="
    fun getTranslateMode(from: Language, to: Language) = "$BASE_URL${from.code}&tl=${to.code}&op=translate"
}