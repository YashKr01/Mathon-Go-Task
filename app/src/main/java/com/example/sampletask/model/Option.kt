package com.example.sampletask.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    val id: String,
    val isCorrect: Boolean,
    val text: String
) : Parcelable