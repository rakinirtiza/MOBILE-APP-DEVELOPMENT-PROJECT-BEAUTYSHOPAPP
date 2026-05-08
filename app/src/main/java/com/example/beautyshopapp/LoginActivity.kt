package com.example.beautyshopapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailEt = findViewById<EditText>(R.id.emailEt)
        val passwordEt = findViewById<EditText>(R.id.passwordEt)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerTv = findViewById<TextView>(R.id.registerTv)
        val forgotTv = findViewById<TextView>(R.id.forgotTv)

        loginBtn.setOnClickListener {

            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
            } else {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                            if (auth.currentUser!!.isEmailVerified) {

                                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this, MainActivity::class.java))
                                finish()

                            } else {

                                Toast.makeText(this, "Verify your email first", Toast.LENGTH_LONG).show()

                            }

                        } else {

                            Toast.makeText(this, it.exception!!.message, Toast.LENGTH_LONG).show()

                        }

                    }

            }

        }

        registerTv.setOnClickListener {

            startActivity(Intent(this, RegisterActivity::class.java))

        }

        forgotTv.setOnClickListener {

            startActivity(Intent(this, ForgotPasswordActivity::class.java))

        }

    }
}