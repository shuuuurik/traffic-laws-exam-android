package com.example.android.trafficlawsexam.allquestionslist

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trafficlawsexam.R
import com.example.android.trafficlawsexam.database.Question

class QuestionAdapter : RecyclerView.Adapter<QuestionItemViewHolder>() {
    var data = listOf<Question>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: QuestionItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionItemViewHolder {
        return QuestionItemViewHolder.from(parent)
    }
}