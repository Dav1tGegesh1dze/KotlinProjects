package com.example.test8.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test8.utils.Resource
import com.example.test8.data.model.LocationData
import com.example.test8.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _locations = MutableStateFlow<Resource<List<LocationData>>>(Resource.Loading())
    val locations: StateFlow<Resource<List<LocationData>>> = _locations

    init {
        fetchLocations()
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            _locations.value = Resource.Loading()
            val result = locationRepository.getLocations()
            _locations.value = result
        }
    }
}