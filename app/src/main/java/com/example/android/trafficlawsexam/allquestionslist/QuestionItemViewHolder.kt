package com.example.android.trafficlawsexam.allquestionslist

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.database.Question

class QuestionItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val questionImage: ImageView = itemView.findViewById(R.id.question_image)
    private val questionText: TextView = itemView.findViewById(R.id.question_text)

    fun bind(item: Question) {
        questionText.text = (position + 1).toString() + ". " + item.text
        if (item.correctAnswerNumber == item.lastGivenAnswerNumber)
            questionText.setTextColor(Color.parseColor("#1dcf17"))
        else
            questionText.setTextColor(Color.parseColor("#fa0e0e"))
        if (item.imagePath == null)
            questionImage.isVisible = false
        else {
            questionImage.isVisible = true
            val uri = Uri.parse("android.resource://com.example.android.trafficlawsexam/drawable/${item.imagePath}")
            questionImage.setImageURI(uri)
        }
    }

    companion object {
        fun from(parent: ViewGroup): QuestionItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.list_item_question, parent, false)
            return QuestionItemViewHolder(view)
        }
    }
}