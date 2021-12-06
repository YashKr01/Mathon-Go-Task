package com.example.sampletask.ui.fragments

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sampletask.R
import com.example.sampletask.databinding.FragmentQuestionDetailBinding
import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.viewmodel.QuestionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionDetailFragment : Fragment() {

    private var _binding: FragmentQuestionDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<QuestionDetailFragmentArgs>()

    private var currentSelected: Int? = null
    private var previousSelected: Int? = null

    private var selected = false
    private var streak = 0

    private val viewModel by viewModels<QuestionDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionDetailBinding.inflate(inflater, container, false)

        val list = args.questions
        val index = args.position
        var currentIndex = index

        binding.apply {

            txtOption1.setOnClickListener {
                previousSelected = currentSelected
                currentSelected = 1
                enableButton()
                setSelectedBackground(currentSelected, previousSelected)
            }

            txtOption2.setOnClickListener {
                previousSelected = currentSelected
                currentSelected = 2
                enableButton()
                setSelectedBackground(currentSelected, previousSelected)
            }

            txtOption3.setOnClickListener {
                previousSelected = currentSelected
                currentSelected = 3
                enableButton()
                setSelectedBackground(currentSelected, previousSelected)
            }

            txtOption4.setOnClickListener {
                previousSelected = currentSelected
                currentSelected = 4
                enableButton()
                setSelectedBackground(currentSelected, previousSelected)
            }

            btnBack.setOnClickListener { findNavController().popBackStack() }

        }

        binding.btnPrevious.setOnClickListener {
            currentIndex--
            if (currentIndex >= 0)
                loadIndexQuestion(currentIndex, list)
        }

        binding.btnNext.setOnClickListener {
            currentIndex++
            loadIndexQuestion(currentIndex, list)
        }

        binding.btnNavigation.apply {
            setOnClickListener {
                if (binding.btnNavigation.text == resources.getString(R.string.next)) {
                    currentIndex++
                    loadIndexQuestion(currentIndex, list)
                } else {
                    displayResult(currentIndex, list, currentSelected!!)
                }
            }
            text = resources.getString(R.string.next)
        }

        loadIndexQuestion(index, list)

        return binding.root
    }

    private fun displayResult(
        currentQuestion: Int,
        list: Array<QuestionResponse>,
        currentSelectedOption: Int
    ) {

        binding.apply {
            btnNavigation.text = resources.getString(R.string.next)
            txtSolution.visibility = View.VISIBLE
            txtSolution.text = list[currentQuestion].solution.text
        }

        val options = list[currentQuestion].options

        lifecycleScope.launch {
            viewModel.insertQuestion(list[currentQuestion])
        }

        streak++
        var correctOptionIndex = -1
        options.forEachIndexed { index, option ->
            if (option.isCorrect) correctOptionIndex = index
        }
        correctOptionIndex++

        val currentIndex = when (correctOptionIndex) {
            1 -> binding.txtOption1
            2 -> binding.txtOption2
            3 -> binding.txtOption3
            else -> binding.txtOption4
        }
        currentIndex.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_correct)

        if (correctOptionIndex != currentSelectedOption) {
            streak = 0
            val index = when (currentSelectedOption) {
                1 -> binding.txtOption1
                2 -> binding.txtOption2
                3 -> binding.txtOption3
                else -> binding.txtOption4
            }
            index.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_wrong)
        }

    }

    private fun setSelectedBackground(current: Int?, previous: Int?) {

        val currentIndex = when (current) {
            1 -> binding.txtOption1
            2 -> binding.txtOption2
            3 -> binding.txtOption3
            else -> binding.txtOption4
        }
        currentIndex.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_selected)

        if (current != null && previous != null && current != previous) {
            val previousIndex = when (previous) {
                1 -> binding.txtOption1
                2 -> binding.txtOption2
                3 -> binding.txtOption3
                else -> binding.txtOption4
            }

            previousIndex.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_option)
        }

    }

    private fun clearSelection() {

        binding.apply {
            txtOption1.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_option)
            txtOption2.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_option)
            txtOption3.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_option)
            txtOption4.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_option)
        }

    }

    private fun enableButton() {
        selected = true
        binding.btnNavigation.apply {
            isClickable = true
            setTextColor(resources.getColor(R.color.color_white))
            setBackgroundColor(resources.getColor(R.color.color_primary_blue))
        }
    }

    private fun disableButton() {

        selected = false
        binding.btnNavigation.apply {
            isClickable = false
            setTextColor(resources.getColor(R.color.color_grey))
            setBackgroundColor(resources.getColor(R.color.color_light_grey))
            text = resources.getString(R.string.check_answer)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadIndexQuestion(index: Int, list: Array<QuestionResponse>) {

        disableButton()
        clearSelection()
        currentSelected = null
        previousSelected = null

        if (index == 0) {
            binding.btnPrevious.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_grey
                ), android.graphics.PorterDuff.Mode.MULTIPLY
            )
        } else {
            binding.btnPrevious.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_primary_blue
                ), android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }

        val question = list[index]
        binding.apply {
            txtSolution.visibility = View.GONE
            txtPaper.text = question.exams[0] + " " + question.previousYearPapers[0]
            txtQuestion.text = question.question.text
            txtQuestionNumber.text =
                if (index < 9) "0${index + 1} (Single Correct)" else "${index + 1} (Single Correct)"

            txtOption1.text = question.options[0].text
            txtOption2.text = question.options[1].text
            txtOption3.text = question.options[2].text
            txtOption4.text = question.options[3].text

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}