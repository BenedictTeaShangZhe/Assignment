package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.assignment.fragment.FoodBankFragment
import com.example.assignment.fragment.HomepageFragment
import com.example.assignment.fragment.SearchingFragment
import com.example.assignment.fragment.UserProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationBarMenuView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private val homepageFragment = HomepageFragment()
    private val searchingFragment = SearchingFragment()
    private val foodBankFragment = FoodBankFragment()
    private val userprofileFragment = UserProfileFragment()
    lateinit var bottom_navigation : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homepageFragment)

        bottom_navigation = findViewById(R.id.bottom_navigation)
        bottom_navigation.setOnItemSelectedListener{
            when(it.itemId){
                R.id.ic_homepage->replaceFragment(homepageFragment)
                R.id.ic_search->replaceFragment(searchingFragment)
                R.id.ic_foodbank->replaceFragment(foodBankFragment)
                R.id.ic_userprofile->replaceFragment(userprofileFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }
}