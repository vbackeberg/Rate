package com.example.myapplication.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Competency
import com.example.myapplication.viewmodels.CompetenciesVM
import kotlinx.android.synthetic.main.item_competencies.view.*

class CompetenciesAdapter(activity: AppCompatActivity) :
    RecyclerView.Adapter<CompetenciesAdapter.CompetencyViewHolder>() {
    private var competencies: List<Competency> = emptyList()
    private val competenciesVM: CompetenciesVM = ViewModelProviders.of(activity).get(CompetenciesVM::class.java)

    fun updateData(newData: List<Competency>) {
        val diffResult = DiffUtil.calculateDiff(CompetenciesDiffUtilCallback(competencies, newData))
        this.competencies = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_competencies,
            parent,
            false
        )

        return CompetencyViewHolder(view, competenciesVM)
    }

    override fun onBindViewHolder(holder: CompetencyViewHolder, position: Int) {
        holder.bind(competencies[position])
    }

    override fun getItemCount() = competencies.size

    class CompetencyViewHolder(
        view: View,
        competenciesVM: CompetenciesVM
    ) :
        RecyclerView.ViewHolder(view) {
        private val seekBarCompetency: SeekBar = view.seekBarCompetency
        private val textViewCompetency: TextView = view.textViewCompetency
        private lateinit var competency: Competency

        init {
            view.seekBarCompetency
                .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        competency.value = progress
                        competenciesVM.update(competency)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }
                })
        }

        fun bind(competency: Competency) {
            this.competency = competency
            textViewCompetency.text = competency.name
            seekBarCompetency.progress = competency.value
        }
    }
}