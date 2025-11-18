package com.josrangel.androidttsspeach.data.model

data class TextEntry(
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)