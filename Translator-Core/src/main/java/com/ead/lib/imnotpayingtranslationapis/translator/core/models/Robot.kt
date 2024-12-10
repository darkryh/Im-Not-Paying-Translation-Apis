package com.ead.lib.imnotpayingtranslationapis.translator.core.models

interface Robot {

    suspend fun init()

    suspend fun evaluateJavascriptCode(script : String) : String

    suspend fun loadUrl(url: String) : Unit?

    //suspend fun getInterceptionUrl(url: String, verificationRegex: Regex, endingRegex: Regex,jsCode : String? = null,client: OkHttpClient,configData: Configuration.Data) : String?

    fun release()
}