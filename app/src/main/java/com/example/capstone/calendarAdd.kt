package com.example.capstone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class calendarAdd : AppCompatActivity() {

    private lateinit var df: DocumentReference
    private lateinit var fStore: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_add)

        fStore = FirebaseFirestore.getInstance()

        val add: Button = findViewById(R.id.add)




        add.setOnClickListener {
            events()
            finish()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun events(){

        val c = Calendar.getInstance()

        val title: EditText = findViewById(R.id.eventTitle)
        val desc: EditText = findViewById(R.id.eventDescription)

        val eventTitle = title.text.toString()
        val description = desc.text.toString()

        val datePicker: DatePicker = findViewById(R.id.datePicker)
        val timePicker: TimePicker = findViewById(R.id.time_picker)

        val monthp = datePicker.month + 1
        val month = monthp.toString()


        val day = datePicker.dayOfMonth.toString()
        val year = datePicker.year.toString()
        val date = "$month/$day/$year"
        val hour = timePicker.hour.toString()
        val min = timePicker.minute.toString()

        val time = if  (timePicker.hour >= 12){
            "$hour:$min PM"
        }else{
            "$hour:$min AM"
        }


        df = fStore.collection("EventsAnnouncement").document()
        val events = hashMapOf(
            "eventTitle" to eventTitle,
            "eventPlace" to description,
            "eventDate" to date,
            "eventTime" to time
        )
        df.set(events)
        Toast.makeText(this, "uploaded", Toast.LENGTH_SHORT).show()


    }


}
