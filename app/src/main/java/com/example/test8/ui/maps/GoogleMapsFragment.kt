package com.example.test8.ui.maps

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.test8.ui.base.BaseFragment
import com.example.test8.utils.BottomSheetHelper
import com.example.test8.data.model.LocationClusterItem
import com.example.test8.utils.LocationHelper
import com.example.test8.utils.LocationPermissionHelper
import com.example.test8.R
import com.example.test8.utils.Resource
import com.example.test8.databinding.FragmentGoogleMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.test8.data.model.LocationData
import com.google.maps.android.clustering.ClusterManager

@AndroidEntryPoint
class GoogleMapsFragment : BaseFragment<FragmentGoogleMapsBinding>(FragmentGoogleMapsBinding::inflate) {

    private val viewModel: MapsViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<LocationClusterItem>
    private lateinit var permissionHelper: LocationPermissionHelper
    private lateinit var locationHelper: LocationHelper
    private lateinit var bottomSheetHelper: BottomSheetHelper

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionHelper.handlePermissionsResult(permissions)
    }

    override fun start() {
        bottomSheetHelper = BottomSheetHelper(binding)
        bottomSheetHelper.setupBottomSheet()
        setupMap()

        observeLocations()
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            googleMap = map
            googleMap.uiSettings.isZoomControlsEnabled = true
            permissionHelper = LocationPermissionHelper(requireContext(), permissionLauncher, googleMap)
            locationHelper = LocationHelper(requireContext(), googleMap)
            bottomSheetHelper = BottomSheetHelper(binding)

            permissionHelper.requestPermissions()
            locationHelper.tryGetUserLocation()
            setupMarkerClustering()
        }
    }

    private fun setupMarkerClustering() {
        clusterManager = ClusterManager(requireContext(), googleMap)
        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)

        clusterManager.setOnClusterItemClickListener { clusterItem ->
            bottomSheetHelper.showLocationDetails(clusterItem.location)
            true
        }
    }

    private fun observeLocations() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.locations.collectLatest { resource ->
                    if (resource is Resource.Success) {
                        displayLocationsOnMap(resource.data)
                    }
                }
            }
        }
    }

    private fun displayLocationsOnMap(locations: List<LocationData>) {
        googleMap.clear()
        clusterManager.clearItems()

        if (locations.isEmpty()) return

        val boundsBuilder = LatLngBounds.Builder()

        locations.forEach { location ->
            val position = LatLng(location.lat, location.lan)
            val clusterItem = LocationClusterItem(location, position, location.title, location.address ?: "")
            clusterManager.addItem(clusterItem)
            boundsBuilder.include(position)
        }

        clusterManager.cluster()

        val bounds = boundsBuilder.build()
        val padding = 100
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }
}
