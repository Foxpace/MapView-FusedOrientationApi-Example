package com.tomasrepcik.mapview_fusedorientationapi_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tomasrepcik.mapview_fusedorientationapi_example.ui.theme.MapViewFusedOrientationApiExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapViewFusedOrientationApiExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val orientationViewModel: OrientationViewModel = hiltViewModel()
                    OrientationUi(state = orientationViewModel.cameraPositionState.collectAsState().value,
                        onStart = { orientationViewModel.start() },
                        onStop = { orientationViewModel.stop() })
                }
            }
        }
    }
}

