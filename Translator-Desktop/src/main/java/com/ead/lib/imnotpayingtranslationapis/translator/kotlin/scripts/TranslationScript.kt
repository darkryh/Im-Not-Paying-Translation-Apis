package com.ead.lib.imnotpayingtranslationapis.translator.kotlin.scripts

fun translationScript(text: String) = """
    const inputEvent = new Event('input', {
        bubbles: true,
        cancelable: true
    });
    
    function resetTextarea() {
        const textarea = document.querySelector('textarea');
        if (textarea) {
            textarea.textContent = null;
            textarea.value = "";
            textarea.dispatchEvent(inputEvent);
        }
    }
    
    function setTextareaContent(text) {
        const textarea = document.querySelector('textarea');
        if (textarea) {
            textarea.textContent = text;
            textarea.value = text;
            textarea.dispatchEvent(inputEvent);
        }
    }
    
    function getTextareasContent() {
        const feminineDiv = Array.from(document.querySelectorAll('div.lRu31'))
            .find(div => div.querySelector('div.wcMo7b')?.textContent.includes('(feminine)'));
        const masculineDiv = Array.from(document.querySelectorAll('div.lRu31'))
            .find(div => div.querySelector('div.wcMo7b')?.textContent.includes('(masculine)'));
    
        const content = {
            feminine: feminineDiv?.querySelector('span.HwtZe')?.textContent?.trim() || null,
            masculine: masculineDiv?.querySelector('span.HwtZe')?.textContent?.trim() || null
        };
    
        return content;
    }
    
    function waitForResult() {
        return new Promise((resolve) => {
            let attempts = 0;
            const maxAttempts = 20;
    
            const interval = setInterval(() => {
                const textareas = document.querySelectorAll('textarea');
                if (textareas.length > 1 && textareas[1]) {
                    const targetTextarea = textareas[1];
                    clearInterval(interval);
    
                    const content = targetTextarea.textContent;
    
                    targetTextarea.textContent = null;
                    targetTextarea.value = "";
                    targetTextarea.dispatchEvent(inputEvent);
    
                    resolve({ result: content || null });
                    return;
                }
    
                const versions = getTextareasContent();
                const hasFeminine = versions.feminine && versions.feminine.length > 0;
                const hasMasculine = versions.masculine && versions.masculine.length > 0;
    
                if (hasFeminine || hasMasculine) {
                    clearInterval(interval);
                    resolve(versions);
                    return;
                }
    
                if (++attempts >= maxAttempts) {
                    clearInterval(interval);
                    resolve(null);
                }
            }, 250);
        });
    }
    
    (async function() {
        resetTextarea();
    
        setTextareaContent(`$text`);
    
        const result = await waitForResult();
    
        window.cefQuery({
            request: JSON.stringify(result),
            onSuccess: function(response) {
                console.log("Sent message a Kotlin:", response);
            },
            onFailure: function(error_code, error_message) {
                console.log("Error message a Kotlin:", error_code, error_message);
            }
        });
    
        resetTextarea();
    })();
""".trimIndent()