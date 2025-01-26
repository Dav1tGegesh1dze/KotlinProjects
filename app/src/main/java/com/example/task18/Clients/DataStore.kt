import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


val Application.dataStore by preferencesDataStore(name = "user_prefs")

object DataStoreManager {
    private val emailKey = stringPreferencesKey("email")
    private val passwordKey = stringPreferencesKey("password")
    private val rememberKey = booleanPreferencesKey("remember_login")

    suspend fun getStoredEmail(application: Application): String {
        val prefs = application.dataStore.data.first()
        return prefs[emailKey] ?: "User"
    }

    suspend fun getStoredPassword(application: Application): String? {
        val prefs = application.dataStore.data.first()
        return prefs[passwordKey]
    }

    suspend fun saveUserToPreferences(application: Application, email: String, password: String) {
        application.dataStore.edit { prefs ->
            prefs[emailKey] = email
            prefs[passwordKey] = password
        }
    }

    suspend fun saveRememberMe(application: Application, rememberMe: Boolean) {
        application.dataStore.edit { prefs ->
            prefs[rememberKey] = rememberMe
        }
    }

    suspend fun clearAllData(application: Application) {
        application.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
