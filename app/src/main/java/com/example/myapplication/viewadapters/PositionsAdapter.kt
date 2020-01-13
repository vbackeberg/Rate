package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Position
import kotlinx.android.synthetic.main.item_positions.view.*

class PositionsAdapter(
    private val onItemClickListener: View.OnClickListener,
    private val onItemLongClickListener: View.OnLongClickListener
) : RecyclerView.Adapter<PositionsAdapter.PositionViewHolder>() {
    private var positions: List<Position> = emptyList()

    fun updateData(newData: List<Position>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(positions, newData))
        this.positions = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_positions,
            parent,
            false
        )

        return PositionViewHolder(view, onItemClickListener, onItemLongClickListener)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.bind(positions[position])
    }

    override fun getItemCount() = positions.size

    class PositionViewHolder(
        view: View,
        onItemClickListener: View.OnClickListener,
        onItemLongClickListener: View.OnLongClickListener
    ) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener(onItemClickListener)
            itemView.setOnLongClickListener(onItemLongClickListener)
        }

        fun bind(position: Position) {
            itemView.tag = position
            itemView.positionName.text = position.name
        }
    }

}
