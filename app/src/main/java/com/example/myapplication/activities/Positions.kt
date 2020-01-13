package com.example.myapplication.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.R
import com.example.myapplication.entities.Position
import com.example.myapplication.viewadapters.PositionsAdapter
import com.example.myapplication.viewmodels.PositionsVM
import kotlinx.android.synthetic.main.activity_positions.*
import kotlinx.android.synthetic.main.content_positions.*
import kotlinx.android.synthetic.main.dialog.view.*

@SuppressLint("InflateParams")
class Positions : AppCompatActivity(), ActionMode.Callback {
    private lateinit var positionsVM: PositionsVM
    private lateinit var fabAnimator: Animator
    private lateinit var selectedPosition: Position

    private val onItemClickListener = View.OnClickListener { view ->
        selectedPosition = view.tag as Position
        getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
            .edit().putLong(CURRENT_POSITION_ID, selectedPosition.id)
            .apply()

        startActivity(Intent(this, Applicants::class.java))
    }

    private val onItemLongClickListener = View.OnLongClickListener { view ->
        selectedPosition = view.tag as Position

        startActionMode(this)
        true
    }

    private var viewAdapter = PositionsAdapter(onItemClickListener, onItemLongClickListener)
    private var viewManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_positions)
        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
            .apply { setTarget(fabPositionsNew) }

        positionsVM = ViewModelProviders.of(this).get(PositionsVM::class.java)
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

    override fun onActionItemClicked(actionMode: ActionMode, item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_actionbar_rename -> rename(actionMode)
            R.id.menu_actionbar_delete -> {
                positionsVM.delete(selectedPosition)
                actionMode.finish()
            }
        }
        return true
    }

    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
        val inflater = actionMode.menuInflater
        inflater.inflate(R.menu.menu_actionbar, menu)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {}

    private fun new() {
        val input = layoutInflater.inflate(R.layout.dialog, null)
        AlertDialog.Builder(this)
            .setTitle(R.string.positions_dialog_new)
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

    private fun rename(actionMode: ActionMode): Boolean {
        val input = layoutInflater.inflate(R.layout.dialog, null)
        AlertDialog.Builder(this)
            .setTitle(R.string.positions_dialog_rename)
            .setView(input)
            .setPositiveButton(R.string.dialog_rename_apply) { _, _ ->
                selectedPosition.name = input.editTextNameDialog.editableText.toString()
                positionsVM.update(selectedPosition)
                actionMode.finish()
            }
            .setNeutralButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
                actionMode.finish()
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
