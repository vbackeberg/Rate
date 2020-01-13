package com.example.myapplication.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
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
    private lateinit var fabAnimator: Animator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competency_areas)
        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
            .apply { setTarget(fabCompetencyAreasNew) }

        viewAdapter = CompetencyAreasAdapter(this)

        competencyAreasVM = ViewModelProviders.of(this).get(CompetencyAreasVM::class.java)
        competencyAreasVM.get().observe(this, Observer { position ->
            title = resources.getString(R.string.competency_areas_toolbar_title, position.name)
        })
        competencyAreasVM.getAll().observe(this, Observer { competencyAreas ->
            viewAdapter.updateData(competencyAreas)
            if (competencyAreas.isEmpty()) enableTutorial() else disableTutorial()
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
            .setTitle(R.string.competency_areas_dialog_new)
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

    private fun enableTutorial() {
        textViewTutorialCompetencyAreas.visibility = View.VISIBLE
        fabAnimator.start()
    }

    private fun disableTutorial() {
        textViewTutorialCompetencyAreas.visibility = View.GONE
        fabAnimator.end()
    }
}