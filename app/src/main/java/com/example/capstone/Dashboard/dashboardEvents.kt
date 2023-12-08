package com.example.capstone.Dashboard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.capstone.R

class dashboardEvents : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_events)

        val title: TextView = findViewById(R.id.what)
        val date: TextView = findViewById(R.id.whenday)
        val desc: TextView = findViewById(R.id.where)
        val time: TextView = findViewById(R.id.whentime)
        val back: Button = findViewById(R.id.back)

        val bundle : Bundle?= intent.extras
        val titl = bundle?.getString("title")
        val dat = bundle?.getString("date")
        val descr = bundle?.getString(("place"))
        val tim = bundle?.getString(("time"))


        title.text = titl
        date.text = dat
        desc.text = descr
        time.text = tim

        back.setOnClickListener { finish() }



    }
}