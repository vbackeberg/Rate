package com.example.myapplication.activities

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.viewmodels.ApplicantVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_kompetenz.*
import kotlinx.android.synthetic.main.content_kompetenz.*

class Kompetenz : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kompetenz)
        setSupportActionBar(toolbar)

        val viewModel = ViewModelProviders.of(this)[ApplicantVM::class.java]
//        viewModel.getApplicant().observe(this, Observer<Applicant> { })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setCompetency(progress)
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
}
