package com.josrangel.androidttsspeach.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.josrangel.androidttsspeach.data.model.TextEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class TextToSpeechViewModel(app: Application) : AndroidViewModel(app), TextToSpeech.OnInitListener {

    private val tts = TextToSpeech(app, this)
    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text

    private val _language = MutableStateFlow(Locale("es", "ES"))
    val language: StateFlow<Locale> = _language

    private val _history = MutableStateFlow<List<TextEntry>>(emptyList())
    val history: StateFlow<List<TextEntry>> = _history

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = _language.value
        }
    }

    fun updateText(newText: String) {
        _text.value = newText
    }

    fun speak() {
        val textToSpeak = _text.value
        if (textToSpeak.isNotBlank()) {
            tts.language = _language.value
            tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
            addToHistory(textToSpeak)
        }
    }

    private fun addToHistory(text: String) {
        viewModelScope.launch {
            _history.value = listOf(TextEntry(text)) + _history.value
        }
    }

    fun setLanguage(locale: Locale) {
        _language.value = locale
        tts.language = locale
    }

    override fun onCleared() {
        super.onCleared()
        tts.shutdown()
    }
}