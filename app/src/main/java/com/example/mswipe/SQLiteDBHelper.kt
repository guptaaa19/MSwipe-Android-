package com.example.mswipe

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "UserData.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "user_details"
        const val COLUMN_NAME = "name"
        const val COLUMN_AADHAAR = "aadhaarNumber"
        const val COLUMN_DOB = "dob"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_ADDRESS = "address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_AADHAAR TEXT,
                $COLUMN_DOB TEXT,
                $COLUMN_GENDER TEXT,
                $COLUMN_ADDRESS TEXT
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUserDetails(name: String, aadhaarNumber: String, dob: String, gender: String, address: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_AADHAAR, aadhaarNumber)
            put(COLUMN_DOB, dob)
            put(COLUMN_GENDER, gender)
            put(COLUMN_ADDRESS, address)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getUserDetails(): Map<String, String> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "id DESC", "1")
        val userDetails = mutableMapOf<String, String>()
        if (cursor.moveToFirst()) {
            userDetails[COLUMN_NAME] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            userDetails[COLUMN_AADHAAR] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AADHAAR))
            userDetails[COLUMN_DOB] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB))
            userDetails[COLUMN_GENDER] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))
            userDetails[COLUMN_ADDRESS] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
        }
        cursor.close()
        return userDetails
    }
}
