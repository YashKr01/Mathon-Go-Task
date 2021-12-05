package com.example.sampletask.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.sampletask.model.QuestionResponse

class ItemComparator : DiffUtil.ItemCallback<QuestionResponse>() {

    override fun areItemsTheSame(oldItem: QuestionResponse, newItem: QuestionResponse): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: QuestionResponse, newItem: QuestionResponse): Boolean =
        oldItem == newItem

}