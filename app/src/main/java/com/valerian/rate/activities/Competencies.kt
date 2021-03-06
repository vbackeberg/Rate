package com.valerian.rate.activities

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.valerian.rate.R
import com.valerian.rate.entities.CompetencyWithScore
import com.valerian.rate.viewadapters.CompetenciesAdapter
import com.valerian.rate.viewmodels.CompetenciesVM
import kotlinx.android.synthetic.main.activity_competencies.*
import kotlinx.android.synthetic.main.content_competencies.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.coroutines.launch

@SuppressLint("InflateParams")
class Competencies : AppCompatActivity() {
    private lateinit var fabAnimator: Animator
    private lateinit var competenciesVM: CompetenciesVM
    private lateinit var selectedCompetency: CompetencyWithScore

    private val actionModeCallback = object : ActionModeCallback() {
        override fun onActionItemClicked(actionMode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_actionbar_rename -> rename(actionMode)
                R.id.menu_actionbar_delete -> {
                    competenciesVM.delete(selectedCompetency.competency)
                    actionMode.finish()
                }
            }
            return true
        }
    }

    private val onItemLongClickListener = View.OnLongClickListener { view ->
        selectedCompetency = view.tag as CompetencyWithScore

        startActionMode(actionModeCallback)
        true
    }

    private val onSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            selectedCompetency = seekBar.tag as CompetencyWithScore
            selectedCompetency.score.value = progress
            competenciesVM.update(selectedCompetency.score)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {}
        override fun onStopTrackingTouch(p0: SeekBar?) {}
    }

    private val viewAdapter = CompetenciesAdapter(onItemLongClickListener, onSeekBarChangeListener)
    private val viewManager = LinearLayoutManager(this)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_competencies)

        competenciesVM = ViewModelProvider(this).get(CompetenciesVM::class.java)
        competenciesVM.getAll().observe(this, Observer { competencies ->
            viewAdapter.updateData(competencies)
            if (competencies.isEmpty()) enableTutorial() else disableTutorial()
        })

        textViewTitleCompetencies.text = "Debug: Bewerber-Id: ${competenciesVM.applicantId}"

        lifecycleScope.launch {
            title = resources.getString(
                R.string.competencies_toolbar_title,
                competenciesVM.competencyArea.await().name
            )
        }

        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
        fabAnimator.setTarget(fabCompetenciesNew)

        fabCompetenciesNew.setOnClickListener { new() }

        exFabCompetenciesFinish.setOnClickListener {
            startActivity(Intent(this, Evaluation::class.java))
        }

        recyclerViewCompetencies.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun new() {
        val input = layoutInflater.inflate(R.layout.dialog, null)
        AlertDialog.Builder(this)
            .setTitle(R.string.competencies_dialog_new)
            .setView(input)
            .setPositiveButton(R.string.dialog_new_apply) { _, _ ->
                competenciesVM.newCompetency(
                    input.editTextNameDialog.editableText.toString()
                )
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
            .setTitle(R.string.competencies_dialog_rename)
            .setView(input)
            .setPositiveButton(R.string.dialog_rename_apply) { _, _ ->
                selectedCompetency.competency.name =
                    input.editTextNameDialog.editableText.toString()
                competenciesVM.update(selectedCompetency.competency)
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
        textViewTutorialCompetencies.visibility = View.VISIBLE
        fabAnimator.start()
    }

    private fun disableTutorial() {
        textViewTutorialCompetencies.visibility = View.GONE
        fabAnimator.end()
    }
}
