package com.example.sampletask.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    @ColumnInfo(name = "option_id")
    val id: String,
    val isCorrect: Boolean,
    @ColumnInfo(name = "option_text")
    val text: String
) : Parcelable