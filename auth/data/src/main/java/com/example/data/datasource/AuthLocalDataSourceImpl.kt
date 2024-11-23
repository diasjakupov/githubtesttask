package com.example.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.oauth.AuthLocalDataSource
import com.example.domain.oauth.AuthLocalDataSource.Companion.DATASTORE_NAME
import com.example.domain.oauth.AuthLocalDataSource.Companion.OATH_TOKEN
import com.example.domain.oauth.entity.OAuthCredentials
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthLocalDataSourceImpl @Inject constructor(
        @ApplicationContext private val context: Context
): AuthLocalDataSource {
    private val oauthToken = stringPreferencesKey(OATH_TOKEN)
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)


    override suspend fun putOAuthCredentials(creds: OAuthCredentials) {
        context.dataStore.updateData {
            it.toMutablePreferences().apply {
                set(oauthToken, creds.accessToken)
            }
        }
    }

    override suspend fun getOAuthCredentials(): Flow<OAuthCredentials> = context.dataStore.data.map { OAuthCredentials(it[oauthToken].orEmpty()) }

}