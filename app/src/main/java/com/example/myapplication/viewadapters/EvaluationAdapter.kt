package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Applicant
import kotlinx.android.synthetic.main.item_evaluation.view.*

class EvaluationAdapter : RecyclerView.Adapter<EvaluationAdapter.EvaluationViewHolder>() {
    private var applicants = mutableListOf<Applicant>()

    fun updateData(newData: MutableList<Applicant>) {
        println("old" + applicants)
        println("newdata " + newData)
        newData.sortBy { applicant -> applicant.score }
        val diffResult = DiffUtil.calculateDiff(ApplicantsDiffUtilCallback(applicants, newData))
        this.applicants = newData
        println("now " + applicants)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvaluationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_evaluation,
            parent,
            false
        )
        return EvaluationViewHolder(view)
    }

    override fun onBindViewHolder(holder: EvaluationViewHolder, position: Int) {
        holder.bind(applicants[position])
    }

    override fun getItemCount(): Int = applicants.size

    class EvaluationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.evaluationApplicantName
        private val score: TextView = view.evaluationApplicantScore
        private var id = 0L

        fun bind(applicant: Applicant) {
            id = applicant.id
            name.text = "Id: " + applicant.id.toString()
            score.text = "Score " + applicant.score.toString()
        }
    }
}