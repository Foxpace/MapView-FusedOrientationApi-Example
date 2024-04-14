package com.tomasrepcik.mapview_fusedorientationapi_example

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.DeviceOrientation
import com.google.android.gms.location.DeviceOrientationListener
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrientationViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel(),
    DeviceOrientationListener {

    private val orientationRepo = OrientationRepo(context)

    private val _bearing: MutableStateFlow<CameraPositionState> = MutableStateFlow(
        CameraPositionState()
    )
    var bearing = _bearing.asStateFlow()


    fun start() = orientationRepo.addListener(this)

    fun stop() = orientationRepo.removeListenerIfExists()


    override fun onDeviceOrientationChanged(orientation: DeviceOrientation) {
        viewModelScope.launch(Dispatchers.Main) {
            _bearing.value = CameraPositionState(CameraPosition.builder().apply {
                target(LatLng(49.06144, 20.29798))
                bearing(orientation.headingDegrees)
                zoom(10f)
            }.build())

        }
    }

}