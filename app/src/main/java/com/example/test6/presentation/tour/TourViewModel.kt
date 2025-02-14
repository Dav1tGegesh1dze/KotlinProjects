package com.example.test6.presentation.tour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.Resource
import com.example.test6.data.local.TourItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TourViewModel @Inject constructor(
    private val repository: TourRepository
) : ViewModel() {

    private val _toursState = MutableStateFlow<Resource<List<TourItem>>>(Resource.Loading())
    val toursState: StateFlow<Resource<List<TourItem>>> = _toursState

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int> = _currentPosition

    init {
        fetchTours()
    }

    fun fetchTours() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTours()
                .catch { e ->
                    _toursState.value = Resource.Error(e.message ?: "Unknown error occurred")
                }
                .collect { resource ->
                    _toursState.value = resource
                }
        }
    }

    fun updatePosition(position: Int) {
        _currentPosition.value = position
    }
}
