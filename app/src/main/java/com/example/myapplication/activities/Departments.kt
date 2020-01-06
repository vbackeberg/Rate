package com.example.myapplication.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.R
import com.example.myapplication.viewadapters.DepartmentsAdapter
import com.example.myapplication.viewmodels.DepartmentsVM
import kotlinx.android.synthetic.main.activity_departments.*
import kotlinx.android.synthetic.main.content_departments.*
import kotlinx.android.synthetic.main.dialog.view.*

class Departments : AppCompatActivity() {
    private val onItemClickListener = View.OnClickListener { view ->
        val viewHolder = view.tag as DepartmentsAdapter.DepartmentViewHolder

        getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
            .edit().putLong(CURRENT_DEPARTMENT_ID, viewHolder.id)
            .apply()

        startActivity(Intent(this, Positions::class.java))
    }

    private var viewAdapter = DepartmentsAdapter(onItemClickListener)
    private var viewManager = GridLayoutManager(this, 3)
    private lateinit var departmentsVM: DepartmentsVM
    private lateinit var fabAnimator: Animator

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_departments)
        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
            .apply { setTarget(fabDepartmentsNew) }

        departmentsVM = ViewModelProviders.of(this).get(DepartmentsVM::class.java)
        departmentsVM.getAll().observe(this, Observer { departments ->
            viewAdapter.updateData(departments)
            if (departments.isEmpty()) enableTutorial() else disableTutorial()
        })

        recyclerViewDepartments.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        fabDepartmentsNew.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val input = layoutInflater.inflate(R.layout.dialog, null)

            builder
                .setTitle(R.string.dialog_new_department)
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