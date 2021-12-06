package com.example.sampletask.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MATHON_GO")
data class QuestionResponse(
    val chapters: List<String>,
    val exams: List<String>,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "question_id")
    val id: String,
    val options: List<Option>,
    val previousYearPapers: List<String>,
    @Embedded
    val question: Question,
    @Embedded
    val solution: Solution,
    val source: String,
    val subjects: List<String>,
    val type: String,
) : Parcelable