package com.example.restaurantreview

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.restaurantreview.databinding.ActivitySignUpBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.UserProfileChangeRequest




class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val userText  = findViewById<EditText>(R.id.username)
        val emailText = findViewById<EditText>(R.id.email)
        val passText = findViewById<EditText>(R.id.pass)
        val signupButton = findViewById<MaterialButton>(R.id.signup)


        signupButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                emailText.text.toString().trim{it<=' '},
                passText.text.toString().trim{it<=' '}

            )
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful) {

                        val firebaseUser : FirebaseUser = task.result!!.user!!
                        Toast.makeText(baseContext, R.string.signup_success,
                            Toast.LENGTH_SHORT).show()

                        userText.text.toString()

                        val user = auth.currentUser
                        val profileUpdates =
                            UserProfileChangeRequest.Builder().setDisplayName(userText.text.toString()).build()
                        user?.updateProfile(profileUpdates)

                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("userid", firebaseUser.uid)
                        intent.putExtra("email", firebaseUser.email)
                        startActivity(intent)
                        finish()

                    }else {
                        Toast.makeText(baseContext, R.string.signup_fail,
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}