package com.example.android.trafficlawsexam.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.allquestionslist.AllQuestionsListViewModel
import com.example.android.trafficlawsexam.allquestionslist.AllQuestionsListViewModelFactory
import com.example.android.trafficlawsexam.database.QuestionDatabase
import com.example.android.trafficlawsexam.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private lateinit var viewModel: TitleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
            inflater, R.layout.fragment_title, container, false)

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()
        val viewModelFactory = TitleViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TitleViewModel::class.java)

        binding.allQuestionsButton.setOnClickListener {
            it.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToAllQuestionsListFragment())
        }

        binding.statisticsButton.setOnClickListener {
            it.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToStatisticsFragment())
        }

        binding.startButton.setOnClickListener {
            it.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToExamFragment())
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        return binding.root
    }
}