package com.example.beautyshopapp

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.beautyshopapp.adapter.CategoryAdapter
import com.example.beautyshopapp.databinding.ActivityMainBinding
import com.example.beautyshopapp.MainviewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth

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

        // RecyclerView + Firebase Database
        initCategory()
    }

    private fun initCategory() {

        viewModel.loadCategory()

        viewModel.category.observe(this, Observer {

            binding.rvCategory.adapter = CategoryAdapter(it)
        })
    }
}