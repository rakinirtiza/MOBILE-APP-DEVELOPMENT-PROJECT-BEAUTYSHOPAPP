package com.example.beautyshopapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val nameEt = findViewById<EditText>(R.id.nameEt)
        val emailEt = findViewById<EditText>(R.id.emailEt)
        val passwordEt = findViewById<EditText>(R.id.passwordEt)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val loginTv = findViewById<TextView>(R.id.loginTv)

        registerBtn.setOnClickListener {

            val name = nameEt.text.toString().trim()
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {

                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()

            } else {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                            auth.currentUser!!.sendEmailVerification()

                            Toast.makeText(
                                this,
                                "Registration Successful. Verify your email.",
                                Toast.LENGTH_LONG
                            ).show()

                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()

                        } else {

                            Toast.makeText(
                                this,
                                it.exception!!.message,
                                Toast.LENGTH_LONG
                            ).show()

                        }

                    }

            }

        }

        loginTv.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))

        }

    }
}