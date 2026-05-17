package com.example.beautyshopapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beautyshopapp.databinding.ViewholderPicBinding

class PicAdapter(
    val items: List<String>,
    private val onImageSelected: (String) -> Unit
) : RecyclerView.Adapter<PicAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    class Viewholder(val binding: ViewholderPicBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PicAdapter.Viewholder {

        val binding =
            ViewholderPicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return Viewholder(binding)
    }

    override fun onBindViewHolder(
        holder: PicAdapter.Viewholder,
        position: Int
    ) {

        val item = items[position]

        holder.binding.pic.loadImage(item)

        holder.binding.root.setOnClickListener {

            val position = position

            if (position != RecyclerView.NO_POSITION) {

                lastSelectedPosition = selectedPosition
                selectedPosition = position

                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)

                onImageSelected(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size
    fun ImageView.loadImage(url: String) {

        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}