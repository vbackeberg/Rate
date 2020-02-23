package com.valerian.rate.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.valerian.rate.R
import com.valerian.rate.entities.CompetencyAreaWithImportance
import kotlinx.android.synthetic.main.item_competency_areas.view.*

class CompetencyAreasAdapter(
    private val onItemClickListener: View.OnClickListener,
    private val onItemLongClickListener: View.OnLongClickListener,
    private val onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener
) :
    RecyclerView.Adapter<CompetencyAreasAdapter.CompetencyAreaViewHolder>() {
    private var competencyAreas = emptyList<CompetencyAreaWithImportance>()

    fun updateData(newData: List<CompetencyAreaWithImportance>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(competencyAreas, newData))
        this.competencyAreas = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetencyAreaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_competency_areas,
            parent,
            false
        )

        return CompetencyAreaViewHolder(
            view,
            onItemClickListener,
            onItemLongClickListener,
            onSeekBarChangeListener
        )
    }

    override fun onBindViewHolder(holder: CompetencyAreaViewHolder, position: Int) {
        holder.bind(competencyAreas[position])
    }

    override fun getItemCount() = competencyAreas.size

    class CompetencyAreaViewHolder(
        view: View,
        onItemClickListener: View.OnClickListener,
        onItemLongClickListener: View.OnLongClickListener,
        onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener
    ) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener(onItemClickListener)
            itemView.setOnLongClickListener(onItemLongClickListener)
            itemView.seekBarCompetencyArea.setOnSeekBarChangeListener(onSeekBarChangeListener)
        }

        fun bind(competencyArea: CompetencyAreaWithImportance) {
            itemView.tag = competencyArea
            itemView.seekBarCompetencyArea.tag = competencyArea
            itemView.seekBarCompetencyArea.progress = competencyArea.importance.value
            itemView.textViewCompetencyArea.text = competencyArea.competencyArea.name
        }
    }

}
