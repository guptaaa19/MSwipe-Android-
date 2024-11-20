package com.example.mswipe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AadhaarDetailsActivity : AppCompatActivity() {

    private lateinit var dbHelper: SQLiteDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aadhaar_details)

        dbHelper = SQLiteDBHelper(this)

        // Setting padding for the main view to handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Fetch data from database
        val userDetails = dbHelper.getUserDetails()
        findViewById<TextView>(R.id.nameTextView).text = userDetails[SQLiteDBHelper.COLUMN_NAME]
        findViewById<TextView>(R.id.aadhaarNumberTextView).text = userDetails[SQLiteDBHelper.COLUMN_AADHAAR]
        findViewById<TextView>(R.id.dobTextView).text = userDetails[SQLiteDBHelper.COLUMN_DOB]
        findViewById<TextView>(R.id.genderTextView).text = userDetails[SQLiteDBHelper.COLUMN_GENDER]
        findViewById<TextView>(R.id.addressTextView).text = userDetails[SQLiteDBHelper.COLUMN_ADDRESS]

        // Delay and navigate to MSTestsActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MSTestsActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000) // 5 seconds delay
    }
}
