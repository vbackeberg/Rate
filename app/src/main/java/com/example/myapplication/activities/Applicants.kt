package com.example.myapplication.activities

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.R
import com.example.myapplication.entities.Applicant
import com.example.myapplication.entities.Position
import com.example.myapplication.viewadapters.ApplicantsAdapter
import com.example.myapplication.viewmodels.ApplicantsVM
import kotlinx.android.synthetic.main.activity_applicants.*
import kotlinx.android.synthetic.main.content_applicants.*
import kotlinx.android.synthetic.main.dialog.view.*

@SuppressLint("InflateParams")
class Applicants : AppCompatActivity() {
    private lateinit var applicantsVm: ApplicantsVM
    private lateinit var fabAnimator: Animator
    private lateinit var position: Position
    private lateinit var selectedApplicant: Applicant

    private val actionModeCallback = object : ActionModeCallback() {
        override fun onActionItemClicked(actionMode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_actionbar_rename -> rename(actionMode)
                R.id.menu_actionbar_delete -> {
                    applicantsVm.delete(selectedApplicant)
                    actionMode.finish()
                }
            }
            return true
        }
    }

    private val onItemClickListener = View.OnClickListener { view ->
        selectedApplicant = view.tag as Applicant
        getSharedPreferences(CURRENT_APPLICANT_ID, MODE_PRIVATE)
            .edit().putLong(CURRENT_APPLICANT_ID, selectedApplicant.id)
            .apply()

        startActivity(Intent(this, CompetencyAreas::class.java))
    }

    private val onItemLongClickListener = View.OnLongClickListener { view ->
        selectedApplicant = view.tag as Applicant

        startActionMode(actionModeCallback)
        true
    }

    private val viewAdapter = ApplicantsAdapter(onItemClickListener, onItemLongClickListener)
    private val viewManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicants)
        fabAnimator = AnimatorInflater.loadAnimator(this, R.animator.fab_animator)
            .apply { setTarget(fabApplicantsNew) }

        applicantsVm = ViewModelProviders.of(this).get(ApplicantsVM::class.java)
        applicantsVm.getAll().observe(this, Observer { applicants ->
            viewAdapter.updateData(applicants)
            if (applicants.isEmpty()) enableTutorial() else disableTutorial()
        })

        fabApplicantsNew.setOnClickListener { new() }

        recyclerViewApplicants.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    private fun new() {
        val input = layoutInflater.inflate(R.layout.dialog, null)
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.applicants_dialog_new)
            .setView(input)
            .setPositiveButton(R.string.dialog_new_apply) { _, _ ->
                applicantsVm.newApplicant(input.editTextNameDialog.editableText.toString())
            }
            .setNeutralButton(R.string.dialog_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    private fun rename(actionMode: ActionMode) {
        val input = layoutInflater.inflate(R.layout.dialog, null)
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.applicants_dialog_rename)
            .setView(input)
            .setPositiveButton(R.string.dialog_rename_apply) { _, _ ->
                selectedApplicant.name = input.editTextNameDialog.editableText.toString()
                applicantsVm.update(selectedApplicant)
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
        textViewTutorialApplicants.visibility = View.VISIBLE
        fabAnimator.start()
    }

    private fun disableTutorial() {
        textViewTutorialApplicants.visibility = View.GONE
        fabAnimator.end()
    }
}
