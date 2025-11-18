package com.josrangel.androidttsspeach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.josrangel.androidttsspeach.ui.MainScreen
import com.josrangel.androidttsspeach.ui.theme.AndroidTtsSpeachTheme
import com.josrangel.androidttsspeach.viewmodel.TextToSpeechViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val textToSpeechViewModel: TextToSpeechViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTtsSpeachTheme {
                MainScreen(textToSpeechViewModel)
            }
        }
    }
}