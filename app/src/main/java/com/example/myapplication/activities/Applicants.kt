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
import com.example.myapplication.R
import com.example.myapplication.entities.Position
import com.example.myapplication.viewadapters.ApplicantsAdapter
import com.example.myapplication.viewmodels.ApplicantsVM
import kotlinx.android.synthetic.main.activity_applicants.*
import kotlinx.android.synthetic.main.content_applicants.*

class Applicants : AppCompatActivity() {
    private val viewAdapter: ApplicantsAdapter = ApplicantsAdapter()
    private val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private var departmentId = 0L
    private var positionId = 0L
    private lateinit var position: Position
    private lateinit var applicantsVm: ApplicantsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicants)

        applicantsVm = ViewModelProviders.of(this).get(ApplicantsVM::class.java)
        applicantsVm.get().observe(this, Observer { position ->
            this.position = position
            title = resources.getString(R.string.applicants_toolbar_title, position.name)
        })
        applicantsVm.getAll().observe(this, Observer { applicants ->
            viewAdapter.updateData(applicants)
        })

        fabApplicantsNew.setOnClickListener { applicantsVm.new() }

        recyclerViewApplicants.apply {
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

    private fun rename(): Boolean {
        val builder = AlertDialog.Builder(this)
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(position.name)

        builder
            .setTitle(R.string.dialog_rename_apply)
            .setView(input)
            .setPositiveButton(R.string.dialog_rename_apply) { _, _ ->
                position.name = input.editableText.toString()
                applicantsVm.updatePosition(position)
            }
            .setNegativeButton(R.string.dialog_rename_cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()

        return true
    }
}
