package dev.josed20.pc02moviles2220015022200171.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.josed20.pc02moviles2220015022200171.data.model.Team
import dev.josed20.pc02moviles2220015022200171.data.repository.TeamRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onSaveSuccess: () -> Unit
) {
    var teamName by remember { mutableStateOf("") }
    var logoUrl by remember { mutableStateOf("") }
    var anioFundacion by remember { mutableStateOf("") }
    var titulos by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val repository = TeamRepository()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Registro de Equipo") },
                navigationIcon = {
                    IconButton(onClick = onSaveSuccess) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = teamName,
                onValueChange = { teamName = it },
                label = { Text("Nombre del Equipo") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            OutlinedTextField(
                value = logoUrl,
                onValueChange = { logoUrl = it },
                label = { Text("URL del Logo") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            OutlinedTextField(
                value = anioFundacion,
                onValueChange = { anioFundacion = it },
                label = { Text("Año de Fundación") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            OutlinedTextField(
                value = titulos,
                onValueChange = { titulos = it },
                label = { Text("Títulos") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    if (teamName.isNotBlank()) {
                        isLoading = true
                        errorMessage = null

                        val newTeam = Team(
                            id = "", // Firestore generará el ID
                            nombre = teamName,
                            imagenUrl = logoUrl.ifBlank { "" },
                            anioFundacion = anioFundacion.ifBlank { "" },
                            titulos = titulos.ifBlank { "0" }
                        )

                        val result = repository.addTeam(newTeam)
                        result.onSuccess {
                            isLoading = false
                            onSaveSuccess()
                        }.onFailure { e ->
                            isLoading = false
                            errorMessage = e.message ?: "Error al guardar"
                        }
                    } else {
                        errorMessage = "Complete los campos obligatorios"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar Equipo")
                }
            }
        }
    }
}
