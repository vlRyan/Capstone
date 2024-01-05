package com.example.capstone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.Adapters.calendarAdapter
import com.example.capstone.Adapters.dashboardEventsAdapter
import com.example.capstone.Dashboard.BarangayOfficials
import com.example.capstone.Dashboard.EmergencyContacts
import com.example.capstone.Dashboard.dashboardEvents
import com.example.capstone.List.Events
import com.example.capstone.LocalShops.FoodnBev
import com.example.capstone.LocalShops.Market
import com.example.capstone.LocalShops.Services
import com.example.capstone.Message_.UserInbox
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class dashboard : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsArray: ArrayList<Events>
    private lateinit var dashboardAdapter: dashboardEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val officials: LinearLayout = view.findViewById<LinearLayout>(R.id.officials)
        val contacts: LinearLayout = view.findViewById<LinearLayout>(R.id.contacts)
        val message: LinearLayout = view.findViewById<LinearLayout>(R.id.message)
        val policies: LinearLayout = view.findViewById<LinearLayout>(R.id.policies)

        val FnB: CardView = view.findViewById(R.id.food)
        val services: CardView = view.findViewById(R.id.services)
        val stores: CardView = view.findViewById(R.id.store)

        FnB.setOnClickListener {
            val intent = Intent(context, FoodnBev::class.java)
            startActivity(intent)
        }

        services.setOnClickListener {
            val intent = Intent(context, Services::class.java)
            startActivity(intent)
        }

        stores.setOnClickListener {
            val intent = Intent(context, Market::class.java)
            startActivity(intent)
        }

        officials.setOnClickListener{
            val intent = Intent(context, BarangayOfficials::class.java)
            startActivity(intent)
        }

        contacts.setOnClickListener {
            val intent1 = Intent(context, EmergencyContacts::class.java)
            startActivity(intent1)
        }

        message.setOnClickListener{
            val intent = Intent(context, UserInbox::class.java)
            startActivity(intent)
        }

        recyclerView = view.findViewById(R.id.currentEvents)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        eventsArray = arrayListOf()
        dashboardAdapter = dashboardEventsAdapter(eventsArray)
        recyclerView.adapter = dashboardAdapter

        EventChangeListener()

        return view
    }

    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        db.collection("EventsAnnouncement").orderBy("eventDate", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onEvent(
                    value: QuerySnapshot?,
                    error: FirebaseFirestoreException?
                ) {
                    if (error != null){

                        Log.e("Firestore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!){
                        if (dc.type == DocumentChange.Type.ADDED){
                            eventsArray.add(dc.document.toObject(Events::class.java))

                        }
                        val adapter = dashboardEventsAdapter(eventsArray)
                        recyclerView.adapter = adapter
                        adapter.onItemClickListener(object : dashboardEventsAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {
                                val intent = Intent(context, dashboardEvents::class.java)
                                intent.putExtra("title", eventsArray[position].eventTitle)
                                intent.putExtra("date", eventsArray[position].eventDate)
                                intent.putExtra("place", eventsArray[position].eventPlace)
                                intent.putExtra("time", eventsArray[position].eventTime)
                                startActivity(intent)
                            }
                        })
                    }

                    dashboardAdapter.notifyDataSetChanged()
                }
            })
    }
}