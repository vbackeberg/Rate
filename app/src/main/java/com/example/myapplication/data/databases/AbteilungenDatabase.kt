package com.example.myapplication.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.daos.AbteilungDao
import com.example.myapplication.entities.Abteilung

@Database(entities = [Abteilung::class], version = 2)
abstract class AbteilungenDatabase : RoomDatabase() {
    abstract fun abteilungDao(): AbteilungDao

    companion object {
        @Volatile
        private var INSTANCE: AbteilungenDatabase? = null

        fun getDatabase(context: Context): AbteilungenDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AbteilungenDatabase::class.java,
                        "abteilungen_database"
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