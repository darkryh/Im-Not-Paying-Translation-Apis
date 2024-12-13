package com.ead.lib.imnotpayingtranslationapis.translator.kotlin.core

import dev.datlag.kcef.KCEFBrowser
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.WindowConstants

object Instance {
    fun doWork(kcefBrowser: KCEFBrowser) {
        val frame = JFrame("Instance")
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.contentPane.add(kcefBrowser.uiComponent)
        frame.preferredSize = Dimension(0, 0)
        frame.pack()
        frame.isVisible = false
    }
}