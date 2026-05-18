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

        val quantityEdt =
            findViewById<EditText>(R.id.quantityEdt)

        val updateQuantityBtn =
            findViewById<Button>(R.id.updateQuantityBtn)

        val addProductIdEdt =
            findViewById<EditText>(R.id.addProductIdEdt)

        val addProductTitleEdt =
            findViewById<EditText>(R.id.addProductTitleEdt)

        val addProductPriceEdt =
            findViewById<EditText>(R.id.addProductPriceEdt)

        val addProductBtn =
            findViewById<Button>(R.id.addProductBtn)

        val deleteProductBtn =
            findViewById<Button>(R.id.deleteProductBtn)


        updateQuantityBtn.setOnClickListener {

            val productId =
                productIdEdt.text.toString().trim()

            val quantity =
                quantityEdt.text.toString().trim()

            FirebaseDatabase.getInstance()
                .getReference("Items")
                .child(productId)
                .child("quantity")
                .setValue(quantity.toInt())

                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Quantity Updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }


        addProductBtn.setOnClickListener {

            val id =
                addProductIdEdt.text.toString()

            val title =
                addProductTitleEdt.text.toString()

            val price =
                addProductPriceEdt.text.toString()

            val map = HashMap<String, Any>()

            map["title"] = title
            map["price"] = price.toInt()

            FirebaseDatabase.getInstance()
                .getReference("Items")
                .child(id)
                .updateChildren(map)

                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Product Added",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }


        deleteProductBtn.setOnClickListener {

            val productId =
                productIdEdt.text.toString()

            FirebaseDatabase.getInstance()
                .getReference("Items")
                .child(productId)
                .removeValue()

                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Product Deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}