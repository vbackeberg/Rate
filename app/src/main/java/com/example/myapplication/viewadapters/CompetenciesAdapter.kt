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
import com.example.myapplication.entities.CompetencyWithScore
import com.example.myapplication.viewmodels.CompetenciesVM
import kotlinx.android.synthetic.main.item_competencies.view.*

class CompetenciesAdapter(
    activity: AppCompatActivity,
    private val onItemLongClickListener: View.OnLongClickListener
) :
    RecyclerView.Adapter<CompetenciesAdapter.CompetencyViewHolder>() {
    private var competencies: List<CompetencyWithScore> = emptyList()
    private val competenciesVM: CompetenciesVM =
        ViewModelProviders.of(activity).get(CompetenciesVM::class.java)

    fun updateData(newData: List<CompetencyWithScore>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(competencies, newData))
        this.competencies = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_competencies,
            parent,
            false
        )

        return CompetencyViewHolder(
            view,
            competenciesVM,
            onItemLongClickListener
        )
    }

    override fun onBindViewHolder(holder: CompetencyViewHolder, position: Int) {
        holder.bind(competencies[position])
    }

    override fun getItemCount() = competencies.size

    class CompetencyViewHolder(
        view: View,
        competenciesVM: CompetenciesVM,
        onItemLongClickListener: View.OnLongClickListener
    ) :
        RecyclerView.ViewHolder(view) {
        private lateinit var competency: CompetencyWithScore

        init {
            itemView.setOnLongClickListener(onItemLongClickListener)

            view.seekBarCompetency
                .setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        competency.score.value = progress
                        competenciesVM.update(competency.score)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }
                })
        }

        fun bind(competency: CompetencyWithScore) {
            this.competency = competency
            itemView.tag = competency
            itemView.textViewCompetency.text = competency.competency.name
            itemView.seekBarCompetency.progress = competency.score.value
        }
    }
}