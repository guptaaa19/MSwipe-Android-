package com.example.mswipe

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mswipe.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: SQLiteDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = SQLiteDBHelper(this)

        // Set up gender dropdown
        val genders = resources.getStringArray(R.array.Gender)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, genders)
        binding.genderDropdown.setAdapter(arrayAdapter)

        binding.genderDropdown.setOnClickListener {
            binding.genderDropdown.showDropDown()
        }

        // Optional: Log the selected gender
        binding.genderDropdown.setOnItemClickListener { parent, _, position, _ ->
            val selectedGender = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Selected: $selectedGender", Toast.LENGTH_SHORT).show()
        }


        // Set up Date Picker for Date of Birth
        binding.dobEt.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.dobEt.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        // Set up click listener for the submit button
        binding.submitButton.setOnClickListener {
            val name = binding.nameEt.text.toString()
            val aadhaarNumber = binding.aadhaarEt.text.toString()
            val dob = binding.dobEt.text.toString()
            val gender = binding.genderDropdown.text.toString() // Updated to use genderDropdown
            val address = binding.addressEt.text.toString()

            // Validate the Aadhaar Number (12 digits)
            if (isValidAadhaarNumber(aadhaarNumber)) {
                // Save data to the database
                val id = dbHelper.insertUserDetails(name, aadhaarNumber, dob, gender, address)
                if (id > 0) {
                    // Navigate to AadhaarDetailsActivity
                    val intent = Intent(this, AadhaarDetailsActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Show an error message if the Aadhaar number is invalid
                Toast.makeText(this, "Please enter a valid 12-digit Aadhaar number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to validate the Aadhaar number
    private fun isValidAadhaarNumber(aadhaarNumber: String): Boolean {
        return aadhaarNumber.matches(Regex("^[0-9]{12}$"))
    }
}
