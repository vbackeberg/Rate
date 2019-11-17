package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_rezeption.*

class Rezeption : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rezeption)
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun student(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, Competencies::class.java)
        startActivity(intent)
    }
}