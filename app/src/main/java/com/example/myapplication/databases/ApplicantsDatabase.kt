package com.example.myapplication.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.daos.ApplicantDao
import com.example.myapplication.entities.Applicant

@Database(entities = [Applicant::class], version = 2)
abstract class ApplicantsDatabase : RoomDatabase() {
    abstract fun applicantDao(): ApplicantDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicantsDatabase? = null

        fun getDatabase(context: Context): ApplicantsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        ApplicantsDatabase::class.java,
                        "applicants_database"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}