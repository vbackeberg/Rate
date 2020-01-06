package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.viewadapters.CompetencyAreasAdapter
import com.example.myapplication.viewmodels.CompetencyAreasVM
import kotlinx.android.synthetic.main.activity_competency_areas.*
import kotlinx.android.synthetic.main.content_competency_areas.*
import kotlinx.android.synthetic.main.dialog.view.*

class CompetencyAreas : AppCompatActivity() {
    private lateinit var viewAdapter: CompetencyAreasAdapter
    private var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var competencyAreasVM: CompetencyAreasVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competency_areas)

        viewAdapter = CompetencyAreasAdapter(this)

        competencyAreasVM = ViewModelProviders.of(this).get(CompetencyAreasVM::class.java)
        competencyAreasVM.get().observe(this, Observer { position ->
            title = position.name
        })
        competencyAreasVM.getAll().observe(this, Observer { competencyAreas ->
            viewAdapter.updateData(competencyAreas)
        })

        fabCompetencyAreasNew.setOnClickListener { new() }

        recyclerViewCompetencyAreas.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    @SuppressLint("InflateParams")
    private fun new() {
        val builder = AlertDialog.Builder(this)
        val input = layoutInflater.inflate(R.layout.dialog, null)

        builder
            .setTitle(R.string.dialog_new_competency_area)
            .setView(input)
            .setPositiveButton(R.string.dialog_new_apply) { _, _ ->
                competencyAreasVM
                    .newCompetencyArea(input.editTextNameDialog.editableText.toString())
            }
            .setNeutralButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }
}