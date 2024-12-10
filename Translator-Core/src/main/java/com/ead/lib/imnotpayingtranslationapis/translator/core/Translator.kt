package com.ead.lib.imnotpayingtranslationapis.translator.core

import com.ead.lib.imnotpayingtranslationapis.translator.core.models.Robot

class Translator(private val robot: Robot) {

    private val isReady = false

    suspend fun ini() = robot.init()
}