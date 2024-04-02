package com.example.android.trafficlawsexam.exam

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.database.Question
import com.example.android.trafficlawsexam.database.QuestionDatabase
import com.example.android.trafficlawsexam.databinding.FragmentExamBinding

class ExamFragment : Fragment() {

    lateinit var viewModel: ExamViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentExamBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_exam, container, false)

        val application = requireNotNull(this.activity).application
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()
        val viewModelFactory = ExamViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ExamViewModel::class.java)
        binding.questionRadioGroup.clearCheck()

        binding.submitButton.setOnClickListener {
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            if (-1 != checkedId) {
                var checkedNumber = 1
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> checkedNumber = 2
                    R.id.thirdAnswerRadioButton -> checkedNumber = 3
                    R.id.fourthAnswerRadioButton -> checkedNumber = 4
                }
                binding.questionRadioGroup.clearCheck()
                viewModel.onSubmit(checkedNumber)
            }
        }

        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { currentQuestion ->
            binding.questionText.text = currentQuestion?.text
            binding.firstAnswerRadioButton.text = currentQuestion?.firstAnswer
            binding.secondAnswerRadioButton.text = currentQuestion?.secondAnswer
            if (currentQuestion?.thirdAnswer == null)
                binding.thirdAnswerRadioButton.isVisible = false
            else {
                binding.thirdAnswerRadioButton.isVisible = true
                binding.thirdAnswerRadioButton.text = currentQuestion.thirdAnswer
            }
            if (currentQuestion?.fourthAnswer == null)
                binding.fourthAnswerRadioButton.isVisible = false
            else {
                binding.fourthAnswerRadioButton.isVisible = true
                binding.fourthAnswerRadioButton.text = currentQuestion.fourthAnswer
            }
            if (currentQuestion?.imagePath == null)
                binding.questionImage.isVisible = false
            else {
                binding.questionImage.isVisible = true
                val imagePath = currentQuestion.imagePath ?: "blank_image"
                val uri = Uri.parse("android.resource://com.example.android.trafficlawsexam/drawable/${imagePath}")
                binding.questionImage.setImageURI(uri)
            }
        })

        viewModel.questionIndex.observe(viewLifecycleOwner, Observer { index ->
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_exam_question, index + 1, viewModel.numQuestions.value)
        })

        viewModel.numQuestions.observe(viewLifecycleOwner, Observer { numQuestions ->
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_exam_question, viewModel.questionIndex.value!! + 1, numQuestions)
        })

        viewModel.areQuestionsAdded.observe(viewLifecycleOwner, Observer { shouldNotify ->
            if (shouldNotify!!){
                Toast.makeText(context, getString(R.string.additional_questions_notifying, viewModel.additionalQuestionsCount),
                    Toast.LENGTH_LONG).show()
                viewModel.notifyAdditionalQuestions()
            }

        })

        viewModel.navigateToExamPassed.observe(viewLifecycleOwner,  Observer { shouldNavigate ->
            if (shouldNavigate!!) {
                this.findNavController().navigate(ExamFragmentDirections
                    .actionExamFragmentToExamPassedFragment(viewModel.listQuestionsId.toLongArray(), viewModel.numQuestions.value!!))
                viewModel.doneExamPassedNavigating()
            }
        })

        viewModel.navigateToExamFailed.observe(viewLifecycleOwner,  Observer { shouldNavigate ->
            if (shouldNavigate!!) {
                this.findNavController().navigate(ExamFragmentDirections
                    .actionExamFragmentToExamFailedFragment(viewModel.listQuestionsId.toLongArray(), viewModel.numQuestions.value!!))
                viewModel.doneExamFailedNavigating()
            }
        })

        viewModel.secondsUntilEnd.observe(viewLifecycleOwner, Observer {secondsUntilEnd ->
            binding.timerText.text = DateUtils.formatElapsedTime(secondsUntilEnd)
        })

        return binding.root
    }
}