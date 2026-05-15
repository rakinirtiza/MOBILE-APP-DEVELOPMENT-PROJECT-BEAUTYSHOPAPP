package com.example.beautyshopapp.MainviewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyshopapp.domain.CategoryModel
import com.example.beautyshopapp.domain.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _category = MutableLiveData<List<CategoryModel>>()
    private val _banner = MutableLiveData<List<SliderModel>>()

    val category: LiveData<List<CategoryModel>> = _category
    val banner: LiveData<List<SliderModel>> = _banner

    fun loadCategory() {

        val ref = firebaseDatabase.getReference("Category")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val list = mutableListOf<CategoryModel>()

                for (child in snapshot.children) {

                    val model = child.getValue(CategoryModel::class.java)

                    if (model != null) {
                        list.add(model)
                    }
                }

                _category.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun loadBanners() {

        val ref = firebaseDatabase.getReference("Banner")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                val list = mutableListOf<SliderModel>()

                for (child in snapshot.children) {

                    val model = child.getValue(SliderModel::class.java)

                    if (model != null) {
                        list.add(model)
                    }
                }

                _banner.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}