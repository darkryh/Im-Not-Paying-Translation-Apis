package com.ead.lib.imnotpayingtranslationapis.translator.core.models

import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Language

/**
 * The driver used to translate text
 */
interface Driver {
    /**
     * The language to translate from
     */
    var _from: Language?

    /**
     * The language to translate to
     */
    var _to: Language?

    /**
     * initialization process for the translator
     * @param from The language to translate from
     * @param to The language to translate to
     */
    suspend fun init(from: Language, to: Language)

    /**
     * @param text The text to translate
     * @param from The language to translate from
     * @param to The language to translate to
     */
    suspend fun translate(text: String, from: Language?, to: Language?) : String

    /**
     * releases all resources used by the driver
     */
    fun release()
}