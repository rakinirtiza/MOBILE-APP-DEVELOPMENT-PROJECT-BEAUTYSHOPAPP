package com.example.beautyshopapp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beautyshopapp.R
import com.google.firebase.database.FirebaseDatabase

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin)

        val productIdEdt =
            findViewById<EditText>(R.id.productIdEdt)

        val newPriceEdt =
            findViewById<EditText>(R.id.newPriceEdt)

        val updatePriceBtn =
            findViewById<Button>(R.id.updatePriceBtn)

        updatePriceBtn.setOnClickListener {

            val productId =
                productIdEdt.text.toString().trim()

            val newPrice =
                newPriceEdt.text.toString().trim()

            if (productId.isEmpty() || newPrice.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            FirebaseDatabase.getInstance()
                .getReference("Items")
                .child(productId)
                .child("price")
                .setValue(newPrice.toInt())

                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Price Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Update Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}