package com.example.android.trafficlawsexam.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.allquestionslist.AllQuestionsListViewModel
import com.example.android.trafficlawsexam.allquestionslist.AllQuestionsListViewModelFactory
import com.example.android.trafficlawsexam.allquestionslist.QuestionAdapter
import com.example.android.trafficlawsexam.database.QuestionDatabase
import com.example.android.trafficlawsexam.databinding.FragmentAllQuestionsListBinding
import com.example.android.trafficlawsexam.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentStatisticsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_statistics, container, false)

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()
        val viewModelFactory = StatisticsViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StatisticsViewModel::class.java)

        viewModel.learnedQuestionsNumber.observe(viewLifecycleOwner,  Observer { _ ->
            binding.learnedQuestionsNumberValue.text = viewModel.learnedQuestionsNumber.value!!.toString()
        })

        viewModel.allQuestionsNumber.observe(viewLifecycleOwner,  Observer { _ ->
            binding.allQuestionsNumberValue.text = viewModel.allQuestionsNumber.value!!.toString()
        })

        viewModel.learningProgress.observe(viewLifecycleOwner,  Observer { _ ->
            binding.learningProgressValue.text = viewModel.learningProgress.value!!.toString() + "%"
        })

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.statistics)
        return binding.root
    }
}