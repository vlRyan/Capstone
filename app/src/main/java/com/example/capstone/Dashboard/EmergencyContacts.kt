package com.example.capstone.Dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.capstone.R

class EmergencyContacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)

        val back = findViewById<Button>(R.id.back)

        back.setOnClickListener { finish() }
    }
}