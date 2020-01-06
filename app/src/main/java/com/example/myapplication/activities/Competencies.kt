package com.example.myapplication.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.R
import com.example.myapplication.services.ScoreService
import com.example.myapplication.viewadapters.CompetenciesAdapter
import com.example.myapplication.viewmodels.CompetenciesVM
import kotlinx.android.synthetic.main.activity_competencies.*
import kotlinx.android.synthetic.main.content_competencies.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Competencies : AppCompatActivity() {
    private lateinit var viewAdapter: CompetenciesAdapter
    private var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private var applicantId = 0L
    private var positionId = 0L
    private lateinit var competenciesVM: CompetenciesVM
    private lateinit var scoreService: ScoreService
    private lateinit var fabAnimator: Animator

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competencies)
        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
            .apply { setTarget(fabCompetenciesNew) }

        applicantId = this
            .getSharedPreferences(CURRENT_APPLICANT_ID, MODE_PRIVATE)
            .getLong(CURRENT_APPLICANT_ID, 0L)

        positionId = this
            .getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
            .getLong(CURRENT_POSITION_ID, 0L)

        textViewTitleCompetencies.text = "Bewerber-Id: $applicantId"

        competenciesVM = ViewModelProviders.of(this).get(CompetenciesVM::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            title = competenciesVM.get().name
        }

        viewAdapter = CompetenciesAdapter(this)
        competenciesVM.getAll().observe(this, Observer { competencies ->
            viewAdapter.updateData(competencies)
            if (competencies.isEmpty()) enableTutorial() else disableTutorial()
        })

        fabCompetenciesNew.setOnClickListener { new() }

        scoreService = ScoreService.getInstance(application)
        exFabCompetenciesFinish.setOnClickListener {
            startActivity(Intent(this, Evaluation::class.java))
        }

        recyclerViewCompetencies.apply {
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
            .setTitle(R.string.dialog_new_competency)
            .setView(input)
            .setPositiveButton(R.string.dialog_new_apply) { _, _ ->
                competenciesVM.newCompetency(input.editTextNameDialog.editableText.toString())
            }
            .setNeutralButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun enableTutorial() {
        textViewTutorialCompetencies.visibility = View.VISIBLE
        fabAnimator.start()
    }

    private fun disableTutorial() {
        textViewTutorialCompetencies.visibility = View.GONE
        fabAnimator.end()
    }
}
