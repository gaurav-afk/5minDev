package com.example.a5mindev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.bottomNav.setOnItemSelectedListener(this)

        // Attaching Fragment
        supportFragmentManager.commit {
            add(R.id.frame_content, FiveShortsFragment())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}