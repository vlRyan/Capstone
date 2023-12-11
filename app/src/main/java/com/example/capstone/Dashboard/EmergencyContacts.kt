package com.example.capstone.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.capstone.LoginPage
import com.example.capstone.R
import com.example.capstone.navigation

class EmergencyContacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)

        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener{
            val login = Intent(this, navigation::class.java)
            startActivity(login)
        }

    }
}