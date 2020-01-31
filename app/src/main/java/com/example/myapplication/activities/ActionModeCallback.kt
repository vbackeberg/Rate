package com.example.myapplication.activities

import android.view.ActionMode
import android.view.Menu
import com.example.myapplication.R

abstract class ActionModeCallback : ActionMode.Callback {
    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
        val inflater = actionMode.menuInflater
        inflater.inflate(R.menu.menu_actionbar, menu)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        return true
    }

    override fun onDestroyActionMode(p0: ActionMode?) {}
}