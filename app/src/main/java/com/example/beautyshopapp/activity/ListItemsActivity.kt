package com.example.beautyshopapp.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beautyshopapp.R
import com.example.beautyshopapp.databinding.ActivityListItemsBinding
import com.example.beautyshopapp.viewModel.MainViewModel

class ListItemsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListItemsBinding
    private val viewModel= MainViewModel()
    private var id: String=""
    private var title: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}