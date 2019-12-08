package com.example.myapplication.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.daos.CompetencyAreaDao
import com.example.myapplication.entities.CompetencyArea

@Database(entities = [CompetencyArea::class], version = 2)
abstract class CompetencyAreasDatabase : RoomDatabase() {
    abstract fun competencyAreaDao(): CompetencyAreaDao

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
