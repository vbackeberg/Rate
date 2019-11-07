package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.viewmodels.ApplicantVM
import com.example.myapplication.viewmodels.CURRENT_APPLICANT_ID
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var applicantVM: ApplicantVM
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        sharedPreferences = this
            .getSharedPreferences(CURRENT_APPLICANT_ID, Context.MODE_PRIVATE)

        applicantVM = ViewModelProviders.of(this)
            .get(ApplicantVM::class.java)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    fun rezeption(@Suppress("UNUSED_PARAMETER") view: View) {
        val applicantId = applicantVM.newApplicant()
        Log.d("Applicant Main Activity Id", "$applicantId")

        sharedPreferences
            .edit()
            .putLong(CURRENT_APPLICANT_ID, applicantId)
            .apply()

        Log.d(
            "Applicant Main Activity shpref id",
            "${sharedPreferences.getLong(CURRENT_APPLICANT_ID, 0L)}"
        )

        val intent = Intent(this, Kompetenz::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}