package com.example.sampletask.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question(
    @ColumnInfo(name = "question_text")
    val text: String
) : Parcelable