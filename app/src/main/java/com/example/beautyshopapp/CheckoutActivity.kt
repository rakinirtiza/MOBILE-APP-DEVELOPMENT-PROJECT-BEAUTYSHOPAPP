package com.example.beautyshopapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val nameEdt = findViewById<EditText>(R.id.nameEdt)
        val phoneEdt = findViewById<EditText>(R.id.phoneEdt)
        val addressEdt = findViewById<EditText>(R.id.addressEdt)

        val subtotalTxt = findViewById<TextView>(R.id.subtotalTxt)
        val deliveryTxt = findViewById<TextView>(R.id.deliveryTxt)
        val totalTxt = findViewById<TextView>(R.id.totalTxt)

        val confirmBtn = findViewById<Button>(R.id.confirmOrderBtn)

        val subtotal = intent.getDoubleExtra("totalPrice", 0.0)

        val delivery = 10.0
        val total = subtotal + delivery

        subtotalTxt.text = "Subtotal: $$subtotal"
        deliveryTxt.text = "Delivery: $$delivery"
        totalTxt.text = "Total: $$total"

        confirmBtn.setOnClickListener {

            val name = nameEdt.text.toString()
            val phone = phoneEdt.text.toString()
            val address = addressEdt.text.toString()

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val orderId = System.currentTimeMillis().toString()

                val orderMap = HashMap<String, Any>()

                orderMap["name"] = name
                orderMap["phone"] = phone
                orderMap["address"] = address
                orderMap["subtotal"] = subtotal
                orderMap["delivery"] = delivery
                orderMap["totalPrice"] = total
                orderMap["status"] = "Confirmed"

                FirebaseDatabase.getInstance()
                    .getReference("Orders")
                    .child(orderId)
                    .setValue(orderMap)

                    .addOnSuccessListener {

                        Toast.makeText(
                            this,
                            "Order Confirmed",
                            Toast.LENGTH_SHORT
                        ).show()

                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )

                        finish()
                    }
            }
        }
    }
}