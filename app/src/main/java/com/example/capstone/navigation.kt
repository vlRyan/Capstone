package com.example.capstone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.PopupMenu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstone.Reservation.adminReservationView
import com.example.capstone.bottomMenu.ReservationList
import com.example.capstone.databinding.ActivityMainBinding
import com.example.capstone.message.Inbox
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class navigation : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)

        val user = FirebaseAuth.getInstance().currentUser
        val bottomNavigationView = binding.bottomNavigationView
        replace(dashboard())

//        val menuBurger = binding.menu
//
//        menuBurger.setOnClickListener { view ->
//            showTopMenu(view)
//        }

        if (user?.email == LoginPage.ADMIN_EMAIL) {
            // Admin user, load admin layout
            val menuInflater = MenuInflater(this)
            menuInflater.inflate(R.menu.adminmenu, bottomNavigationView.menu)
        } else {
            // Regular user, load regular layout
            bottomNavigationView.inflateMenu(R.menu.menu_tab)
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replace(dashboard())
                R.id.calendar -> replace(calendar())
                R.id.admincalendar -> replace(AdminCalendarView())
                R.id.upload -> replace(Home())
                R.id.navigation_read_report -> replace(AdminCheck())
                R.id.userappointments -> replace(ReservationList())
                R.id.appointments -> replace(adminReservationView())
                R.id.profile -> replace(Profile())
                R.id.inbox -> replace(Inbox())

                else ->{

                }
            }
            true
        }
    }

    private fun showTopMenu(view: View) {
        Log.d("Navigation", "Top menu clicked")
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.burger_nav, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
//                R.id.message -> {
//                    replace(user_send_message())
//                    true
//                }
                R.id.logout -> {
                    Firebase.auth.signOut()
                    val intent = Intent(this, LoginPage::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    private fun replace(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container,fragment)
        fragmentTransaction.commit()
    }
}