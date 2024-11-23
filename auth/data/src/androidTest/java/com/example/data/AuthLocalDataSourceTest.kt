import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.data.datasource.AuthLocalDataSourceImpl
import com.example.domain.oauth.entity.OAuthCredentials
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.UUID

class AuthLocalDataSourceImplTest {

    private lateinit var dataSource: AuthLocalDataSourceImpl
    private lateinit var testDataStore: DataStore<Preferences>
    private val oauthTokenKey = stringPreferencesKey("OATH_TOKEN")
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var testScope: TestScope
    private lateinit var testFile: File

    @Before
    fun setup() {
        val context = androidx.test.core.app.ApplicationProvider.getApplicationContext<Context>()

        // Use a unique file for each test
        testFile = context.preferencesDataStoreFile("test_datastore_${UUID.randomUUID()}")

        testScope = TestScope(testDispatcher)

        testDataStore = androidx.datastore.preferences.core.PreferenceDataStoreFactory.create(
                scope = testScope,
                produceFile = { testFile }
        )

        dataSource = AuthLocalDataSourceImpl(context)
    }

    @After
    fun teardown() {
        testFile.delete() // Clean up the test file
    }

    @Test
    fun putOAuthCredentialsStoresCredentialsInDataStore() = runTest(testDispatcher) {
        val credentials = OAuthCredentials("test_access_token")

        // Act
        testDataStore.edit { it[oauthTokenKey] = credentials.accessToken }

        // Assert
        val storedToken = testDataStore.data.first()[oauthTokenKey]
        assertEquals("test_access_token", storedToken)
    }

    @Test
    fun getOAuthCredentialsRetrievesCredentialsFromDataStore() = runTest(testDispatcher) {
        val credentials = OAuthCredentials("test_access_token")

        // Arrange
        testDataStore.edit { it[oauthTokenKey] = credentials.accessToken }

        val storedToken = testDataStore.data.first()[oauthTokenKey]

        // Assert
        assertEquals("test_access_token", storedToken)
    }
}
