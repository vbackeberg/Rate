package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.SELECTED_IDS
import com.example.myapplication.data.databases.AppDatabase
import com.example.myapplication.entities.Applicant
import com.example.myapplication.entities.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicantsVM(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val applicantDao = database.applicantDao()
    private val competencyDao = database.competencyDao()
    private val scoreDao = database.scoreDao()

    private val positionId = MutableLiveData<Long>(
        application.getSharedPreferences(SELECTED_IDS, Context.MODE_PRIVATE).getLong(
            CURRENT_POSITION_ID,
            0L
        )
    )

    private val departmentId = MutableLiveData<Long>(
        application.getSharedPreferences(SELECTED_IDS, Context.MODE_PRIVATE).getLong(
            CURRENT_DEPARTMENT_ID,
            0L
        )
    )

    val applicants = Transformations.switchMap(
        MutableLiveData<List<Long>>(listOfNotNull(positionId.value, departmentId.value)),
        ::getAll
    )

    fun newApplicant(name: String) =
        CoroutineScope(Dispatchers.IO).launch {
            database.runInTransaction {
                val applicantId = applicantDao
                    .insert(Applicant(0L, positionId.value!!, departmentId.value!!, name))
                val scores = mutableListOf<Score>()
                competencyDao.findAllIds().forEach { competencyId ->
                    scores.add(Score(competencyId, applicantId, 0))
                }
                scoreDao.insertMany(scores)
            }
        }

    fun update(applicant: Applicant) = viewModelScope.launch {
        applicantDao.update(applicant)
    }

    fun delete(applicant: Applicant) = viewModelScope.launch {
        applicantDao.delete(applicant)
    }

    private fun getAll(ids: List<Long>): LiveData<MutableList<Applicant>> {
        return applicantDao.findAllByPositionAndDepartment(ids[0], ids[1])
    }
}
