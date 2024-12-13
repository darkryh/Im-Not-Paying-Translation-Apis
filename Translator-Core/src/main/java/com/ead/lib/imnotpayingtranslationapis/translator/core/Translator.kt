package com.ead.lib.imnotpayingtranslationapis.translator.core

import com.ead.lib.imnotpayingtranslationapis.translator.core.core.Language
import com.ead.lib.imnotpayingtranslationapis.translator.core.models.Driver

/**
 * Translator class
 */
class Translator(
    /**
     * The driver used to translate text
     * its adapters to the architecture
     * of the used platform
     */
    private val driver: Driver
) {

    /**
     * initialization process for the translator
     * @param from The language to translate from
     * @param to The language to translate to
     */
    suspend fun init(
        from: Language,
        to: Language
    ) {
        if (from == to) throw IllegalArgumentException("From and To languages can't be the same")
        driver
            .init(
                from,
                to
            )
    }

    /**
     * @param text The text to translate
     * from and to still not working
     * in this function will change
     * in the future
     */
    suspend fun translate(
        text: String,
        from : Language? = null,
        to: Language? = null
    ) : String {
        if (from == to && from != null) throw IllegalArgumentException("From and To languages can't be the same")
        return driver
            .translate(
                text,
                from,
                to
            )
    }

    /**
     * Releases all resources used by the translator
     */
    fun release() {
        driver
            .release()
    }
}