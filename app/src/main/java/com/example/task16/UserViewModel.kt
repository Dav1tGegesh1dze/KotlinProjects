package com.example.task16

import kotlinx.serialization.json.Json
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.task16.ChildRecycler.Field
import com.example.task16.ParentRecycler.FieldGroup

class UserViewModel : ViewModel() {
    private val jsonString = """
            [
             [
              {
                "field_id": 1,
                "hint": "UserName",
                "icon": ""
              },
              {
                "field_id": 2,
                "hint": "Email",
                "icon": ""
              },
              {
                "field_id": 3,
                "hint": "phone",
                "icon": ""
              }
             ],
             [
              {
                "field_id": 4,
                "hint": "FullName",
                "icon": ""
              },
              {
                "field_id": 14,
                "hint": "Jemali",
                "icon": ""
              },
              {
                "field_id": 89,
                "hint": "Birthday",
                "icon": ""
              },
              {
                "field_id": 898,
                "hint": "Gender",
                "icon": ""
              }
            ],
            [
              {
                "field_id": 435,
                "hint": "App",
                "icon": ""
              },
              {
                "field_id": 134,
                "hint": "Passcode",
                "icon": ""
              }
            ]
            ]
        """.trimIndent()

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }


    val fieldGroups: List<FieldGroup> = parseJson()

    private fun parseJson(): List<FieldGroup> {
        return try {
            val parsedData: List<List<Field>> = json.decodeFromString(jsonString)
            Log.d("FViewModel", "Parsed Data: $parsedData")


            parsedData.mapIndexed { index, fields ->
                FieldGroup(
                    groupId = index + 1,
                    fields = fields
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}