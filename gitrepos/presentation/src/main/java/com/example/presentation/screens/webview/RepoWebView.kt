package com.example.presentation.screens.webview

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.core.base.ActionCallback
import com.example.core.webview.WVClient
import com.example.presentation.screens.reposcreen.RepoActions

@Composable
fun RepoWebView(url: String, callback: ActionCallback<RepoActions>){
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            )
            this.webViewClient = WVClient{
                callback.sendAction(RepoActions.MarkAsViewed(url))
            }
        }
    }, update = {
        it.loadUrl(url)
    })
}


