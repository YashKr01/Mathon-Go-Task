package com.example.sampletask.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    val text: String
) : Parcelable