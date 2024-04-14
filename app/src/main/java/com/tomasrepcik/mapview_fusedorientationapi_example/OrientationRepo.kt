package com.tomasrepcik.mapview_fusedorientationapi_example

import android.content.Context
import android.util.Log
import com.google.android.gms.location.DeviceOrientationListener
import com.google.android.gms.location.DeviceOrientationRequest
import com.google.android.gms.location.FusedOrientationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class OrientationRepo(context: Context) {

    private var listener: DeviceOrientationListener? = null
    private val fusedOrientationProviderClient: FusedOrientationProviderClient =
        LocationServices.getFusedOrientationProviderClient(context)

    fun addListener(
        listener: OrientationViewModel,
        executor: ExecutorService = Executors.newSingleThreadExecutor()
    ) {
        removeListenerIfExists()
        this.listener = listener
        val request =
            DeviceOrientationRequest.Builder(DeviceOrientationRequest.OUTPUT_PERIOD_DEFAULT).build()
        fusedOrientationProviderClient.requestOrientationUpdates(request, executor, listener)
            .addOnSuccessListener {
                Log.i(TAG, "Successfully added new orientation listener")
            }.addOnFailureListener { e: Exception? ->
                Log.e(TAG, "Failed to add new orientation listener", e)
            }
    }

    fun removeListenerIfExists() = listener?.let {
        Log.i(TAG, "Removing active orientation listener")
        fusedOrientationProviderClient.removeOrientationUpdates(it)
        listener = null
    }

    companion object {
        const val TAG = "OrientationRepo"
    }

}