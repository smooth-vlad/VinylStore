package com.android.vinylstore.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.vinylstore.R
import com.android.vinylstore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_nav_graph)
        binding.navigationView.setupWithNavController(navController)

//        binding.navigationView.setOnItemSelectedListener {  menuItem ->
//            when (menuItem.itemId) {
//                R.id.home_page -> navController.navigate(R.id.action_global_homeFragment)
//                R.id.cart_page -> navController.navigate(R.id.action_global_cartFragment)
//                R.id.favorites_page -> navController.navigate(R.id.action_global_favoritesFragment)
//            }
//            true
//        }
    }
}