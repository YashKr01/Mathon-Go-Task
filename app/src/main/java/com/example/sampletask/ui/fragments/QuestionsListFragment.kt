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

    var attempted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsListBinding.inflate(inflater, container, false)

        // setting up recycler view and adapter
        var list = emptyList<QuestionResponse>()
        val questionAdapter = QuestionsAdapter(requireContext(),
            onItemClick = { position ->
                val listArgument = arrayListOf<QuestionResponse>()
                if (binding.txtFilter.text.equals(resources.getString(R.string.not_attempted))) {
                    listArgument.clear()
                    listArgument.addAll(list)
                } else {
                    listArgument.clear()
                    listArgument.addAll(viewModel.databaseList)
                }
                val action =
                    QuestionsListFragmentDirections.actionQuestionsListFragmentToQuestionDetailFragment(
                        listArgument.toTypedArray(), position
                    )
                findNavController().navigate(action)
            })

        binding.recyclerViewQuestions.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = questionAdapter
        }

        // loading from viewModel
        if (!viewModel.isLoaded) viewModel.getQuestionsList()

        // observe viewModel data
        viewModel.list.observe(viewLifecycleOwner) { result ->
//            binding.layoutEmpty.visibility = View.GONE
            when (result) {
                is Resource.Loading -> {
                    binding.shimmerLayout.startShimmer()
                }
                is Resource.Success -> {
                    binding.shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                    list = result.data!!
                    questionAdapter.submitList(result.data)
                }
                is Resource.Error -> {
                    binding.shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                    Snackbar.make(
                        requireContext(),
                        binding.root,
                        "An Error Occurred",
                        Snackbar.LENGTH_SHORT
                    ).setAction("RETRY") {
                        binding.shimmerLayout.startShimmer()
                        viewModel.getQuestionsList()
                    }.show()
                }
            }
        }

        viewModel.getAttemptedQuestionsList().observe(viewLifecycleOwner) { questionList ->
            viewModel.databaseList.clear()
            viewModel.databaseList.addAll(questionList)
        }

        binding.txtFilter.setOnClickListener {
            if (!attempted) {
                binding.txtFilter.text = getString(R.string.attempted)
                questionAdapter.submitList(viewModel.databaseList)
            } else {
                questionAdapter.submitList(viewModel.storedList)
                binding.txtFilter.text = resources.getString(R.string.not_attempted)
            }
            attempted = !attempted
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}