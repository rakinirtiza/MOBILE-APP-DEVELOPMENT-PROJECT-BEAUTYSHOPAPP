package com.example.beautyshopapp

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FavouriteActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        val favouriteTxt = findViewById<TextView>(R.id.favouriteTxt)

        sharedPreferences =
            getSharedPreferences("FAVOURITE", MODE_PRIVATE)

        val allItems = sharedPreferences.all

        var result = ""

        for ((key, value) in allItems) {

            result += "$value\n\n"
        }

        if (result.isEmpty()) {

            favouriteTxt.text = "No favourite items yet"

        } else {

            favouriteTxt.text = result
        }
    }
}