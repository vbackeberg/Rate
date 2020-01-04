package com.example.myapplication.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.R
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.viewadapters.CompetencyAreasAdapter
import com.example.myapplication.viewmodels.CompetencyAreasVM
import kotlinx.android.synthetic.main.activity_competency_areas.*
import kotlinx.android.synthetic.main.content_competency_areas.*

class CompetencyAreas : AppCompatActivity() {
    private lateinit var viewAdapter: CompetencyAreasAdapter
    private var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var competencyAreasVM: CompetencyAreasVM
    private var positionId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competency_areas)

        positionId = this
            .getSharedPreferences(CURRENT_POSITION_ID, Context.MODE_PRIVATE)
            .getLong(CURRENT_POSITION_ID, 0L)

        viewAdapter = CompetencyAreasAdapter(this)

        competencyAreasVM = ViewModelProviders.of(this).get(CompetencyAreasVM::class.java)
        competencyAreasVM.getAll(positionId).observe(this, Observer { competencyAreas ->
            viewAdapter.updateData(competencyAreas)
        })

        fabCompetencyAreasNew.setOnClickListener {
            competencyAreasVM.new(CompetencyArea(0L, "neuer Kompetenzbereich"))
        }

        recyclerViewCompetencyAreas.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}