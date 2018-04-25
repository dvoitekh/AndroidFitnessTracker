package com.example.dvoitekh.fitnesstracker.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*


class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FitnessTrackerDB", null, 3) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("Day", true,
                "id" to SqlType.create("INTEGER PRIMARY KEY AUTOINCREMENT"),
                "date" to INTEGER,
                "steps" to INTEGER,
                "calories" to REAL,
                "distance" to REAL,
                "longitude" to REAL,
                "latitude" to REAL,
                "address" to TEXT)
        db.createTable("Settings", true,
                "id" to SqlType.create("INTEGER PRIMARY KEY AUTOINCREMENT"),
                "step_size" to REAL,
                "weight" to REAL,
                "kcal_per_kg_per_m" to REAL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        for (i in oldVersion until newVersion) {
            when (i) {
                1 -> from1to2(db)
                2 -> from2to3(db)
            }
        }
    }

    private fun from1to2(db: SQLiteDatabase) {
        db.transaction {
            db.execSQL("ALTER TABLE Day ADD COLUMN longitude REAL DEFAULT 0.0")
            db.execSQL("ALTER TABLE Day ADD COLUMN latitude REAL DEFAULT 0.0")
            db.execSQL("ALTER TABLE Day ADD COLUMN address TEXT DEFAULT ''")
        }
    }

    private fun from2to3(db: SQLiteDatabase) {
        db.transaction {
            db.createTable("Settings", true,
                    "id" to SqlType.create("INTEGER PRIMARY KEY AUTOINCREMENT"),
                    "step_size" to REAL,
                    "weight" to REAL,
                    "kcal_per_kg_per_m" to REAL)
        }
    }
}
