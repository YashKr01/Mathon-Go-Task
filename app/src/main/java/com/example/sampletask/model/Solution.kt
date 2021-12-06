package com.example.sampletask.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Solution(
    val text: String
) : Parcelable