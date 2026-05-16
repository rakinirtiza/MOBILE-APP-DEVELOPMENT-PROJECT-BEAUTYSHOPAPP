package com.example.beautyshopapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beautyshopapp.databinding.ViewholderPopularBinding
import com.example.beautyshopapp.domain.ItemsModel

class PopularAdapter(val items: List<ItemsModel>) :
    RecyclerView.Adapter<PopularAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderPopularBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.Viewholder {

        val binding = ViewholderPopularBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return Viewholder(binding)
    }

    override fun onBindViewHolder(
        holder: PopularAdapter.Viewholder,
        position: Int
    ) {

        val item = items[position]

        with(holder.binding) {

            titleTxt.text = item.title
            priceTxt.text = "$${item.price}"
            ratingTxt.text = item.rating.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .into(pic)
        }
    }

    override fun getItemCount(): Int = items.size
}