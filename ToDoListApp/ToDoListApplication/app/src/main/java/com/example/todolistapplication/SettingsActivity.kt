package com.example.todolistapplication

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize Vibration Setting
        val vibrationCheckbox = findViewById<CheckBox>(R.id.vibration_checkbox)
        val vibrationStatus = findViewById<TextView>(R.id.vibration_status)

        vibrationCheckbox.isChecked = true // Default checked state
        vibrationStatus.text = "Enabled"

        vibrationCheckbox.setOnCheckedChangeListener { _, isChecked ->
            vibrationStatus.text = if (isChecked) "Enabled" else "Disabled"
        }

        // Initialize Voice Setting
        val voiceCheckbox = findViewById<CheckBox>(R.id.voice_checkbox)
        val voiceStatus = findViewById<TextView>(R.id.Voice_status)

        voiceCheckbox.isChecked = true // Default checked state
        voiceStatus.text = "Enabled"

        voiceCheckbox.setOnCheckedChangeListener { _, isChecked ->
            voiceStatus.text = if (isChecked) "Enabled" else "Disabled"
        }

        // Back arrow navigation logic
        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener {
            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close the current activity
        }
    }
}
