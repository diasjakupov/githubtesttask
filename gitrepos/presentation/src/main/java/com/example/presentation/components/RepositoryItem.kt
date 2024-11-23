package com.example.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.theme.Golden
import com.example.core.ui.theme.ZimranGithubTheme
import com.example.presentation.uimodels.RepositoryUiModel

@Composable
fun RepositoryItem(repository: RepositoryUiModel,
                   onClick: (repository: RepositoryUiModel) -> Unit = {},
                   onUsernameClick: (repository: RepositoryUiModel) -> Unit = {}) {
    Column(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
    ) {
        Text(
                text = stringResource(com.example.gitrepos.presentation.R.string.item_author, repository.author.orEmpty()),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clickable { onUsernameClick.invoke(repository) },
                textDecoration = TextDecoration.Underline,
                color = Color.Gray
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
                text = "${repository.title.orEmpty()} ${if (repository.isLocallySaved) stringResource(com.example.gitrepos.presentation.R.string.saved_sign) else ""}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(4.dp))


        Text(
                modifier = Modifier.clickable { onClick(repository) },
                text = repository.description.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 3
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Stars",
                        tint = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                        text = repository.stars.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                        painter = painterResource(id = com.example.gitrepos.presentation.R.drawable.git_fork),
                        contentDescription = "Forks",
                        tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                        text = repository.forks.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

class RepositoryUiModelProvider : PreviewParameterProvider<RepositoryUiModel> {

    override val values = sequenceOf(
            RepositoryUiModel(
                    id = "1jrheiuw-21rnwf",
                    author = "JaneDoe",
                    title = "Sample GitHub Repository",
                    description = "A sample repository demonstrating best practices in Android development with Jetpack Compose.",
                    stars = 1234,
                    forks = 567,
                    url = "",
                    isLocallySaved = true,
                    datetime = null,
                    authorUrl = ""
            ),
            RepositoryUiModel(
                    id = "jkdhgkjd2-21rnwf",
                    author = "JohnSmith",
                    title = "Compose UI Library",
                    description = "A UI library for building Compose components with ease. Includes customizable buttons, lists, and more.",
                    stars = 4321,
                    forks = 210,
                    url = "",
                    datetime = null,
                    authorUrl = ""
            )
    )
}

@[Preview(showBackground = true) Composable]
fun RepositoryItemPreview(
        @PreviewParameter(RepositoryUiModelProvider::class) repository: RepositoryUiModel
) {
    ZimranGithubTheme {
        RepositoryItem(repository = repository)
    }
}