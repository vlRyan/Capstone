package com.example.capstone.message

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.Adapters.UserAdapter
import com.example.capstone.List.User
import com.example.capstone.Message.UserSendMessage
import com.example.capstone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Inbox : Fragment() {

    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var userList : ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_inbox, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        userList = arrayListOf()
        adapter = UserAdapter(userList)
        messagesRecyclerView = view.findViewById(R.id.messages)
        messagesRecyclerView.layoutManager = LinearLayoutManager(context)
        messagesRecyclerView.adapter = adapter


        database.child("/users").addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)
//                    Log.d("Test", postSnapshot.toString())

                    if (auth.currentUser?.uid == currentUser?.uid) {
                        userList.add(currentUser!!)
                    }
                    val adapter = UserAdapter(userList)
                    messagesRecyclerView.adapter = adapter
                    adapter.onItemClickListener(object : UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(context, UserSendMessage::class.java)
                            intent.putExtra("name", currentUser?.name)
                            intent.putExtra("uid", currentUser?.uid)
                            startActivity(intent)
                        }

                    })
                }
                adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {

            }

        })

        return view
    }
}