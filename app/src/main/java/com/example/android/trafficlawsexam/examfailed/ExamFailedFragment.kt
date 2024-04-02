package com.example.android.trafficlawsexam.examfailed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.databinding.FragmentExamFailedBinding

class ExamFailedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentExamFailedBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_exam_failed, container, false)
        val args =
            ExamFailedFragmentArgs.fromBundle(requireArguments())

        binding.myAnswersButton.setOnClickListener {view: View ->
            view.findNavController().navigate(
                ExamFailedFragmentDirections.actionExamFailedFragmentToExamCorrectAnswersFragment(
                    args.listQuestionsId,
                    args.numQuestions
                )
            )
        }

        binding.passAgainButton.setOnClickListener {view: View ->
            view.findNavController().navigate(
                ExamFailedFragmentDirections.actionExamFailedFragmentToExamFragment()
            )
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_results)
        return binding.root
    }

}