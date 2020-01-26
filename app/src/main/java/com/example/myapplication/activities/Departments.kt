package com.example.myapplication.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.R
import com.example.myapplication.entities.Department
import com.example.myapplication.viewadapters.DepartmentsAdapter
import com.example.myapplication.viewmodels.DepartmentsVM
import kotlinx.android.synthetic.main.activity_departments.*
import kotlinx.android.synthetic.main.content_departments.*
import kotlinx.android.synthetic.main.dialog.view.*

@SuppressLint("InflateParams")
class Departments : AppCompatActivity() {
    private lateinit var departmentsVM: DepartmentsVM
    private lateinit var fabAnimator: Animator
    private lateinit var selectedDepartment: Department

    private val actionModeCallback = object : ActionModeCallback() {
        override fun onActionItemClicked(actionMode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_actionbar_rename -> rename(actionMode)
                R.id.menu_actionbar_delete -> {
                    departmentsVM.delete(selectedDepartment)
                    actionMode.finish()
                }
            }
            return true
        }
    }

    private val onItemClickListener = View.OnClickListener { view ->
        selectedDepartment = view.tag as Department

        getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
            .edit().putLong(CURRENT_DEPARTMENT_ID, selectedDepartment.id)
            .apply()

        startActivity(Intent(this, Positions::class.java))
    }

    private val onItemLongClickListener = View.OnLongClickListener { view ->
        selectedDepartment = view.tag as Department

        startActionMode(actionModeCallback)
        true
    }

    private var viewAdapter = DepartmentsAdapter(onItemClickListener, onItemLongClickListener)
    private var viewManager = GridLayoutManager(this, 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departments)

        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
            .apply { setTarget(fabDepartmentsNew) }

        departmentsVM = ViewModelProvider(this).get(DepartmentsVM::class.java)
        departmentsVM.getAll().observe(this, Observer { departments ->
            viewAdapter.updateData(departments)
            if (departments.isEmpty()) enableTutorial() else disableTutorial()
        })

        recyclerViewDepartments.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        fabDepartmentsNew.setOnClickListener { new() }
    }

    private fun new() {
        val input = layoutInflater.inflate(R.layout.dialog, null)
        AlertDialog.Builder(this)
            .setTitle(R.string.departments_dialog_new)
            .setView(input)
            .setPositiveButton(R.string.dialog_new_apply) { _, _ ->
                departmentsVM.newDepartment(input.editTextNameDialog.editableText.toString())
            }
            .setNeutralButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun rename(actionMode: ActionMode) {
        val input = layoutInflater.inflate(R.layout.dialog, null)
        AlertDialog.Builder(this)
            .setTitle(R.string.departments_dialog_rename)
            .setView(input)
            .setPositiveButton(R.string.dialog_rename_apply) { _, _ ->
                selectedDepartment.name = input.editTextNameDialog.editableText.toString()
                departmentsVM.update(selectedDepartment)
                actionMode.finish()
            }
            .setNeutralButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
                actionMode.finish()
            }
            .create()
            .show()
    }

    private fun enableTutorial() {
        textViewTutorialDepartments.visibility = View.VISIBLE
        fabAnimator.start()
    }

    private fun disableTutorial() {
        textViewTutorialDepartments.visibility = View.GONE
        fabAnimator.end()
    }
}
