package com.example.restaurantreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView

class ReviewPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_page)

        var intent : Intent = intent
        val restID = intent.getStringExtra("restID")
        val restName = intent.getStringExtra("restName")

        var textView1 = findViewById<TextView>(R.id.textView1)
        var textView6 = findViewById<TextView>(R.id.textView6)
        var signup = findViewById<Button>(R.id.signup)

        signup.setOnClickListener {

            Toast.makeText(baseContext, R.string.success,
                Toast.LENGTH_SHORT).show()


            finish()
            startActivity(Intent(this@ReviewPage, MainActivity::class.java))
        }

        //textView6.text = restID
        textView1.text = restName


    }



}