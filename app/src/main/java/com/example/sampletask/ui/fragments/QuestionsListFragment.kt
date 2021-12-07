package com.example.sampletask.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampletask.R
import com.example.sampletask.adapters.QuestionsAdapter
import com.example.sampletask.databinding.FragmentQuestionsListBinding
import com.example.sampletask.model.QuestionResponse
import com.example.sampletask.utils.ExtensionFunctions.hide
import com.example.sampletask.viewmodel.QuestionsViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.nio.charset.Charset

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
        val questionsList = arrayListOf<QuestionResponse>()
        val questionAdapter = QuestionsAdapter(requireContext(),
            onItemClick = { position ->

                val action1 =
                    QuestionsListFragmentDirections.actionQuestionsListFragmentToQuestionDetailFragment(
                        questionsList.toTypedArray(), position
                    )

                val action2 =
                    QuestionsListFragmentDirections.actionQuestionsListFragmentToQuestionDetailFragment(
                        viewModel.databaseList.toTypedArray(), position
                    )

                if (binding.txtFilter.text.equals(resources.getString(R.string.not_attempted)))
                    findNavController().navigate(action1)
                else findNavController().navigate(action2)

            })

        binding.recyclerViewQuestions.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = questionAdapter
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

        lifecycleScope.launch {
            delay(3000)
            questionsList.addAll(getData())
            viewModel.storedList = questionsList
            questionAdapter.submitList(questionsList)
            binding.shimmerLayout.apply {
                stopShimmer()
                hide()
            }
        }

        return binding.root
    }

    private fun getData(): List<QuestionResponse> {
        return try {
            val type = object : TypeToken<List<QuestionResponse>>() {}.type
            Gson().fromJson(loadJSONFromAsset(), type)

        } catch (e: IOException) {
            emptyList()
        }
    }

    private fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = resources.assets.open("questions.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}