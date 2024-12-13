package com.ead.lib.imnotpayingtranslationapis.desktopapp.util

object QueryValidator {
    fun execute(newQuery: String?, lastQuery: String?): String? {
        val sanitizedQuery = newQuery?.trim() ?: return null
        if (sanitizedQuery.isBlank() || sanitizedQuery.equals(lastQuery, ignoreCase = true)) {
            return null
        }
        return sanitizedQuery
    }
}