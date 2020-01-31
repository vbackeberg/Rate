package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Applicant
import kotlinx.android.synthetic.main.item_applicants.view.*

class ApplicantsAdapter(
    private val onItemClickListener: View.OnClickListener,
    private val onItemLongClickListener: View.OnLongClickListener
) : RecyclerView.Adapter<ApplicantsAdapter.ApplicantViewHolder>() {
    private var applicants = mutableListOf<Applicant>()

    fun updateData(newData: MutableList<Applicant>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(applicants, newData))
        this.applicants = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_applicants,
            parent,
            false
        )
        return ApplicantViewHolder(view, onItemClickListener, onItemLongClickListener)
    }

    override fun onBindViewHolder(holder: ApplicantViewHolder, position: Int) {
        holder.bind(applicants[position])
    }

    override fun getItemCount(): Int = applicants.size

    class ApplicantViewHolder(
        view: View,
        onItemClickListener: View.OnClickListener,
        onItemLongClickListener: View.OnLongClickListener
    ) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener(onItemClickListener)
            itemView.setOnLongClickListener(onItemLongClickListener)
        }

        fun bind(applicant: Applicant) {
            itemView.tag = applicant
            itemView.applicantName.text = applicant.name
        }
    }
}