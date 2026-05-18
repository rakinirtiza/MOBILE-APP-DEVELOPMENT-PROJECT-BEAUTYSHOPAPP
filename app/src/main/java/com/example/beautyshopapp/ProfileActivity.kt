package com.example.beautyshopapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        val emailTxt = findViewById<TextView>(R.id.emailTxt)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        val user = auth.currentUser

        emailTxt.text = user?.email

        logoutBtn.setOnClickListener {

            auth.signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}