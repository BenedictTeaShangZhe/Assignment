package com.example.assignment.SearchingFoodActivity

import androidx.lifecycle.ViewModel

class cartModel: ViewModel() {

    private var cartlist: MutableList<dataCart> = mutableListOf<dataCart>()

    fun addItem(cartData: dataCart) {
        cartlist.add(cartData)
    }

    fun getCartList(): MutableList<dataCart>{
        return cartlist
    }
}