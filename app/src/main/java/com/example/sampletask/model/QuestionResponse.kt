package com.example.sampletask.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionResponse(
    val chapters: List<String>,
    val exams: List<String>,
    val id: String,
    val options: List<Option>,
    val previousYearPapers: List<String>,
    val question: Question,
    val solution: Solution,
    val source: String,
    val subjects: List<String>,
    val type: String,
) : Parcelable