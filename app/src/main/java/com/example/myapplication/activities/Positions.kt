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
import com.example.myapplication.entities.Position
import com.example.myapplication.viewadapters.PositionsAdapter
import com.example.myapplication.viewmodels.PositionsVM
import kotlinx.android.synthetic.main.activity_positions.*
import kotlinx.android.synthetic.main.content_positions.*

class Positions : AppCompatActivity() {
    private var viewAdapter: PositionsAdapter = PositionsAdapter()
    private var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var positionsVM: PositionsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_positions)
        setSupportActionBar(toolbar)

        positionsVM = ViewModelProviders.of(this).get(PositionsVM::class.java)
        positionsVM.getAll().observe(this, Observer { positions ->
            viewAdapter.updateData(positions)
        })

        fabPositionsNew.setOnClickListener {
            positionsVM.new(Position(0L, "asdasd"))
        }

        recyclerViewPositions.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else                 -> super.onOptionsItemSelected(item)
        }
    }
}