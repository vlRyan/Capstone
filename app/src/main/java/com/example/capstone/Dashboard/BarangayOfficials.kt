package com.example.capstone.Dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.capstone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class BarangayOfficials : AppCompatActivity() {
    private var mediaUri: Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barangay_officials)

        auth = Firebase.auth
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        val currentUser = auth.currentUser
        val uploadButton: ImageButton = findViewById(R.id.upload_punong_barangay_prof)

        if (currentUser != null && "admin@email.com" == currentUser.email) {
            uploadButton.visibility = ImageButton.VISIBLE
        } else {
            uploadButton.visibility = ImageButton.GONE
        }

        uploadButton.setOnClickListener {
            val mediaIntent = Intent(Intent.ACTION_PICK)
            mediaIntent.type = "image/*"
            imagePickerActivityResult.launch(mediaIntent)
        }

        loadProfilePicture(currentUser?.uid)
    }

    private val imagePickerActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the selected media
                mediaUri = result.data?.data
                // Update the UI or do something with the selected media
                val profileImageView: ImageView = findViewById(R.id.punong_barangay_profile)
                profileImageView.setImageURI(mediaUri)

                // Save the profile picture to Firebase Storage
                saveProfilePicture(auth.currentUser?.uid)
            }
        }

    private fun saveProfilePicture(userId: String?) {
        if (userId != null && mediaUri != null) {
            // Use a fixed filename for simplicity
            val filename = "profile_picture.jpg"
            val imageRef = storageRef.child("$userId/$filename")

            // Upload the image to Firebase Storage
            imageRef.putFile(mediaUri!!)
                .addOnSuccessListener {
                    // Get the download URL of the uploaded image
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Update the user's profile in Firebase Authentication with the new image URL
                        val user = auth.currentUser
                        val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                            .setPhotoUri(uri)
                            .build()

                        user?.updateProfile(profileUpdates)
                    }
                }
        }
    }

    private fun loadProfilePicture(userId: String?) {
        if (userId != null) {
            // Load the user's profile picture from Firebase Storage
            val imageRef = storageRef.child("$userId/profile_picture.jpg")

            // Get the download URL of the image
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                // Update the UI with the retrieved image URL
                Glide.with(this)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(findViewById(R.id.punong_barangay_profile))
            }.addOnFailureListener {
                // Handle failure, for example, log an error
                // You can add a placeholder image or handle the failure in another way
                Glide.with(this)
                    .load(R.drawable.profile_icon) // Add a placeholder image resource
                    .into(findViewById(R.id.punong_barangay_profile))
            }
        }
    }

}
