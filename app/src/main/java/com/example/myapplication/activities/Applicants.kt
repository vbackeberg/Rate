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
import com.example.myapplication.entities.Applicant
import com.example.myapplication.viewadapters.ApplicantsAdapter
import com.example.myapplication.viewmodels.ApplicantsVM
import kotlinx.android.synthetic.main.activity_applicants.*
import kotlinx.android.synthetic.main.content_applicants.*

class Applicants : AppCompatActivity() {
    private val viewAdapter: ApplicantsAdapter = ApplicantsAdapter()
    private val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private var departmentId = 0L
    private var positionId = 0L
    private lateinit var applicantsVm: ApplicantsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicants)
        setSupportActionBar(toolbar)

        departmentId = this
            .getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
            .getLong(CURRENT_DEPARTMENT_ID, 0L)

        positionId = this
            .getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
            .getLong(CURRENT_POSITION_ID, 0L)

        applicantsVm = ViewModelProviders.of(this).get(ApplicantsVM::class.java)
        applicantsVm.getAll().observe(this, Observer { applicants ->
            viewAdapter.updateData(applicants)
        })

        fabApplicantsNew.setOnClickListener {
            applicantsVm.new(Applicant(0L, positionId, departmentId))
        }

        recyclerViewApplicants.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
