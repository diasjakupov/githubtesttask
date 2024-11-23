package com.example.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.presentation.uimodels.UserUiModel


@Composable
fun UserCard(user: UserUiModel) {
    Card(
            modifier = Modifier
                    .height(250.dp),
            shape = RoundedCornerShape(8.dp),
    ) {
        Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                    model = user.profilePictureUrl,
                    contentDescription = null,
                    placeholder = painterResource(com.example.gitrepos.presentation.R.drawable.person),
                    modifier = Modifier
                            .size(64.dp)
                            .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                    text = user.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                    text = user.bio.ifBlank { stringResource(com.example.gitrepos.presentation.R.string.no_bio) },
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                    text = "${user.followers} followers",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
            )
        }
    }
}

@[Preview(showBackground = true) Composable]
fun UserGridPreview() {
    UserCard(
            UserUiModel(
                    profilePictureUrl = "https://via.placeholder.com/150",
                    name = "Alice",
                    bio = "Loves coffee and cats.",
                    followers = 1200
            )
    )
}
