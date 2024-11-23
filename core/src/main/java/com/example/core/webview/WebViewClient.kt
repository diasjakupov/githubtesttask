package com.example.core.webview

import android.webkit.WebView
import android.webkit.WebViewClient


class WVClient(
        private val onFinish: () -> Unit
): WebViewClient(){

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        onFinish.invoke()
    }
}