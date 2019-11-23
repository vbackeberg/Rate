package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Applicant
import kotlinx.android.synthetic.main.item_applicants.view.*

class ApplicantsAdapter : RecyclerView.Adapter<ApplicantsAdapter.ApplicantViewHolder>() {
    private var applicants: List<Applicant> = emptyList()

    fun updateData(newData: List<Applicant>) {
        val diffResult = DiffUtil.calculateDiff(ApplicantsDiffUtilCallback(applicants, newData))
        this.applicants = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_applicants,
            parent,
            false
        )
        return ApplicantViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApplicantViewHolder, position: Int) {
        holder.bind(applicants[position])
    }

    override fun getItemCount(): Int = applicants.size

    class ApplicantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.applicantName

        fun bind(applicant: Applicant) {
            name.text = applicant.id.toString()
        }
    }
}