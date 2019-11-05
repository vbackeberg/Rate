package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.viewmodels.ApplicantVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_kompetenz.*
import kotlinx.android.synthetic.main.content_kompetenz.*

class Kompetenz : AppCompatActivity() {

    private lateinit var applicantVM: ApplicantVM
    var applicantId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kompetenz)
        setSupportActionBar(toolbar)

        applicantVM = ViewModelProviders.of(this).get(ApplicantVM::class.java)
        applicantVM.getCompetency().observe(this, Observer {
                newCompetency -> textViewProgress.text = newCompetency.toString()
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.d("Applicant Kompetenz progressint", "$progress")
                applicantVM.updateCompetency(applicantId, progress)
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

    fun nextPage(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, Rezeption::class.java).putExtra("applicantId", applicantId)
        startActivity(intent)
    }
}
