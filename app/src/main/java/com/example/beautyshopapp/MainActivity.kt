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

import android.widget.ImageView

import androidx.appcompat.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.beautyshopapp.FavouriteActivity
import com.example.beautyshopapp.activity.AdminActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var popularAdapter: PopularAdapter

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        auth = FirebaseAuth.getInstance()


        if (auth.currentUser == null) {

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )



        val settingsBtn = findViewById<ImageView>(R.id.settingsBtn)

        settingsBtn.setOnClickListener {

            AlertDialog.Builder(this)
                .setIcon(R.drawable.logout)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")

                .setPositiveButton("Logout") { _, _ ->

                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this, LoginActivity::class.java)

                    startActivity(intent)

                    finish()
                }

                .setNegativeButton("Cancel", null)

                .show()
        }




        initCategory()
        initBanner()
        initPopular()
        initBottomMenu()

        binding.adminBtn.setOnClickListener {

            val currentUser = FirebaseAuth
                .getInstance()
                .currentUser

            if (currentUser?.email == "abonti72542@gmail.com") {

                startActivity(
                    Intent(this, AdminActivity::class.java)
                )

            } else {

                Toast.makeText(
                    this,
                    "Access Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.bellBtn.setOnClickListener {

            Toast.makeText(
                this,
                "No notifications yet",
                Toast.LENGTH_SHORT
            ).show()
        }


        binding.searchEdt.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                filterProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun initBottomMenu() {

        binding.bottomBtn4.setOnClickListener {

            startActivity(
                Intent(this@MainActivity, ProfileActivity::class.java)
            )
        }
        binding.bottomBtn3.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
        binding.bottomBtn2.setOnClickListener {

            startActivity(
                Intent(
                    this@MainActivity,
                    FavouriteActivity::class.java
                )
            )
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
                popularAdapter = PopularAdapter(it)

                viewPopular.adapter = popularAdapter

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

        private fun filterProducts(query: String) {

            val filteredList = viewModel.popular.value?.filter {

                it.title.lowercase().contains(query.lowercase())
            }

            if (filteredList != null) {

                val adapter = PopularAdapter(ArrayList(filteredList))

                binding.viewPopular.adapter = adapter
            }
        }

    }



