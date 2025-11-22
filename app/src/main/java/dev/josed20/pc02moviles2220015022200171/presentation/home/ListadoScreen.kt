package dev.josed20.pc02moviles2220015022200171.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.josed20.pc02moviles2220015022200171.data.model.Team
import dev.josed20.pc02moviles2220015022200171.data.repository.TeamRepository

@Composable
fun ListadoScreen(
    onNavigateToRegistro: () -> Unit
) {
    val repository = TeamRepository()
    var teams by remember { mutableStateOf<List<Team>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Cargar datos automáticamente al entrar
    LaunchedEffect(Unit) {
        repository.getTeams()
            .onSuccess {
                teams = it
                isLoading = false
            }
            .onFailure {
                isLoading = false
            }
    }

    Scaffold(
        floatingActionButton = {
            // Botón flotante para ir al registro (Como pide el PDF "Nuevo Registro")
            ExtendedFloatingActionButton(
                onClick = onNavigateToRegistro,
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Text("Nuevo Registro")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Equipos",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.Black)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(teams) { team ->
                        TeamCard(team)
                    }
                }
            }
        }
    }
}

@Composable
fun TeamCard(team: Team) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Imagen del equipo (Izquierda)
            AsyncImage(
                model = team.imagenUrl,
                contentDescription = "Logo",
                modifier = Modifier
                    .size(50.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 2. Nombre y Año (Centro)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = team.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = team.anioFundacion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // 3. Títulos (Derecha - Grande)
            Text(
                text = team.titulos,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}