package com.example.myapplication.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.R
import com.example.myapplication.entities.Department
import com.example.myapplication.viewadapters.DepartmentsAdapter
import com.example.myapplication.viewmodels.DepartmentsVM
import kotlinx.android.synthetic.main.activity_departments.*
import kotlinx.android.synthetic.main.content_departments.*
import android.util.Pair as UtilPair

class Departments : AppCompatActivity() {
    private val onItemClickListener = View.OnClickListener { view ->
        val viewHolder = view.tag as DepartmentsAdapter.DepartmentViewHolder

        getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
            .edit().putLong(CURRENT_DEPARTMENT_ID, viewHolder.id)
            .apply()

        val options = ActivityOptions
            .makeSceneTransitionAnimation(
                this,
                UtilPair.create(
                    viewHolder.itemView,
                    "departmentContainer"
                ),
                UtilPair.create(
                    viewHolder.name as View,
                    "departmentName"
                )
            )

        startActivity(Intent(this, Positions::class.java), options.toBundle())
    }

    private var viewAdapter = DepartmentsAdapter(onItemClickListener)
    private var viewManager = GridLayoutManager(this, 3)

    private lateinit var departmentsVM: DepartmentsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departments)
        setSupportActionBar(toolbar)

        departmentsVM = ViewModelProviders.of(this).get(DepartmentsVM::class.java)
        departmentsVM.getAll().observe(this, Observer { departments ->
            viewAdapter.updateData(departments)
        })

        recyclerViewDepartments.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        fabDepartmentsNew.setOnClickListener {
            departmentsVM.new(Department(0L, "neue Abteilung"))
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