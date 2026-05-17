package com.example.beautyshopapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.beautyshopapp.activity.CartActivity
import com.example.beautyshopapp.adapter.CategoryAdapter
import com.example.beautyshopapp.databinding.ActivityMainBinding
import com.example.beautyshopapp.viewModel.MainViewModel
import com.example.beautyshopapp.adapter.PopularAdapter
import com.example.beautyshopapp.adapter.SliderAdapter
import com.example.beautyshopapp.domain.SliderModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Firebase Auth
        auth = FirebaseAuth.getInstance()


        if (auth.currentUser == null) {

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        initCategory()
        initBanner()
        initPopular()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.bottomBtn3.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }

    private fun initPopular() {
        binding.apply {

            progressBarBanner.visibility = View.VISIBLE

            viewModel.popular.observe(this@MainActivity, Observer {

                viewPopular.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                viewPopular.adapter = PopularAdapter(it)

                progressBarPopular.visibility = View.GONE
            })
        }

        viewModel.loadPopular()
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE

        viewModel.banner.observe(this, Observer {

            binding.progressBarBanner.visibility = View.GONE

            banners(image = it)
        })

        viewModel.loadBanners()
    }


    private fun initCategory() {

        binding.progressBarCategory.visibility = View.VISIBLE

        viewModel.category.observe(this, Observer {

            binding.progressBarCategory.visibility = View.GONE

            binding.rvCategory.adapter = CategoryAdapter(it)

            binding.rvCategory.layoutManager =
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        })

        viewModel.loadCategory()
    }

    private fun banners(image: List<SliderModel>) {
        binding.apply {
            viewPager2.adapter = SliderAdapter(
                sliderItems = image,
                viewPager = viewPager2
            )

            viewPager2.clipToPadding = false
            viewPager2.clipChildren = false
            viewPager2.offscreenPageLimit = 2

            viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer().apply {

                addTransformer(
                    MarginPageTransformer(40)
                )
            }

            viewPager2.setPageTransformer(compositePageTransformer)

            if (image.size > 1) {

                dotIndicator.visibility = View.VISIBLE

                dotIndicator.attachTo(viewPager2)
            }
        }
    }

}
