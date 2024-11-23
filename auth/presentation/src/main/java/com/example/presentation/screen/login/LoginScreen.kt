package com.example.presentation.screen.login

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.theme.ZimranGithubTheme
import com.example.auth.presentation.R
import com.example.core.base.ActionCallback
import com.example.presentation.screen.viewModel.LoginActions


@Composable
fun GitHubLoginScreen(state: LoginState, callback: ActionCallback<LoginActions>) {
    var animatedScale by remember { mutableFloatStateOf(0f) }
    val context = LocalContext.current as Activity


    val scale by animateFloatAsState(targetValue = animatedScale, label = "")

    LaunchedEffect(Unit) {
        animatedScale = 1f
    }
    LaunchedEffect(Unit) {
        callback.sendAction(LoginActions.NavigateIfLoggedIn)
    }

    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.scale(scale)
        ) {
            Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.github),
                    contentDescription = "GitHub Icon",
                    modifier = Modifier
                            .size(80.dp)
            )

            Text(
                    text = stringResource(id = R.string.login_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
            )

            Button(
                    onClick = { callback.sendAction(LoginActions.OnLoginClick(context)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxWidth(0.7f)
            ) {
                Text(
                        text = stringResource(id = R.string.login_btn_text),
                        color = Color.White,
                        fontSize = 16.sp
                )
            }
        }
    }
}

@[Preview(showBackground = true) Composable]
fun GitHubLoginScreenPreview() {
    ZimranGithubTheme {
        GitHubLoginScreen(state = LoginState(), callback = {})
    }
}

