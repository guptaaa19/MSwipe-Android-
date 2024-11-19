package com.example.mswipe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mswipe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listener for the submit button
        binding.submitButton.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val aadhaarNumber = binding.aadhaarEt.text.toString()
            val dob = binding.dobEt.text.toString()
            val gender = binding.genderEt.text.toString()
            val address = binding.addressEt.text.toString()

            // Validate the Aadhaar Number (12 digits)
            if (isValidAadhaarNumber(aadhaarNumber)) {
                // Create an Intent to start AadhaarDetailsActivity
                val intent = Intent(this, AadhaarDetailsActivity::class.java)

                // Add the details as extras to the Intent
                intent.putExtra("name", name)
                intent.putExtra("aadhaarNumber", aadhaarNumber)
                intent.putExtra("dob", dob)
                intent.putExtra("gender", gender)
                intent.putExtra("address", address)

                // Start the AadhaarDetailsActivity
                startActivity(intent)
            } else {
                // Show an error message if the Aadhaar number is invalid
                Toast.makeText(this, "Please enter a valid 12-digit Aadhaar number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to validate the Aadhaar number
    private fun isValidAadhaarNumber(aadhaarNumber: String): Boolean {
        // Check if the Aadhaar number is exactly 12 digits
        return aadhaarNumber.matches(Regex("^[0-9]{12}$"))
    }
}
