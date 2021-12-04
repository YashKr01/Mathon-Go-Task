package com.example.sampletask.model

import androidx.room.Entity

@Entity
data class QuestionResponse(
    val approximateTimeRequired: Any,
    val category: Any,
    val chapters: List<String>,
    val classes: List<Any>,
    val concepts: List<Any>,
    val correctValue: Any,
    val exams: List<String>,
    val helperText: Any,
    val id: String,
    val imageBaseUrl: Any,
    val level: Any,
    val microConcepts: List<Any>,
    val options: List<Option>,
    val previousYearPapers: List<String>,
    val question: Question,
    val questionLabels: List<Any>,
    val solution: Solution,
    val source: String,
    val subjects: List<String>,
    val topics: List<Any>,
    val type: String,
    val videoSolution: Any
)