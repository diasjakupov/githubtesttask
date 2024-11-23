package com.example.zimrangithub

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.registerReceiver
import com.example.core.network.UnauthorizedInterceptor
import com.example.core.ui.theme.ZimranGithubTheme
import com.example.data.oauth.AuthInterceptor
import com.example.zimrangithub.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZimranGithubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){
                        Navigation()
                    }
                }
            }
        }
    }
}


@Composable
fun HandleLoginBroadcast(context: Context, onLoginRequired: () -> Unit) {
    val loginReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == UnauthorizedInterceptor.ACTION_LOGIN_REQUIRED) {
                    onLoginRequired()
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val intentFilter = IntentFilter(UnauthorizedInterceptor.ACTION_LOGIN_REQUIRED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(loginReceiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            context.registerReceiver(loginReceiver, intentFilter)
        }

        onDispose {
            context.unregisterReceiver(loginReceiver)
        }
    }
}