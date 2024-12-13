package com.ead.lib.imnotpayingtranslationapis.translator.android.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.CountDownLatch

object Thread {

    private val handler = Handler(Looper.getMainLooper())

    fun onUi(action: () -> Unit) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            handler.post(action)
        } else {
            action.invoke()
        }
    }

    fun <T> letOnUi(action: () -> T): T {
        return if (Looper.myLooper() != Looper.getMainLooper()) {
            val latch = CountDownLatch(1)
            var result: T? = null
            handler.post {
                try {
                    result = action.invoke()
                } finally {
                    latch.countDown()
                }
            }
            latch.await()
            result ?: throw IllegalStateException("Action returned null result")
        } else {
            action.invoke()
        }
    }
}

