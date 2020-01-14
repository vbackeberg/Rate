package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.CompetencyAreaWithImportance
import com.example.myapplication.viewmodels.CompetencyAreasVM
import kotlinx.android.synthetic.main.item_competency_areas.view.*

class CompetencyAreasAdapter(
    activity: AppCompatActivity,
    private val onItemClickListener: View.OnClickListener,
    private val onItemLongClickListener: View.OnLongClickListener
) :
    RecyclerView.Adapter<CompetencyAreasAdapter.CompetencyAreaViewHolder>() {
    private var competencyAreas: List<CompetencyAreaWithImportance> = emptyList()
    private val competencyAreasVM: CompetencyAreasVM =
        ViewModelProviders.of(activity).get(CompetencyAreasVM::class.java)

    fun updateData(newData: List<CompetencyAreaWithImportance>) {
        val diffResult =
            DiffUtil.calculateDiff(DiffUtilCallback(competencyAreas, newData))
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
            competencyAreasVM,
            onItemClickListener,
            onItemLongClickListener
        )
    }

    override fun onBindViewHolder(holder: CompetencyAreaViewHolder, position: Int) {
        holder.bind(competencyAreas[position])
    }

    override fun getItemCount() = competencyAreas.size

    class CompetencyAreaViewHolder(
        view: View,
        competencyAreasVM: CompetencyAreasVM,
        onItemClickListener: View.OnClickListener,
        onItemLongClickListener: View.OnLongClickListener
    ) : RecyclerView.ViewHolder(view) {
        private lateinit var competencyAreaWithImportance: CompetencyAreaWithImportance

        init {
            view.setOnClickListener {
                itemView.setOnClickListener(onItemClickListener)
                itemView.setOnLongClickListener(onItemLongClickListener)
            }

            view.seekBarCompetencyArea
                .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        competencyAreaWithImportance.importance.value = progress
                        competencyAreasVM.update(competencyAreaWithImportance.importance)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }
                })
        }

        fun bind(competencyAreaWithImportance: CompetencyAreaWithImportance) {
            this.competencyAreaWithImportance = competencyAreaWithImportance
            itemView.tag = competencyAreaWithImportance
            itemView.textViewCompetencyArea.text = competencyAreaWithImportance.competencyArea.name
            itemView.seekBarCompetencyArea.progress = competencyAreaWithImportance.importance.value
        }
    }

}
