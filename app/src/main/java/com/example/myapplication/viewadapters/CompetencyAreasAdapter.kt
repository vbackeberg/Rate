package com.example.myapplication.viewadapters

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_COMPETENCY_AREA_ID
import com.example.myapplication.R
import com.example.myapplication.activities.Competencies
import com.example.myapplication.entities.CompetencyAreaWithImportance
import com.example.myapplication.viewmodels.CompetencyAreasVM
import kotlinx.android.synthetic.main.item_competency_areas.view.*

class CompetencyAreasAdapter(activity: AppCompatActivity) :
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

        return CompetencyAreaViewHolder(view, competencyAreasVM)
    }

    override fun onBindViewHolder(holder: CompetencyAreaViewHolder, position: Int) {
        holder.bind(competencyAreas[position])
    }

    override fun getItemCount() = competencyAreas.size

    class CompetencyAreaViewHolder(
        private val view: View,
        competencyAreasVM: CompetencyAreasVM
    ) : RecyclerView.ViewHolder(view) {
        private lateinit var competencyAreaWithImportance: CompetencyAreaWithImportance

        init {
            view.setOnClickListener {
                view.context.startActivity(Intent(view.context, Competencies::class.java))

                view.context
                    .getSharedPreferences(CURRENT_COMPETENCY_AREA_ID, MODE_PRIVATE)
                    .edit().putLong(CURRENT_COMPETENCY_AREA_ID, competencyAreaWithImportance.competencyArea.id)
                    .apply()
            }

            view.seekBarCompetencyArea
                .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        competencyAreaWithImportance.competencyAreaImportance.value = progress
                        competencyAreasVM.update(competencyAreaWithImportance.competencyAreaImportance)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }
                })
        }

        fun bind(competencyAreaWithImportance: CompetencyAreaWithImportance) {
            this.competencyAreaWithImportance = competencyAreaWithImportance
            view.textViewCompetencyArea.text = competencyAreaWithImportance.competencyArea.name
            view.seekBarCompetencyArea.progress = competencyAreaWithImportance.competencyAreaImportance.value
        }
    }

}
