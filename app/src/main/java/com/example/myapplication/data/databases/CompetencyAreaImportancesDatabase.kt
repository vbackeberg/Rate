package com.example.myapplication.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.daos.CompetencyAreaImportanceDao
import com.example.myapplication.entities.CompetencyAreaImportance

@Database(entities = [CompetencyAreaImportance::class], version = 1)
abstract class CompetencyAreaImportancesDatabase : RoomDatabase() {
    abstract fun competencyAreaImportanceDao(): CompetencyAreaImportanceDao

    companion object {
        @Volatile
        private var INSTANCE: CompetencyAreaImportancesDatabase? = null

        fun getDatabase(context: Context): CompetencyAreaImportancesDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        CompetencyAreaImportancesDatabase::class.java,
                        "competency_area_importances_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
