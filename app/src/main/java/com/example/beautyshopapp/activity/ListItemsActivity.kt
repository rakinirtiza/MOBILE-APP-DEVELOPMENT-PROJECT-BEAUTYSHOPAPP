package com.example.beautyshopapp.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beautyshopapp.R
import com.example.beautyshopapp.adapter.PopularAdapter
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

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        getBundle()
        initList()


    }

    private fun initList() {

        binding.apply {

            backBtn.setOnClickListener { finish() }

            progressBarList.visibility = View.VISIBLE

            viewModel.popular.observe(this@ListItemsActivity, Observer {

                viewList.layoutManager = GridLayoutManager(this@ListItemsActivity, 2)

                viewList.adapter = PopularAdapter(it)

            })

            viewModel.loadFiltered(id)

        }
    }

    private fun getBundle(){

        id=intent.getStringExtra("id").toString()
        title=intent.getStringExtra("title").toString()

        binding.categoryTxt.text = title

    }
}