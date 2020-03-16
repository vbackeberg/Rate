package com.valerian.rate.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.valerian.rate.R
import com.valerian.rate.entities.CompetencyWithScore
import kotlinx.android.synthetic.main.item_competencies.view.*

class CompetenciesAdapter(
    private val onItemLongClickListener: View.OnLongClickListener,
    private val onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener
) :
    RecyclerView.Adapter<CompetenciesAdapter.CompetencyViewHolder>() {
    private var competencies = emptyList<CompetencyWithScore>()

    fun updateData(newData: List<CompetencyWithScore>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(competencies, newData))
        this.competencies = newData
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged() // Workaround
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_competencies,
            parent,
            false
        )

        return CompetencyViewHolder(
            view,
            onItemLongClickListener,
            onSeekBarChangeListener
        )
    }

    override fun onBindViewHolder(holder: CompetencyViewHolder, position: Int) {
        holder.bind(competencies[position])
    }

    override fun getItemCount() = competencies.size

    class CompetencyViewHolder(
        view: View,
        onItemLongClickListener: View.OnLongClickListener,
        onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener
    ) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnLongClickListener(onItemLongClickListener)
            itemView.seekBarCompetency.setOnSeekBarChangeListener(onSeekBarChangeListener)
        }

        fun bind(competency: CompetencyWithScore) {
            itemView.tag = competency
            itemView.seekBarCompetency.tag = competency
            itemView.seekBarCompetency.progress = competency.score.value
            itemView.textViewCompetency.text = competency.competency.name
        }
    }
}