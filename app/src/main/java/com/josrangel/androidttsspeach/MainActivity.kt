package com.josrangel.androidttsspeach

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.josrangel.androidttsspeach.ui.MainScreen
import com.josrangel.androidttsspeach.ui.theme.AndroidTtsSpeachTheme
import com.josrangel.androidttsspeach.viewmodel.TextToSpeechViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val textToSpeechViewModel: TextToSpeechViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            AndroidTtsSpeachTheme {
                MainScreen(textToSpeechViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val fakeViewModel = TextToSpeechViewModel(Application())
    MainScreen(viewModel = fakeViewModel)
}