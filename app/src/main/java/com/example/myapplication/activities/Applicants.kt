package com.example.myapplication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.R
import com.example.myapplication.viewadapters.ApplicantsAdapter
import com.example.myapplication.viewmodels.ApplicantsVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_applicants.*

class Applicants : AppCompatActivity() {
    private var viewAdapter: ApplicantsAdapter = ApplicantsAdapter()
    private var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var applicantsVm: ApplicantsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicants)
        setSupportActionBar(toolbar)

        applicantsVm = ViewModelProviders.of(this).get(ApplicantsVM::class.java)
        applicantsVm.getAll().observe(this, Observer { applicants ->
            viewAdapter.updateData(applicants)
        })

        this.getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)

        fabNewApplicant.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
