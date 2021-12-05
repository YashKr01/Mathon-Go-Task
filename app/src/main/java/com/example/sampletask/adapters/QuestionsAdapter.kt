package com.example.sampletask.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampletask.databinding.ItemQuestionBinding
import com.example.sampletask.model.QuestionResponse

class QuestionsAdapter(private val context: Context) :
    ListAdapter<QuestionResponse, QuestionsAdapter.ItemViewHolder>(ItemComparator()) {

    inner class ItemViewHolder(private val view: ItemQuestionBinding) :
        RecyclerView.ViewHolder(view.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: QuestionResponse, position: Int) {
            view.apply {
                itemQuestion.text = item.question.text
                itemQuestionPaper.text = item.exams[0] + " " + item.previousYearPapers[0]
                itemQuestionNumber.text = position.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(ItemQuestionBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item, position)
    }

}