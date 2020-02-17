package com.example.myapplication.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.CURRENT_COMPETENCY_AREA_ID
import com.example.myapplication.R
import com.example.myapplication.SELECTED_IDS
import com.example.myapplication.entities.CompetencyAreaWithImportance
import com.example.myapplication.viewadapters.CompetencyAreasAdapter
import com.example.myapplication.viewmodels.CompetencyAreasVM
import kotlinx.android.synthetic.main.activity_competency_areas.*
import kotlinx.android.synthetic.main.content_competency_areas.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("InflateParams")
class CompetencyAreas : AppCompatActivity() {
    private lateinit var fabAnimator: Animator
    private lateinit var competencyAreasVM: CompetencyAreasVM
    private lateinit var selectedCompetencyArea: CompetencyAreaWithImportance

    private val actionModeCallback = object : ActionModeCallback() {
        override fun onActionItemClicked(actionMode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_actionbar_rename -> rename(actionMode)
                R.id.menu_actionbar_delete -> {
                    competencyAreasVM.delete(selectedCompetencyArea.competencyArea)
                    actionMode.finish()
                }
            }
            return true
        }
    }

    private val onItemClickListener = View.OnClickListener { view ->
        selectedCompetencyArea = view.tag as CompetencyAreaWithImportance
        getSharedPreferences(SELECTED_IDS, MODE_PRIVATE)
            .edit { putLong(CURRENT_COMPETENCY_AREA_ID, selectedCompetencyArea.id) }

        startActivity(Intent(this, Competencies::class.java))
    }

    private val onItemLongClickListener = View.OnLongClickListener { view ->
        selectedCompetencyArea = view.tag as CompetencyAreaWithImportance

        startActionMode(actionModeCallback)
        true
    }

    private val onSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            selectedCompetencyArea = seekBar.tag as CompetencyAreaWithImportance
            selectedCompetencyArea.importance.value = progress
            competencyAreasVM.update(selectedCompetencyArea.importance)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {}
        override fun onStopTrackingTouch(p0: SeekBar?) {}
    }

    private val viewAdapter = CompetencyAreasAdapter(
        onItemClickListener,
        onItemLongClickListener,
        onSeekBarChangeListener
    )
    private val viewManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competency_areas)

        competencyAreasVM = ViewModelProvider(this).get(CompetencyAreasVM::class.java)
        competencyAreasVM.competencyAreas.observe(this, Observer { competencyAreas ->
            viewAdapter.updateData(competencyAreas)
            if (competencyAreas.isEmpty()) enableTutorial() else disableTutorial()
        })

        CoroutineScope(Dispatchers.Main).launch {
            title = resources.getString(
                R.string.competency_areas_toolbar_title,
                competencyAreasVM.position.await().name
            )
        }

        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
            .apply { setTarget(fabCompetencyAreasNew) }

        fabCompetencyAreasNew.setOnClickListener { new() }

        recyclerViewCompetencyAreas.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun new() {
        val input = layoutInflater.inflate(R.layout.dialog, null)
        AlertDialog.Builder(this)
            .setTitle(R.string.competency_areas_dialog_new)
            .setView(input)
            .setPositiveButton(R.string.dialog_new_apply) { _, _ ->
                competencyAreasVM
                    .newCompetencyArea(input.editTextNameDialog.editableText.toString())
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
            .setTitle(R.string.competency_areas_dialog_rename)
            .setView(input)
            .setPositiveButton(R.string.dialog_rename_apply) { _, _ ->
                selectedCompetencyArea.competencyArea.name =
                    input.editTextNameDialog.editableText.toString()
                competencyAreasVM.update(selectedCompetencyArea.competencyArea)
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
        textViewTutorialCompetencyAreas.visibility = View.VISIBLE
        fabAnimator.start()
    }

    private fun disableTutorial() {
        textViewTutorialCompetencyAreas.visibility = View.GONE
        fabAnimator.end()
    }
}