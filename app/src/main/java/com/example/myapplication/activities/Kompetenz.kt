package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.viewmodels.CURRENT_APPLICANT_ID
import com.example.myapplication.viewmodels.KompetenzVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_kompetenz.*
import kotlinx.android.synthetic.main.content_kompetenz.*

class Kompetenz : AppCompatActivity() {

    private lateinit var kompetenzVM: KompetenzVM
    private lateinit var sharedPreferences: SharedPreferences
    private var applicantId = 0L

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kompetenz)
        setSupportActionBar(toolbar)

        sharedPreferences = this
            .getSharedPreferences(CURRENT_APPLICANT_ID, Context.MODE_PRIVATE)

        applicantId = sharedPreferences
            .getLong(CURRENT_APPLICANT_ID, 0L)

        kompetenzVM = ViewModelProviders.of(this).get(KompetenzVM::class.java)
        kompetenzVM.getBerufserfahrung().observe(this, Observer { newBerufserfahrung ->
            textViewProgress.text = "Progress: $newBerufserfahrung."
        })

        Log.d("Applicant Kompetenz Id", "$applicantId")

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.d("Applicant Kompetenz progressint", "$progress")
                kompetenzVM.updateBerufserfahrung(applicantId, progress)
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
