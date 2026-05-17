package com.example.beautyshopapp.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.beautyshopapp.Helper.ManagmentCart
import com.example.beautyshopapp.R
import com.example.beautyshopapp.adapter.PicAdapter
import com.example.beautyshopapp.databinding.ActivityDetailBinding
import com.example.beautyshopapp.domain.ItemsModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        managmentCart = ManagmentCart(this)

        getBundgle()
        initList()
    }

    private fun initList() {

        val picList = ArrayList<String>()

        for (imageUrl in item.picUrl) {

            picList.add(imageUrl)
        }

        Glide.with(this)
            .load(picList[0])
            .into(binding.pic)

        binding.picList.adapter = PicAdapter(items = picList) {

            Glide.with(this)
                .load(it)
                .into(binding.pic)
        }
    }

    private fun getBundgle() {

        item = (intent.getSerializableExtra("object") as ItemsModel?)!!

        binding.apply {

            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = "$" + item.price

            addToCartBtn.setOnClickListener {

                item.numberInCart = Integer.valueOf(
                    numberItemTxt.text.toString()
                )

                managmentCart.insertItem(item)
            }

            backBtn.setOnClickListener { finish() }

            plusBtn.setOnClickListener {

                numberItemTxt.text =
                    (item.numberInCart + 1).toString()

                item.numberInCart++
            }

            minusBtn.setOnClickListener {

                if (item.numberInCart > 0) {

                    numberItemTxt.text =
                        (item.numberInCart - 1).toString()

                    item.numberInCart--
                }
            }
        }
    }
}