package com.example.myapplication.activities

import android.os.Bundle
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
            title = resources.getString(R.string.competency_areas_toolbar_title, position.name)
        })
        competencyAreasVM.getAll().observe(this, Observer { competencyAreas ->
            viewAdapter.updateData(competencyAreas)
        })

        fabCompetencyAreasNew.setOnClickListener { competencyAreasVM.new("Kompetenzbereich") }

        recyclerViewCompetencyAreas.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}