package com.example.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.R


@[Composable OptIn(ExperimentalMaterial3Api::class)]
fun NetworkComponent(
        isLoading: Boolean = false,
        errorMessage: Int? = null,
        errorMessageString: String? = null,
        onCancel: ()->Unit,
        content: @Composable () -> Unit
){
    Box(contentAlignment = Alignment.Center) {
        if(isLoading){
            BasicAlertDialog(onDismissRequest = onCancel, modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                CircularProgressIndicator()
            }
        }
        if(errorMessage != null || errorMessageString != null){
            BasicAlertDialog(onDismissRequest = onCancel, modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp))
                    .padding(10.dp)) {
                Column() {
                    Text(text = stringResource(R.string.error_title), style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(12.dp))
                    Text(errorMessageString ?: stringResource(errorMessage!!))
                }
            }
        }
        content()
    }
}