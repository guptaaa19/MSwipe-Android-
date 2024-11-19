package com.example.mswipe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class AadhaarDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aadhaar_details)

        // Setting padding for the main view to handle insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Receive data from Intent
        val name = intent.getStringExtra("name")
        val aadhaarNumber = intent.getStringExtra("aadhaarNumber")
        val dob = intent.getStringExtra("dob")
        val gender = intent.getStringExtra("gender")
        val address = intent.getStringExtra("address")

        // Update TextViews with the received data
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val aadhaarNumberTextView = findViewById<TextView>(R.id.aadhaarNumberTextView)
        val dobTextView = findViewById<TextView>(R.id.dobTextView)
        val genderTextView = findViewById<TextView>(R.id.genderTextView)
        val addressTextView = findViewById<TextView>(R.id.addressTextView)

        nameTextView.text = name
        aadhaarNumberTextView.text = aadhaarNumber
        dobTextView.text = dob
        genderTextView.text = gender
        addressTextView.text = address

        // Show the details for a delay, then navigate to SymptomsActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Intent to navigate to SymptomsActivity
            val symptomsIntent = Intent(this, SymptomsActivity::class.java)

            // Optional: You can pass data if needed
            symptomsIntent.putExtra("name", name)
            symptomsIntent.putExtra("aadhaarNumber", aadhaarNumber)
            symptomsIntent.putExtra("dob", dob)
            symptomsIntent.putExtra("gender", gender)
            symptomsIntent.putExtra("address", address)

            // Start SymptomsActivity
            startActivity(symptomsIntent)

            // Optional: Finish AadhaarDetailsActivity to prevent going back
            finish()
        }, 2000) // Delay in milliseconds (optional)
    }
}
