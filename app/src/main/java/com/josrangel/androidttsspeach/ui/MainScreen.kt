package com.josrangel.androidttsspeach.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josrangel.androidttsspeach.viewmodel.TextToSpeechViewModel
import java.util.*

@Composable
fun MainScreen(viewModel: TextToSpeechViewModel) {
    val text by viewModel.text.collectAsState()
    val history by viewModel.history.collectAsState()
    val language by viewModel.language.collectAsState()

    val languageOptions = listOf(
        "Español" to Locale("es", "ES"),
        "Inglés" to Locale("en", "US"),
        "Francés" to Locale("fr", "FR")
    )

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 150.dp)) {
            OutlinedTextField(
                value = text,
                onValueChange = viewModel::updateText,
                label = { Text("Ingresa texto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Text("Historial", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(start = 16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(history) { entry ->
                    val date = remember(entry.timestamp) {
                        java.text.SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(entry.timestamp))
                    }
                    Text("• ${entry.text}  —  $date")
                }
            }
        }

        // Botones fijos abajo
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box {
                    Button(onClick = { expanded = true }) {
                        Text(languageOptions.first { it.second == language }.first)
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        languageOptions.forEach { (name, locale) ->
                            DropdownMenuItem(text = { Text(name) }, onClick = {
                                viewModel.setLanguage(locale)
                                expanded = false
                            })
                        }
                    }
                }

                Button(onClick = viewModel::speak, enabled = text.isNotBlank()) {
                    Text("Reproducir")
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Botón de limpiar texto
            Button(
                onClick = { viewModel.updateText("") },
                enabled = text.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpiar texto")
            }
        }
    }
}
