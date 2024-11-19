package com.example.mswipe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class Test3Activity : AppCompatActivity() {
    private lateinit var randomNumbersTextView: TextView
    private lateinit var userInputEditText: EditText
    private lateinit var submitButton: TextView

    private var randomNumbers: String = ""
    private var currentAttempt = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test3)

        // Views initialization
        randomNumbersTextView = findViewById(R.id.randomNumbersTextView)
        userInputEditText = findViewById(R.id.userInputEditText)
        submitButton = findViewById(R.id.submitButton)

        // Set padding for insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Generate and display random numbers
        generateRandomNumbers()

        // After 3 seconds, hide the numbers and show input field for verification
        Handler(Looper.getMainLooper()).postDelayed({
            randomNumbersTextView.visibility = TextView.INVISIBLE // Hide random numbers
            userInputEditText.visibility = EditText.VISIBLE // Show input field
            submitButton.visibility = TextView.VISIBLE // Show submit button
        }, 3000) // Delay for 3 seconds

        // Set the click listener for the submit button to verify the input
        submitButton.setOnClickListener {
            verifyUserInput()
        }
    }

    private fun generateRandomNumbers() {
        val randomNumberList = List(6) { Random.nextInt(0, 10) } // Generate 6 random numbers
        randomNumbers = randomNumberList.joinToString("") // Convert to string
        randomNumbersTextView.text = randomNumbers // Display random numbers

        // Randomize the position of the TextView on the screen
        val randomX = Random.nextInt(100, 600)  // Random X position
        val randomY = Random.nextInt(200, 1000) // Random Y position
        randomNumbersTextView.x = randomX.toFloat()
        randomNumbersTextView.y = randomY.toFloat()
    }

    private fun verifyUserInput() {
        val userInput = userInputEditText.text.toString()
        if (userInput == randomNumbers) {
            correctAnswers++
            Toast.makeText(this, "Correct! Focus Verified", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrect. Try again.", Toast.LENGTH_SHORT).show()
        }

        // Move to the next attempt
        currentAttempt++

        // If 3 attempts are completed, show the score and go back to the previous activity
        if (currentAttempt >= 3) {
            Toast.makeText(this, "Your score: $correctAnswers/3", Toast.LENGTH_LONG).show()

            // Create an intent to go back to MSTestsActivity
            val intent = Intent(this, MSTestsActivity::class.java)
            startActivity(intent)
            finish()  // Finish current activity
        } else {
            // Generate new random numbers for the next attempt
            generateRandomNumbers()

            // Reset visibility
            randomNumbersTextView.visibility = TextView.VISIBLE
            userInputEditText.visibility = EditText.GONE
            submitButton.visibility = TextView.GONE

            // Delay before the next round (optional, to show numbers again before hiding)
            Handler(Looper.getMainLooper()).postDelayed({
                randomNumbersTextView.visibility = TextView.INVISIBLE
                userInputEditText.visibility = EditText.VISIBLE
                submitButton.visibility = TextView.VISIBLE
            }, 3000)
        }
    }
}
