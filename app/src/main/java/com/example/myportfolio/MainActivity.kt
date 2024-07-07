package com.example.myportfolio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.myportfolio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            val navController = findNavController(R.id.fragment_container)
            when (item.itemId) {
                R.id.home_menu_item -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.asset_list_menu_item -> {
                    navController.navigate(R.id.assetListFragment)
                    true
                }
                R.id.portfolio_menu_item -> {
                    navController.navigate(R.id.portfolioFragment)
                    true
                }
                R.id.settings_menu_item -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }
    }
}