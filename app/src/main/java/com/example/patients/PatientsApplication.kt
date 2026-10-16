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
import com.example.patients.ui.patients.PatientsScreen

class PatientsApplication : Application() {
    // Créé une seule fois, partagé par toute l'application
    val container by lazy { AppContainer() }
}



