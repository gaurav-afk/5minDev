package com.example.a5mindev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.MenuItem
import android.widget.Button
import androidx.fragment.app.commit
import com.example.a5mindev.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomePage : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // view binding
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // adding listener for bottom_nav to detect change in menu item
        binding.bottomNav.setOnItemSelectedListener(this)

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_home){
            onHomeClicked()
            return true
        }
        else if (item.itemId == R.id.nav_profile){
            onProfileClicked()
            return true
        }
        return false
    }

    private fun onHomeClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, FiveShortsFragment())
        }
    }

    private fun onProfileClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, ProfileFragment())
        }
    }


}