package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class AbteilungAdapter(private val abteilungen: Array<String>) :
    RecyclerView.Adapter<AbteilungAdapter.AbteilungViewHolder>() {

    class AbteilungViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbteilungViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(
            R.layout.abteilung_main,
            parent,
            false
        ) as TextView

        return AbteilungViewHolder(textView)
    }

    override fun onBindViewHolder(holder: AbteilungViewHolder, position: Int) {
        holder.textView.text = abteilungen[position]
    }

    override fun getItemCount() = abteilungen.size

}
