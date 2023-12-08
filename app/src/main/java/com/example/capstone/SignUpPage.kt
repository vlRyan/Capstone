package com.example.capstone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var df: DocumentReference
    private lateinit var fStore: FirebaseFirestore
    private var valid: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        auth = Firebase.auth
        fStore = FirebaseFirestore.getInstance()

        val back: ImageView = findViewById(R.id.backbttn)
        val create: Button = findViewById(R.id.submit)

        back.setOnClickListener{

            val login = Intent(this, LoginPage::class.java)
            startActivity(login)

        }
        // User SignUp function
        create.setOnClickListener {
            userSignUp()
        }
    }

    private fun userSignUp() {

        val Uname: EditText = findViewById(R.id.username)
        val email: EditText = findViewById(R.id.email)
        val phone_num: EditText = findViewById(R.id.cellnum)
        val pass: EditText = findViewById(R.id.password)
        val confirmPass: EditText = findViewById(R.id.confirm_pass)


        val Email = email.text.toString()
        val Password = pass.text.toString()
        val Confirm = confirmPass.text.toString()
        val name = Uname.text.toString()
        val pn = phone_num.text.toString()

        if (email.text.isEmpty() or pass.text.isEmpty() or Uname.text.isEmpty() or phone_num.text.isEmpty()){

            Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show()
            checkFields(Uname)
            checkFields(email)
            checkFields(phone_num)
            checkFields(pass)
            checkFields(confirmPass)
            return
        }

        else if(Password == Confirm) {
            auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, To navigation
                        val main = Intent(this, navigation::class.java)
                        startActivity(main)
                        // _______________________________STORING USER INFO TO FIREBASEFIRESTORE______________________________________
                        val user = auth.currentUser
                        if (user != null) {
                            df = fStore.collection("Users").document(user.uid)
                            val userInf = hashMapOf(
                                "Name" to name,
                                "Email" to Email,
                                "Phone Number" to pn,
                                "Password" to Password,

                                // code line will specify the admin and user
                                "User" to "1"
                            )
                            df.set(userInf)
                        }
                        // _______________________________changed lines______________________________________

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()


                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.", Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "An Error Occured ${it.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
        }

        else{
            Toast.makeText(this, "Password and Confirm-Password Does not match", Toast.LENGTH_SHORT).show()

        }

    }
    private fun checkFields(text: EditText){
        valid = if(text.text.isEmpty()){
            text.setError("Empty Field")
            Toast.makeText(this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show()
            false
        }else{
            true

        }

    }


}