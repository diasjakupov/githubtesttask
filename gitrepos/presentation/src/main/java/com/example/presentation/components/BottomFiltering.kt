package com.example.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitrepos.presentation.R
import com.example.presentation.screens.reposcreen.remote.SortBy

@Composable
fun BoxScope.BottomFiltering(isVisible: Boolean, selectedFilter: SortBy, onSelect: (SortBy) -> Unit, onConfirm: ()->Unit){

    AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
    ) {
        Surface(
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
        ) {
            Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                        stringResource(R.string.sort_by_label),
                        style = MaterialTheme.typography.titleMedium
                )
                Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    SortBy.entries.forEach {
                        FilterChip(
                                selected = selectedFilter == it,
                                onClick = { onSelect(it) },
                                label = { Text(stringResource(it.nameId)) }
                        )
                    }
                }

                Button(onClick = {
                    onConfirm.invoke()
                }) {
                    Text(stringResource(R.string.apply_filters))
                }
            }
        }
    }
}