package com.example.myapplication.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private var viewAdapter: AbteilungenAdapter = AbteilungenAdapter()
    private var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var abteilungenVM: AbteilungenVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        abteilungenVM = ViewModelProviders.of(this).get(AbteilungenVM::class.java)
        abteilungenVM.get().observe(this, Observer { abteilungen ->
            viewAdapter.updateData(abteilungen)
        })

        fab.setOnClickListener {
            abteilungenVM.new(Abteilung(0L, "asdasd"))
        }

        recyclerViewAbteilungen.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
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