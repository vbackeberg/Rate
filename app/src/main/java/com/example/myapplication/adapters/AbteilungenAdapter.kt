package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.Kompetenz
import com.example.myapplication.entities.Abteilung
import kotlinx.android.synthetic.main.abteilung_main.view.*

class AbteilungenAdapter : RecyclerView.Adapter<AbteilungenAdapter.AbteilungViewHolder>() {
    private var abteilungen: List<Abteilung> = emptyList()

    fun updateData(newData: List<Abteilung>) {
        val diffResult = DiffUtil.calculateDiff(AbteilungenDiffUtilCallback(abteilungen, newData))
        this.abteilungen = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbteilungViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.abteilung_main,
            parent,
            false
        )

        return AbteilungViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbteilungViewHolder, position: Int) {
        holder.bind(abteilungen[position])
    }

    override fun getItemCount() = abteilungen.size

    class AbteilungViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.abteilungText

        init {
            view.setOnClickListener {
                view.context.startActivity(Intent(view.context, Kompetenz::class.java))
            }
        }

        fun bind(abteilung: Abteilung) {
            name.text = abteilung.name
        }
    }

}
