package com.example.myapplication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.viewadapters.EvaluationAdapter
import com.example.myapplication.viewmodels.ApplicantsVM
import kotlinx.android.synthetic.main.activity_evaluation.*
import kotlinx.android.synthetic.main.content_evaluation.*

class Evaluation : AppCompatActivity() {
    private val viewAdapter: EvaluationAdapter = EvaluationAdapter()
    private val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var applicantsVm: ApplicantsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)
        setSupportActionBar(toolbar)

        applicantsVm = ViewModelProviders.of(this).get(ApplicantsVM::class.java)
        applicantsVm.getAll().observe(this, Observer { applicants ->
            viewAdapter.updateData(applicants)
        })

        recyclerViewEvaluation.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
