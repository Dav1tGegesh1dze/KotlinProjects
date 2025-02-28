package com.example.test8.utils

import com.example.test8.data.model.LocationData
import com.example.test8.databinding.FragmentGoogleMapsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetHelper(private val binding: FragmentGoogleMapsBinding) {

    private var bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(binding.locationBottomSheet.bottomSheet)

     fun setupBottomSheet() {

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun showLocationDetails(location: LocationData) {
        with(binding.locationBottomSheet) {
            tvCoordinates.text = "Lat: ${location.lat}, Lon: ${location.lan}"
            tvTitle.text = location.title
            tvAddress.text = location.address
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
