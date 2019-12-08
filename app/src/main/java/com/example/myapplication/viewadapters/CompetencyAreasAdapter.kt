package com.example.myapplication.viewadapters

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_COMPETENCY_AREA_ID
import com.example.myapplication.R
import com.example.myapplication.activities.Positions
import com.example.myapplication.entities.CompetencyArea
import kotlinx.android.synthetic.main.item_competency_areas.view.*

class CompetencyAreasAdapter :
    RecyclerView.Adapter<CompetencyAreasAdapter.CompetencyAreaViewHolder>() {
    private var competencyAreas: List<CompetencyArea> = emptyList()

    fun updateData(newData: List<CompetencyArea>) {
        val diffResult =
            DiffUtil.calculateDiff(CompetencyAreasDiffUtilCallback(competencyAreas, newData))
        this.competencyAreas = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetencyAreaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_competency_areas,
            parent,
            false
        )

        return CompetencyAreaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompetencyAreaViewHolder, position: Int) {
        holder.bind(competencyAreas[position])
    }

    override fun getItemCount() = competencyAreas.size

    class CompetencyAreaViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.competencyAreaName
        private var id = 0L

        init {
            view.setOnClickListener {
                view.context.startActivity(Intent(view.context, Positions::class.java))

                view.context
                    .getSharedPreferences(CURRENT_COMPETENCY_AREA_ID, MODE_PRIVATE)
                    .edit().putLong(CURRENT_COMPETENCY_AREA_ID, id)
                    .apply()
            }
        }

        fun bind(competencyArea: CompetencyArea) {
            name.text = competencyArea.name
            id = competencyArea.id
        }
    }

}
