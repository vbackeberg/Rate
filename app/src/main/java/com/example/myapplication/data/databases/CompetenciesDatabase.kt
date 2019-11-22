package com.example.myapplication.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.daos.CompetencyDao
import com.example.myapplication.entities.Competency

@Database(entities = [Competency::class], version = 2)
abstract class CompetenciesDatabase : RoomDatabase(){
    abstract fun competencyDao(): CompetencyDao

    companion object {
        @Volatile
        private var INSTANCE: CompetenciesDatabase? = null

        fun getDatabase(context: Context): CompetenciesDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        CompetenciesDatabase::class.java,
                        "competencies_database"
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