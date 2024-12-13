package com.ead.lib.imnotpayingtranslationapis.translator.core.core.system.extensions

fun String.isGenderResponse(): Boolean {
    return !startsWith("{\"result\"")
}