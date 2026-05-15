package com.example.beautyshopapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyshopapp.domain.CategoryModel

class CategoryAdapter(val items: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    class Viewholder(val binding: ) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.Viewholder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CategoryAdapter.Viewholder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}