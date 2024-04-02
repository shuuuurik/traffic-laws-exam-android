package com.example.android.trafficlawsexam.exampassed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.databinding.FragmentExamPassedBinding

class ExamPassedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentExamPassedBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_exam_passed, container, false)
        val args =
            ExamPassedFragmentArgs.fromBundle(requireArguments())

        binding.myAnswersButton.setOnClickListener {view: View ->
            view.findNavController().navigate(
                ExamPassedFragmentDirections.actionExamPassedFragmentToExamCorrectAnswersFragment(
                    args.listQuestionsId,
                    args.numQuestions
                )
            )
        }

        binding.passAgainButton.setOnClickListener {view: View ->
            view.findNavController().navigate(
                ExamPassedFragmentDirections.actionExamPassedFragmentToExamFragment()
            )
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_results)
        return binding.root
    }
}