package com.example.beautyshopapp.adapter

import android.content.Intent
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.RecyclerView
import com.example.beautyshopapp.R
import com.example.beautyshopapp.activity.ListItemsActivity
import com.example.beautyshopapp.databinding.ViewholderCategoryBinding
import com.example.beautyshopapp.domain.CategoryModel

import android.os.Handler

class CategoryAdapter(val items: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.binding.titleTxt.text = item.title

        if (selectedPosition == position) {

            holder.binding.titleTxt.setBackgroundResource(R.drawable.purple_bg)

            holder.binding.titleTxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )

        } else {

            holder.binding.titleTxt.setBackgroundResource(R.drawable.white_bg)

            holder.binding.titleTxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }

        holder.binding.root.setOnClickListener {
            val position = position
            if (position != RecyclerView.NO_POSITION) {
                lastSelectedPosition = selectedPosition
                selectedPosition = position
            }
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)


            val intent = Intent(
                holder.itemView.context,
                ListItemsActivity::class.java).apply{
                putExtra("title", item.title)
                putExtra("id", item.id.toString())

            }
            ContextCompat.startActivity(
                holder.itemView.context,
                intent,
                null
            )

        }


    }

    override fun getItemCount(): Int = items.size
}