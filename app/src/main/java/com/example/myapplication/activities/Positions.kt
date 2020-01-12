package com.example.myapplication.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Department
import com.example.myapplication.viewadapters.PositionsAdapter
import com.example.myapplication.viewmodels.PositionsVM
import kotlinx.android.synthetic.main.activity_positions.*
import kotlinx.android.synthetic.main.content_positions.*
import kotlinx.android.synthetic.main.dialog.view.*

class Positions : AppCompatActivity() {
    private var viewAdapter: PositionsAdapter = PositionsAdapter()
    private var viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var positionsVM: PositionsVM
    private lateinit var department: Department
    private lateinit var fabAnimator: Animator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_positions)
        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
            .apply { setTarget(fabPositionsNew) }

        positionsVM = ViewModelProviders.of(this).get(PositionsVM::class.java)
        positionsVM.get().observe(this, Observer { department ->
            this.department = department
            title = department.name
        })
        positionsVM.getAll().observe(this, Observer { positions ->
            viewAdapter.updateData(positions)
            if (positions.isEmpty()) enableTutorial() else disableTutorial()
        })

        fabPositionsNew.setOnClickListener { new() }

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
            R.id.menu_item_rename -> rename()
            else                  -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("InflateParams")
    private fun new() {
        val builder = AlertDialog.Builder(this)
        val input = layoutInflater.inflate(R.layout.dialog, null)

        builder
            .setTitle(R.string.dialog_new_position)
            .setView(input)
            .setPositiveButton(R.string.dialog_new_apply) { _, _ ->
                positionsVM.newPosition(input.editTextNameDialog.editableText.toString())
            }
            .setNeutralButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    @SuppressLint("InflateParams")
    private fun rename(): Boolean {
        val builder = AlertDialog.Builder(this)
        val input = layoutInflater.inflate(R.layout.dialog, null)

        builder
            .setTitle(R.string.dialog_rename_apply)
            .setView(input)
            .setPositiveButton(R.string.dialog_rename_apply) { _, _ ->
                department.name = input.editTextNameDialog.editableText.toString()
                positionsVM.update(department)
            }
            .setNegativeButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()

        return true
    }

    private fun enableTutorial() {
        textViewTutorialPositions.visibility = View.VISIBLE
        fabAnimator.start()
    }

    private fun disableTutorial() {
        textViewTutorialPositions.visibility = View.GONE
        fabAnimator.end()
    }
}
