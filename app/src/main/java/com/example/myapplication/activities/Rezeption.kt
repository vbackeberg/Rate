package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.viewmodels.ApplicantVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_rezeption.*
import kotlinx.android.synthetic.main.content_rezeption.*

class Rezeption : AppCompatActivity() {

    private lateinit var applicantVM: ApplicantVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezeption)
        setSupportActionBar(toolbar)

        applicantVM = ViewModelProviders.of(this).get(ApplicantVM::class.java)

        val applicantId = intent.getLongExtra("applicantId", 0L)

        textViewApplicationId.text = "applicantId $applicantId"

        applicantVM.getCompetency().observe(this, Observer<Int> {
                newCompetency -> textViewCompetency.text = newCompetency.toString()
        })

        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun student(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, Kompetenz::class.java)
        startActivity(intent)
    }
}