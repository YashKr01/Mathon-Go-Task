package com.example.sampletask.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sampletask.R
import com.example.sampletask.databinding.FragmentQuestionDetailBinding
import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.utils.ExtensionFunctions.hide
import com.example.sampletask.utils.ExtensionFunctions.setLeftDrawable
import com.example.sampletask.utils.ExtensionFunctions.setSrcColor
import com.example.sampletask.utils.ExtensionFunctions.setTextViewBackground
import com.example.sampletask.utils.ExtensionFunctions.show
import com.example.sampletask.viewmodel.QuestionDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class QuestionDetailFragment : Fragment() {

    private var _binding: FragmentQuestionDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<QuestionDetailFragmentArgs>()

    private var currentSelectedOption: Int? = null
    private var previousSelectedOption: Int? = null

    private var selected = false
    private var currentStreak = 0

    private var currentIndex by Delegates.notNull<Int>()
    private val viewModel by viewModels<QuestionDetailViewModel>()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionDetailBinding.inflate(inflater, container, false)

        val list = args.questions
        val index = args.position
        currentIndex = index

        binding.apply {

            txtOption1.setOnClickListener {
                previousSelectedOption = currentSelectedOption
                currentSelectedOption = 1
                enableButton()
                setSelectedBackground(currentSelectedOption, previousSelectedOption)
            }

            txtOption2.setOnClickListener {
                previousSelectedOption = currentSelectedOption
                currentSelectedOption = 2
                enableButton()
                setSelectedBackground(currentSelectedOption, previousSelectedOption)
            }

            txtOption3.setOnClickListener {
                previousSelectedOption = currentSelectedOption
                currentSelectedOption = 3
                enableButton()
                setSelectedBackground(currentSelectedOption, previousSelectedOption)
            }

            txtOption4.setOnClickListener {
                previousSelectedOption = currentSelectedOption
                currentSelectedOption = 4
                enableButton()
                setSelectedBackground(currentSelectedOption, previousSelectedOption)
            }

            btnBack.setOnClickListener { findNavController().popBackStack() }

        }

        binding.btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                loadIndexQuestion(currentIndex, list)
            }
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
                } else displayResult(currentIndex, list, currentSelectedOption!!)

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
            txtSolution.show()
            txtSolution.text = list[currentQuestion].solution.text
        }

        val options = list[currentQuestion].options

        lifecycleScope.launch {
            viewModel.insertQuestion(list[currentQuestion])
        }

        currentStreak++
        var correctOptionIndex = -1
        options.forEachIndexed { index, option ->
            if (option.isCorrect) correctOptionIndex = index
        }
        correctOptionIndex++

        // setting green background on correct option
        when (correctOptionIndex) {
            1 -> binding.txtOption1.apply {
                setTextViewBackground(requireContext(), R.drawable.background_correct)
                setLeftDrawable(R.drawable.ic_option_a_correct)
            }
            2 -> binding.txtOption2.apply {
                setTextViewBackground(requireContext(), R.drawable.background_correct)
                setLeftDrawable(R.drawable.ic_option_b_correct)
            }
            3 -> binding.txtOption3.apply {
                setTextViewBackground(requireContext(), R.drawable.background_correct)
                setLeftDrawable(R.drawable.ic_option_c_corect)
            }
            else -> binding.txtOption4.apply {
                setTextViewBackground(requireContext(), R.drawable.background_correct)
                setLeftDrawable(R.drawable.ic_option_d_correct)
            }
        }

        if (correctOptionIndex != currentSelectedOption) {
            currentStreak = 0
            when (currentSelectedOption) {
                1 -> binding.txtOption1.apply {
                    setTextViewBackground(requireContext(), R.drawable.background_wrong)
                    setLeftDrawable(R.drawable.ic_option_a_wrong)
                }
                2 -> binding.txtOption2.apply {
                    setTextViewBackground(requireContext(), R.drawable.background_wrong)
                    setLeftDrawable(R.drawable.ic_option_b_wrong)
                }
                3 -> binding.txtOption3.apply {
                    setTextViewBackground(requireContext(), R.drawable.background_wrong)
                    setLeftDrawable(R.drawable.ic_option_c_wrong)
                }
                else -> binding.txtOption4.apply {
                    setTextViewBackground(requireContext(), R.drawable.background_wrong)
                    setLeftDrawable(R.drawable.ic_option_d_wrong)
                }
            }
        } else displayMessage()

        if (currentStreak == 3) displayStreak(list)

    }

    private fun displayStreak(questionList: Array<QuestionResponse>) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.apply {
                viewStub.show()
                btnViewSolution.setOnClickListener { viewStub.hide() }
                btnNextQuestion.setOnClickListener {
                    viewStub.hide()
                    currentIndex++
                    loadIndexQuestion(currentIndex, questionList)
                }
            }
        }
    }

    private fun displayMessage() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.txtMessage.show()
            delay(1000)
            binding.txtMessage.hide()
        }
    }

    private fun setSelectedBackground(current: Int?, previous: Int?) {

        // setting blue image and background on current selected text view
        when (current) {
            1 -> binding.txtOption1.apply {
                setTextViewBackground(requireContext(), R.drawable.background_selected)
                setLeftDrawable(R.drawable.ic_option_a_active)
            }
            2 -> binding.txtOption2.apply {
                setTextViewBackground(requireContext(), R.drawable.background_selected)
                setLeftDrawable(R.drawable.ic_option_b_active)
            }
            3 -> binding.txtOption3.apply {
                setTextViewBackground(requireContext(), R.drawable.background_selected)
                setLeftDrawable(R.drawable.ic_option_c_active)
            }
            else -> binding.txtOption4.apply {
                setTextViewBackground(requireContext(), R.drawable.background_selected)
                setLeftDrawable(R.drawable.ic_option_d_active)
            }
        }

        // setting default image and background on previous selected text view
        if (current != null && previous != null && current != previous) {
            when (previous) {
                1 -> binding.txtOption1.apply {
                    setTextViewBackground(requireContext(), R.drawable.background_option)
                    setLeftDrawable(R.drawable.ic_option_a_)
                }
                2 -> binding.txtOption2.apply {
                    setTextViewBackground(requireContext(), R.drawable.background_option)
                    setLeftDrawable(R.drawable.ic_option_b)
                }
                3 -> binding.txtOption3.apply {
                    setTextViewBackground(requireContext(), R.drawable.background_option)
                    setLeftDrawable(R.drawable.ic_option_c)
                }
                else -> binding.txtOption4.apply {
                    setTextViewBackground(requireContext(), R.drawable.background_option)
                    setLeftDrawable(R.drawable.ic_option_d)
                }
            }
        }

    }

    // setting options text view to default state
    private fun clearSelection() {

        binding.apply {
            txtOption1.apply {
                setTextViewBackground(requireContext(), R.drawable.background_option)
                setLeftDrawable(R.drawable.ic_option_a_)
            }
            txtOption2.apply {
                setTextViewBackground(requireContext(), R.drawable.background_option)
                setLeftDrawable(R.drawable.ic_option_b)
            }
            txtOption3.apply {
                setTextViewBackground(requireContext(), R.drawable.background_option)
                setLeftDrawable(R.drawable.ic_option_c)
            }
            txtOption4.apply {
                setTextViewBackground(requireContext(), R.drawable.background_option)
                setLeftDrawable(R.drawable.ic_option_d)
            }
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
        currentSelectedOption = null
        previousSelectedOption = null

        if (index == 0)
            binding.btnPrevious.setSrcColor(requireContext(), R.color.color_grey)
        else
            binding.btnPrevious.setSrcColor(requireContext(), R.color.color_primary_blue)

        val question = list[index]
        binding.apply {
            txtSolution.hide()
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