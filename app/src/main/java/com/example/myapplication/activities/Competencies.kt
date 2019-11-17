package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.viewmodels.CURRENT_APPLICANT_ID
import com.example.myapplication.viewmodels.CompetenciesVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_competencies.*

class Competencies : AppCompatActivity() {

    private lateinit var competenciesVM: CompetenciesVM
    private lateinit var sharedPreferences: SharedPreferences
    private var applicantId = 0L

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competencies)
        setSupportActionBar(toolbar)

        sharedPreferences = this
            .getSharedPreferences(CURRENT_APPLICANT_ID, Context.MODE_PRIVATE)

        try {
            competenciesVM = ViewModelProviders.of(this).get(CompetenciesVM::class.java)
            competenciesVM.get().observe(this, Observer {
            })
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        Log.d("Applicant Competencies Id", "$applicantId")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
