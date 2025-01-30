package com.example.task21

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.map

private val Context.personDataStore: DataStore<Person> by dataStore(
    fileName = "person_prefs.pb",
    serializer = MySerializer()
)

class ProtoRepository(private val context: Context) {
    private val dataStore = context.personDataStore

    val personFlow = dataStore.data.map { it }

    suspend fun updatePerson(firstName: String, lastName: String, email: String) {
        dataStore.updateData { person ->
            person.toBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .build()
        }
    }
}
