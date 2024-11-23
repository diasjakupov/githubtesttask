package com.example.data.utils

import com.example.domain.oauth.entity.OAuthCredentials
import com.google.firebase.auth.OAuthCredential


fun OAuthCredential?.map() = OAuthCredentials(
        accessToken = this?.accessToken.orEmpty(),
)

