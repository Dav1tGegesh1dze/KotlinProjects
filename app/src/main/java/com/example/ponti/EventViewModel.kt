package com.example.ponti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ponti.domain.Category
import com.example.ponti.domain.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: PontiRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading())
    val categories: StateFlow<Resource<List<Category>>> = _categories.asStateFlow()

    private val _events = MutableStateFlow<Resource<List<Event>>>(Resource.Loading())
    val events: StateFlow<Resource<List<Event>>> = _events.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _filteredEvents = MutableStateFlow<List<Event>>(emptyList())
    val filteredEvents: StateFlow<List<Event>> = _filteredEvents.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        fetchCategories()
        fetchEvents()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = Resource.Loading()
            val result = repository.getCategories()
            _categories.value = result

            if (result is Resource.Success && result.data.isNotEmpty()) {
                if (_selectedCategory.value == null) {
                    selectCategory(result.data[0].name)
                }
            }
        }
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            _events.value = Resource.Loading()
            val result = repository.getEvents()
            _events.value = result

            _selectedCategory.value?.let { filterEventsByCategory(it) }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        filterEventsByCategory(category)
    }

    private fun filterEventsByCategory(category: String) {
        val data = (_events.value as? Resource.Success<List<Event>>)?.data
        data?.let {
            _filteredEvents.value = it.filter { event -> event.category == category }
        }
    }
}