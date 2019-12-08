package com.example.myapplication.viewadapters

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.R
import com.example.myapplication.activities.Applicants
import com.example.myapplication.entities.Department
import com.example.myapplication.entities.Position
import kotlinx.android.synthetic.main.item_departments.view.*
import kotlinx.android.synthetic.main.item_positions.view.*

class PositionsAdapter : RecyclerView.Adapter<PositionsAdapter.PositionViewHolder>() {
    private var positions: List<Position> = emptyList()

    fun updateData(newData: List<Position>) {
        val diffResult = DiffUtil.calculateDiff(PositionsDiffUtilCallback(positions, newData))
        this.positions = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_positions,
            parent,
            false
        )

        return PositionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.bind(positions[position])
    }

    override fun getItemCount() = positions.size

    class PositionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.positionName
        private var id = 0L

        init {
            view.setOnClickListener {
                view.context.startActivity(Intent(view.context, Applicants::class.java))

                view.context
                    .getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
                    .edit().putLong(CURRENT_POSITION_ID, id)
                    .apply()
            }
        }

        fun bind(position: Position) {
            name.text = position.name
            id = position.id
        }
    }

}
