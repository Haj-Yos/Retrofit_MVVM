package com.example.patients

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patients.di.AppContainer
import com.example.patients.ui.patients.ErrorContent
import com.example.patients.ui.patients.PatientList
import com.example.patients.ui.patients.PatientViewModel
import com.example.patients.ui.patients.PatientsUiState

class PatientsApplication : Application() {
    // Créé une seule fois, partagé par toute l'application
    val container by lazy { AppContainer() }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientsScreen(
    viewModel: PatientViewModel = viewModel(factory = PatientViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Patients") },
                actions = {
                    IconButton(onClick = { viewModel.loadPatients() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualiser")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is PatientsUiState.Loading ->
                    CircularProgressIndicator(Modifier.align(Alignment.Center))

                is PatientsUiState.Error ->
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.loadPatients() },
                        modifier = Modifier.align(Alignment.Center)
                    )

                is PatientsUiState.Success ->
                    PatientList(patients = state.patients)
            }
        }
    }
}


