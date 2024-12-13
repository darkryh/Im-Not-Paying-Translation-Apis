package com.ead.lib.imnotpayingtranslationapis.translator.android.scripts

fun translationScript(text: String) = """
    (async function() {
        resetTextarea();

        setTextareaContent(`$text`);

        const result = await waitForResult();

        resetTextarea();

        if (result) {
            window.Android.onComplete(JSON.stringify(result));
        } else {
            window.Android.onComplete("null");
        }
    })();
""".trimIndent()