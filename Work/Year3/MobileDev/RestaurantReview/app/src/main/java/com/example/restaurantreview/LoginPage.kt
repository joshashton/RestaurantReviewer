package com.example.restaurantreview

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.restaurantreview.databinding.ActivityLoginPageBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val emailText = findViewById<EditText>(R.id.email)
        val passText = findViewById<EditText>(R.id.pass)
        val loginButton = findViewById<MaterialButton>(R.id.login)

        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(
                emailText.text.toString().trim{it<=' '},
                passText.text.toString().trim{it<=' '}
            )
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(baseContext, R.string.login_success,
                            Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("id", user?.email)

                        startActivity(intent)
                    }else {
                        Toast.makeText(baseContext,  R.string.login_failed,
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // link to create account option
        val signupButton = findViewById<MaterialButton>(R.id.signup)
        signupButton.setOnClickListener {
            startActivity(Intent(this@LoginPage, SignUp::class.java))
        }


    }
}