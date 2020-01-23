package com.example.myapplication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.R
import com.example.myapplication.viewadapters.EvaluationAdapter
import com.example.myapplication.viewmodels.EvaluationVM
import kotlinx.android.synthetic.main.content_evaluation.*

class Evaluation : AppCompatActivity() {
    private lateinit var evaluationVM: EvaluationVM
    private val viewAdapter: EvaluationAdapter = EvaluationAdapter()
    private val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

    private var currentDepartmentId = 0L
    private var currentPositionId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluation)

        currentDepartmentId = intent.getLongExtra(CURRENT_DEPARTMENT_ID, 0L)
        currentPositionId = intent.getLongExtra(CURRENT_POSITION_ID, 0L)

        evaluationVM = ViewModelProviders.of(this).get(EvaluationVM::class.java)
        evaluationVM.getAll(currentPositionId, currentDepartmentId)
            .observe(this, Observer { applicants ->
                viewAdapter.updateData(applicants)
            })

        recyclerViewEvaluation.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
