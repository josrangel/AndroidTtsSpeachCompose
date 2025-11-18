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

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = viewModel::updateText,
            label = { Text("Ingresa texto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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

        Spacer(Modifier.height(20.dp))
        Text("Historial", style = MaterialTheme.typography.labelLarge)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(history) { entry ->
                Text("• ${entry.text}")
            }
        }
    }
}