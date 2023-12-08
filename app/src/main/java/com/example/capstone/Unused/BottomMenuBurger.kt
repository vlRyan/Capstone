package com.example.capstone.Unused

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.capstone.LoginPage
import com.example.capstone.R
import com.example.capstone.bottomMenu.MessageActivity
import com.example.capstone.bottomMenu.Reservation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class bottomMenuBurger : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bottom_menu_burger, container, false)

//        val shop = view.findViewById<CardView>(R.id.localShop)
//        val reserve = view.findViewById<CardView>(R.id.reservation)
//        val message = view.findViewById<CardView>(R.id.message)
//        val logout = view.findViewById<CardView>(R.id.logout)
//
//        message.setOnClickListener {
//            val intent = Intent(activity, MessageActivity::class.java)
//            startActivity(intent)
//        }
//
//        reserve.setOnClickListener {
//            val intent2 = Intent(activity, Reservation::class.java)
//            startActivity(intent2)
//        }
//
//        shop.setOnClickListener {
//            val intent3 = Intent(activity, LocalShop::class.java)
//            startActivity(intent3)
//        }
//
//        logout.setOnClickListener{
//            Firebase.auth.signOut()
//            val intent4 = Intent(activity, LoginPage::class.java)
//            startActivity(intent4)
//            activity?.finish()
//        }
//
//
        return view
    }


}