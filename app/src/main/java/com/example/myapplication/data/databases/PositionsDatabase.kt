package com.example.myapplication.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.daos.PositionDao
import com.example.myapplication.entities.Position

@Database(entities = [Position::class], version = 2)
abstract class PositionsDatabase : RoomDatabase() {
    abstract fun positionDao(): PositionDao

    companion object {
        @Volatile
        private var INSTANCE: PositionsDatabase? = null

        fun getDatabase(context: Context): PositionsDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        PositionsDatabase::class.java,
                        "positions_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}