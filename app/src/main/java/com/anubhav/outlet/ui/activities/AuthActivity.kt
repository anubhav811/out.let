package com.anubhav.outlet.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anubhav.outlet.databinding.ActivityAuthBinding
import com.anubhav.outlet.ui.activities.ui.fragments.LoginFragment
import com.anubhav.outlet.ui.activities.ui.fragments.SignUpFragment
import com.google.android.material.tabs.TabLayoutMediator
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials


class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view =  binding.root
        setContentView(view)


        val fragments : ArrayList<Fragment> = arrayListOf(
            LoginFragment(),
            SignUpFragment()
        )
        val adapter = ViewPagerAdapter(fragments,this)
        binding.viewPager2.adapter  = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "LOGIN"
                1 -> tab.text = "SIGN UP"
            }
        }.attach()

    }




    class ViewPagerAdapter(
        val items :  ArrayList<Fragment>,
        activity: AppCompatActivity
    ): FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return items.size
        }

        override fun createFragment(position: Int): Fragment {
            return items[position]
        }
    }




}