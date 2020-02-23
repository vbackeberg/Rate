package com.valerian.rate.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.valerian.rate.data.daos.*
import com.valerian.rate.entities.*

@Database(
    entities = [
        CompetencyArea::class,
        Importance::class,
        Position::class,
        Competency::class,
        Department::class,
        Applicant::class,
        Score::class
    ],
    version = 18
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun competencyAreaDao(): CompetencyAreaDao
    abstract fun importanceDao(): ImportanceDao
    abstract fun positionDao(): PositionDao
    abstract fun competencyDao(): CompetencyDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun applicantDao(): ApplicantDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
