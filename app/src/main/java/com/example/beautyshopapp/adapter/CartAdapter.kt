package com.example.beautyshopapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beautyshopapp.Helper.ChangeNumberItemsListener

import com.example.beautyshopapp.Helper.ManagmentCart
import com.example.beautyshopapp.databinding.ViewholderCartBinding
import com.example.beautyshopapp.domain.ItemsModel

class CartAdapter(
    private val listItemSelected: ArrayList<ItemsModel>,
    context: Context,
    var changeNumberItemListener : ChangeNumberItemsListener
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {



    class ViewHolder(val binding: ViewholderCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val managementCart = ManagmentCart(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.ViewHolder {

        val binding =
            ViewholderCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {

        val item = listItemSelected[position]

        holder.binding.apply {

            titleTxt.text = item.title
            feeEachTime.text = "$${item.price}"
            totalEachTime.text =
                "$${Math.round(item.numberInCart * item.price)}"

            numberItemTxt.text = item.numberInCart.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .into(pic)

            plusBtn.setOnClickListener {

                managementCart.plusItem(
                    listFood = listItemSelected,
                    position,
                    listener = object : ChangeNumberItemsListener {

                        override fun onChanged() {

                            notifyDataSetChanged()
                            changeNumberItemListener.onChanged()
                        }
                    }
                )
            }

            minusBtn.setOnClickListener {

                managementCart.minusItem(
                    listFood = listItemSelected,
                    position,
                    listener = object : ChangeNumberItemsListener {

                        override fun onChanged() {

                            notifyDataSetChanged()
                            changeNumberItemListener.onChanged()
                        }
                    }
                )
            }
        }
    }

    override fun getItemCount(): Int = listItemSelected.size
}