package com.example.beautyshopapp.MainviewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.beautyshopapp.domain.CategoryModel

class MainViewModel : ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _category = MutableLiveData<List<CategoryModel>>()

    val category: LiveData<List<CategoryModel>> = _category

    fun loadCategory() {

        val Ref = firebaseDatabase.getReference("Category")

        Ref.addValueEventListener(object : ValueEventListener {

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
}