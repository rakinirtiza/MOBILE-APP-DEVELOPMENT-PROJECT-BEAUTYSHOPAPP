package com.example.beautyshopapp.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beautyshopapp.Helper.ChangeNumberItemsListener
import com.example.beautyshopapp.Helper.ManagmentCart
import com.example.beautyshopapp.R
import com.example.beautyshopapp.adapter.CartAdapter
import com.example.beautyshopapp.databinding.ActivityCartBinding
import android.content.Intent
import com.example.beautyshopapp.CheckoutActivity
class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setVariable()
        calculatorCart()
        initCartList()
    }

    private fun initCartList() {

        binding.apply {

            viewCart.layoutManager =
                LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)

            viewCart.adapter =
                CartAdapter(
                     managmentCart.getListCart(),
                    this@CartActivity,
                    object : ChangeNumberItemsListener {

                        override fun onChanged() {
                            calculatorCart()

                        }
                    })

        }
    }
    private fun setVariable() {

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.checkoutBtn.setOnClickListener {

            val total =
                managmentCart.getTotalFee() + tax + 10.0

            val intent =
                Intent(this, CheckoutActivity::class.java)

            intent.putExtra(
                "totalPrice",
                total
            )

            startActivity(intent)
        }
    }

    private fun calculatorCart() {

        val percentTax = 0.03
        val delivery = 10.0

        tax = Math.round((managmentCart.getTotalFee() * percentTax) * 100) / 100.0

        val total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100.0

        val itemTotal = Math.round((managmentCart.getTotalFee()) * 100) / 100.0

        with(binding) {
            totalFeeTxt.text = "$$itemTotal"
            taxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }
}