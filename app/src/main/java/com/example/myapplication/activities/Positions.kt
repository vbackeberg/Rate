package com.example.myapplication.activities

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.R
import com.example.myapplication.entities.Department
import com.example.myapplication.entities.Position
import com.example.myapplication.viewadapters.PositionsAdapter
import com.example.myapplication.viewmodels.DepartmentsVM
import com.example.myapplication.viewmodels.PositionsVM
import kotlinx.android.synthetic.main.activity_positions.*
import kotlinx.android.synthetic.main.content_positions.*

class Positions : AppCompatActivity() {
    private var viewAdapter: PositionsAdapter = PositionsAdapter()
    private var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var positionsVM: PositionsVM
    private lateinit var departmentsVm: DepartmentsVM
    private lateinit var department: Department

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_positions)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val departmentId = this
            .getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
            .getLong(CURRENT_DEPARTMENT_ID, 0L)

        departmentsVm = ViewModelProviders.of(this).get(DepartmentsVM::class.java)
        departmentsVm.get(departmentId).observe(this, Observer { department ->
            this.department = department
            title = department.name
        })

        positionsVM = ViewModelProviders.of(this).get(PositionsVM::class.java)
        positionsVM.getAll().observe(this, Observer { positions ->
            viewAdapter.updateData(positions)
        })

        fabPositionsNew.setOnClickListener {
            positionsVM.new(Position(0L, "neue Position"))
        }

        recyclerViewPositions.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_positions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_rename -> rename()
            else             -> super.onOptionsItemSelected(item)
        }
    }

    private fun rename(): Boolean {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(R.string.dialog_rename_apply)
        val inputField = EditText(this)
        inputField.inputType = InputType.TYPE_CLASS_TEXT
        alert.setView(inputField)

        alert.setPositiveButton(
            R.string.dialog_rename_apply
        ) { _, _ ->
            department.name = inputField.editableText.toString()
            departmentsVm.update(department)
        }

        alert.setNegativeButton(R.string.dialog_rename_cancel)
        { dialog, _ ->
            dialog.cancel()
        }

        alert.create().show()
        return true
    }
}