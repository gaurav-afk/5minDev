package com.example.a5mindev

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.a5mindev.databinding.ActivityHomePageBinding
import com.google.android.material.navigation.NavigationBarView

class HomePage : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.setOnItemSelectedListener(this)

        if (savedInstanceState == null) {
            onHomeClicked()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_home -> {
                onHomeClicked()
                true
            }
            R.id.nav_profile -> {
                onProfileClicked()
                true
            }
            else -> false
        }
    }

    private fun onHomeClicked() {
        val topic = intent.getStringExtra("topic").orEmpty()
        val subTopic = intent.getStringExtra("subTopic").orEmpty()

        val fragment = FiveShortsFragment.newInstance(topic, subTopic)
        supportFragmentManager.commit {
            replace(R.id.frame_content, fragment)
        }
    }

    private fun onProfileClicked() {
        supportFragmentManager.commit {
            replace(R.id.frame_content, ProfileFragment())
        }
    }
}
