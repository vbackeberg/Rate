package com.valerian.rate.viewadapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.valerian.rate.R
import com.valerian.rate.entities.Applicant
import kotlinx.android.synthetic.main.item_evaluation.view.*

class EvaluationAdapter : RecyclerView.Adapter<EvaluationAdapter.EvaluationViewHolder>() {
    private var applicants = mutableListOf<Applicant>()

    fun updateData(newData: MutableList<Applicant>) {
        newData.sortBy { applicant -> applicant.score }
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(applicants, newData))
        this.applicants = newData
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

        @SuppressLint("SetTextI18n")
        fun bind(applicant: Applicant) {
            itemView.evaluationApplicantName.text = "Name: " + applicant.name
            itemView.evaluationApplicantScore.text = "Bewertung: " + applicant.score.toString()
        }
    }
}