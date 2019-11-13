package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.abteilung_main.view.*

class AbteilungAdapter(private val abteilungen: Array<String>) :
    RecyclerView.Adapter<AbteilungAdapter.AbteilungViewHolder>() {

    class AbteilungViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val abteilungText = view.abteilungText
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
        holder.abteilungText.text = abteilungen[position]
    }

    override fun getItemCount() = abteilungen.size

}
