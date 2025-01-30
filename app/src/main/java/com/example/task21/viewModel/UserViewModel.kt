package com.example.task21.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task21.Person
import com.example.task21.ProtoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: ProtoRepository) : ViewModel() {

    val personFlow: Flow<Person> = repository.personFlow

    fun updatePerson(firstName: String, lastName: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePerson(firstName, lastName, email)
        }
    }
}
