package com.ead.lib.imnotpayingtranslationapis.translator.core.models

import kotlinx.serialization.Serializable

@Serializable
data class GenderResponse(
    val feminine : String,
    val masculine : String
)