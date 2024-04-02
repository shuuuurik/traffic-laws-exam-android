package com.example.android.trafficlawsexam.examcorrectanswers

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.database.QuestionDatabase
import com.example.android.trafficlawsexam.databinding.FragmentExamBinding
import com.example.android.trafficlawsexam.databinding.FragmentExamCorrectAnswersBinding
import com.example.android.trafficlawsexam.exam.ExamFragmentDirections
import com.example.android.trafficlawsexam.exam.ExamViewModel


class ExamCorrectAnswersFragment : Fragment() {

    lateinit var viewModel: ExamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentExamCorrectAnswersBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_exam_correct_answers, container, false)

        val application = requireNotNull(this.activity).application
        val args = ExamCorrectAnswersFragmentArgs.fromBundle(requireArguments())
        val dao = QuestionDatabase.getInstance(application).getQuestionDatabaseDao()
        val viewModelFactory = ExamCorrectAnswersViewModelFactory(args.numQuestions, args.listQuestionsId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ExamCorrectAnswersViewModel::class.java)

        binding.nextAnswerButton.setOnClickListener {
            viewModel.nextAnswer()
        }

        binding.previousAnswerButton.setOnClickListener {
            viewModel.previousAnswer()
        }

        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { currentQuestion ->
            val textViews = arrayListOf(binding.firstAnswerText, binding.secondAnswerText,
                binding.thirdAnswerText, binding.fourthAnswerText)
            for (textView in textViews)
                textView.setTextColor(ContextCompat.getColor(requireContext() ,R.color.black))

            binding.questionText.text = currentQuestion?.text
            binding.firstAnswerText.text = currentQuestion?.firstAnswer
            binding.secondAnswerText.text = currentQuestion?.secondAnswer
            if (currentQuestion?.thirdAnswer == null)
                binding.thirdAnswerText.isVisible = false
            else {
                binding.thirdAnswerText.isVisible = true
                binding.thirdAnswerText.text = currentQuestion.thirdAnswer
            }
            if (currentQuestion?.fourthAnswer == null)
                binding.fourthAnswerText.isVisible = false
            else {
                binding.fourthAnswerText.isVisible = true
                binding.fourthAnswerText.text = currentQuestion.fourthAnswer
            }
            val imagePath = currentQuestion?.imagePath ?: "blank_image"
            val uri = Uri.parse("android.resource://com.example.android.trafficlawsexam/drawable/${imagePath}")
            binding.questionImage.setImageURI(uri)

            when (currentQuestion!!.correctAnswerNumber){
                1 -> binding.firstAnswerText.setTextColor(ContextCompat.getColor(requireContext() ,R.color.correctAnswer))
                2 -> binding.secondAnswerText.setTextColor(ContextCompat.getColor(requireContext() ,R.color.correctAnswer))
                3 -> binding.thirdAnswerText.setTextColor(ContextCompat.getColor(requireContext() ,R.color.correctAnswer))
                4 -> binding.fourthAnswerText.setTextColor(ContextCompat.getColor(requireContext() ,R.color.correctAnswer))
            }
            if (currentQuestion!!.correctAnswerNumber != currentQuestion!!.lastGivenAnswerNumber)
                when (currentQuestion!!.lastGivenAnswerNumber){
                    1 -> binding.firstAnswerText.setTextColor(ContextCompat.getColor(requireContext() ,R.color.wrongAnswer))
                    2 -> binding.secondAnswerText.setTextColor(ContextCompat.getColor(requireContext() ,R.color.wrongAnswer))
                    3 -> binding.thirdAnswerText.setTextColor(ContextCompat.getColor(requireContext() ,R.color.wrongAnswer))
                    4 -> binding.fourthAnswerText.setTextColor(ContextCompat.getColor(requireContext() ,R.color.wrongAnswer))
                }
        })

        viewModel.answerIndex.observe(viewLifecycleOwner, Observer { index ->
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_exam_question, index + 1, viewModel.numQuestions)
        })

        viewModel.nextButtonHide.observe(viewLifecycleOwner,  Observer { shouldHide ->
            if (shouldHide!!) {
                binding.nextAnswerButton.isVisible = false
            } else
                binding.nextAnswerButton.isVisible = true
        })

        viewModel.previousButtonHide.observe(viewLifecycleOwner,  Observer { shouldHide ->
            if (shouldHide!!) {
                binding.previousAnswerButton.isVisible = false
            } else
                binding.previousAnswerButton.isVisible = true
        })

        return binding.root
    }
}