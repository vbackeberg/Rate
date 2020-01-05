package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competencies)

        applicantId = this
            .getSharedPreferences(CURRENT_APPLICANT_ID, MODE_PRIVATE)
            .getLong(CURRENT_APPLICANT_ID, 0L)

        positionId = this
            .getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
            .getLong(CURRENT_POSITION_ID, 0L)

        textViewTitleCompetencies.text = "Bewerber-Id: $applicantId"

        competenciesVM = ViewModelProviders.of(this).get(CompetenciesVM::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val competencyAreaName = competenciesVM.getCompetencyArea().name
            title = resources.getString(R.string.competencies_toolbar_title, competencyAreaName)
        }

        viewAdapter = CompetenciesAdapter(this)
        competenciesVM.getAll().observe(this, Observer { competencies ->
            viewAdapter.updateData(competencies)
        })

        fabCompetenciesNew.setOnClickListener { competenciesVM.new("test competency") }

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
}
