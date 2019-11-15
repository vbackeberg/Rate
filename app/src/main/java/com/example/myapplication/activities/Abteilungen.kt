package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.AbteilungenAdapter
import com.example.myapplication.entities.Abteilung
import com.example.myapplication.viewmodels.AbteilungenVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class Abteilungen : AppCompatActivity() {
    private lateinit var viewAdapter: AbteilungenAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var abteilungenVM: AbteilungenVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewAdapter = AbteilungenAdapter(abteilungen = emptyList())

        abteilungenVM = ViewModelProviders.of(this).get(AbteilungenVM::class.java)
        abteilungenVM.getAbteilungen().observe(this, Observer { abteilungen ->
            viewAdapter.updateData(abteilungen)
        })

        viewManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            abteilungenVM.newAbteilung(Abteilung(0L, "asdasd"))
        }

        recyclerViewAbteilungen.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    fun positionen(@Suppress("UNUSED_PARAMETER") view: View) {
        val intent = Intent(this, Positionen::class.java)
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