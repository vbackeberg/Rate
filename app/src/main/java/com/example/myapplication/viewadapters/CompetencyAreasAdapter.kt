package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.CompetencyAreaWithImportance
import kotlinx.android.synthetic.main.item_competency_areas.view.*

class CompetencyAreasAdapter(
    private val onItemClickListener: View.OnClickListener,
    private val onItemLongClickListener: View.OnLongClickListener,
    private val onSeekBarChangeListener: SeekBar.OnSeekBarChangeListener
) :
    RecyclerView.Adapter<CompetencyAreasAdapter.CompetencyAreaViewHolder>() {
    private var competencyAreas: List<CompetencyAreaWithImportance> = emptyList()

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
            itemView.textViewCompetencyArea.text = competencyArea.competencyArea.name
            itemView.seekBarCompetencyArea.progress = competencyArea.importance.value
        }
    }

}
