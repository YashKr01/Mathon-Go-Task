package com.example.sampletask.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampletask.adapters.QuestionsAdapter
import com.example.sampletask.databinding.FragmentQuestionsListBinding
import com.example.sampletask.utils.Resource
import com.example.sampletask.viewmodel.QuestionsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsListFragment : Fragment() {

    private var _binding: FragmentQuestionsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<QuestionsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsListBinding.inflate(inflater, container, false)

        val questionAdapter = QuestionsAdapter(requireContext())
        binding.recyclerViewQuestions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = questionAdapter
        }

        viewModel.getQuestionsList()

        viewModel.list.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("TAG", "onCreateView: LOADING")
                }
                is Resource.Success -> {
                    questionAdapter.submitList(result.data)
                }
                is Resource.Error -> {
                    Log.d("TAG", "onCreateView: ERROR")
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}