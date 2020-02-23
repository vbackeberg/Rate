package com.valerian.rate.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valerian.rate.CURRENT_DEPARTMENT_ID
import com.valerian.rate.CURRENT_POSITION_ID
import com.valerian.rate.R
import com.valerian.rate.SELECTED_IDS
import com.valerian.rate.viewadapters.EvaluationAdapter
import com.valerian.rate.viewmodels.EvaluationVM
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

        currentDepartmentId = getSharedPreferences(SELECTED_IDS, Context.MODE_PRIVATE)
            .getLong(CURRENT_DEPARTMENT_ID, 0L)
        currentPositionId = getSharedPreferences(SELECTED_IDS, Context.MODE_PRIVATE)
            .getLong(CURRENT_POSITION_ID, 0L)

        evaluationVM = ViewModelProvider(this).get(EvaluationVM::class.java)
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
