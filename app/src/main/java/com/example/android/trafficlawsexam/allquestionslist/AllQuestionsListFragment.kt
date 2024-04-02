package com.example.android.trafficlawsexam.allquestionslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.database.QuestionDatabase
import com.example.android.trafficlawsexam.databinding.FragmentAllQuestionsListBinding

class AllQuestionsListFragment : Fragment() {

    private lateinit var viewModel: AllQuestionsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentAllQuestionsListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_questions_list, container, false)

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()
        val viewModelFactory = AllQuestionsListViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(AllQuestionsListViewModel::class.java)

        val adapter = QuestionAdapter()
        binding.allQuestionsList.adapter = adapter

        viewModel.questions.observe(viewLifecycleOwner, Observer { questions ->
                adapter.data = questions
        })

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.all_questions)
        return binding.root
    }
}