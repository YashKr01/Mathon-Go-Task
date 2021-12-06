package com.example.sampletask.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampletask.R
import com.example.sampletask.adapters.QuestionsAdapter
import com.example.sampletask.databinding.FragmentQuestionsListBinding
import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.utils.Resource
import com.example.sampletask.viewmodel.QuestionsViewModel
import com.google.android.material.snackbar.Snackbar
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

        // setting up recycler view and adapter
        var list = emptyList<QuestionResponse>()
        val questionAdapter = QuestionsAdapter(requireContext(),
            onItemClick = { position ->
                val action =
                    QuestionsListFragmentDirections.actionQuestionsListFragmentToQuestionDetailFragment(
                        list.toTypedArray(), position
                    )
                findNavController().navigate(action)
                Log.d("TAG", "onCreateView: $position")
            })

        binding.recyclerViewQuestions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = questionAdapter
        }

        // loading from viewModel
        if (!viewModel.isLoaded) {
            viewModel.isLoaded = true
            viewModel.getQuestionsList()
        }

        // observe viewModel data
        viewModel.list.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("TAG", "onCreateView: LOADING")
                }
                is Resource.Success -> {
                    list = result.data!!
                    questionAdapter.submitList(result.data)
                }
                is Resource.Error -> {
                    Snackbar.make(
                        requireContext(),
                        binding.root,
                        "An Error Occurred",
                        Snackbar.LENGTH_SHORT
                    ).show()
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