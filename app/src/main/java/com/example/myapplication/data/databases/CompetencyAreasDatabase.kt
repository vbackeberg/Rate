package com.example.myapplication.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.daos.*
import com.example.myapplication.entities.*

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
    version = 12
)
abstract class CompetencyAreasDatabase : RoomDatabase() {
    abstract fun competencyAreaDao(): CompetencyAreaDao
    abstract fun importanceDao(): ImportanceDao
    abstract fun positionDao(): PositionDao
    abstract fun competencyDao(): CompetencyDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun applicantDao(): ApplicantDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile
        private var INSTANCE: CompetencyAreasDatabase? = null

        fun getDatabase(context: Context): CompetencyAreasDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        CompetencyAreasDatabase::class.java,
                        "competency_areas_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
