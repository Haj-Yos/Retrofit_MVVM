package com.example.patients.ui.consultations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patients.data.model.Consultation
import com.example.patients.ui.Consultations.ConsultationsUiState
import com.example.patients.ui.patients.ErrorContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultationsScreen(
    patientId: String,
    patientName: String,
    onBack: () -> Unit,
    // key = patientId : un ViewModel distinct par patient
    viewModel: ConsultationViewModel = viewModel(
        key = patientId,
        factory = ConsultationViewModel.factory(patientId)
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consultations · $patientName") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.loadConsultations() }) {
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
                is ConsultationsUiState.Loading ->
                    CircularProgressIndicator(Modifier.align(Alignment.Center))

                is ConsultationsUiState.Error ->
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.loadConsultations() },
                        modifier = Modifier.align(Alignment.Center)
                    )

                is ConsultationsUiState.Success ->
                    ConsultationList(state.consultations)
            }
        }
    }
}

@Composable
fun ConsultationList(consultations: List<Consultation>) {
    if (consultations.isEmpty()) {
        Text("Aucune consultation", modifier = Modifier.padding(24.dp))
    } else {
        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(consultations, key = { it.id }) { consultation ->
                ConsultationCard(consultation)
            }
        }
    }
}

@Composable
fun ConsultationCard(consultation: Consultation) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = consultation.date?.take(10) ?: "Date inconnue",
                style = MaterialTheme.typography.titleMedium
            )
            consultation.motif?.let { Text("Motif : $it") }
            consultation.diagnostic?.let { Text("Diagnostic : $it") }
            consultation.notes?.let {
                Text(it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}