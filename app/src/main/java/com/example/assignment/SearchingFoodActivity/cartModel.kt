package com.example.assignment.SearchingFoodActivity

import androidx.lifecycle.ViewModel

class cartModel: ViewModel() {

    private var cartlist: MutableList<CartData> = mutableListOf<CartData>()

    fun addItem(cartData: CartData) {
        cartlist.add(cartData)
    }

    fun getCartList(): MutableList<CartData>{
        return cartlist
    }
}