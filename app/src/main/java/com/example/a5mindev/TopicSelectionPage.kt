package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a5mindev.databinding.ActivityHomePageBinding
import com.example.a5mindev.databinding.ActivityTopicSelectionPageBinding

class TopicSelectionPage : AppCompatActivity() {
    private lateinit var binding: ActivityTopicSelectionPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopicSelectionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWeb.setOnClickListener {
            val openSubTopicPage = Intent(this, SubTopicSelectionPage::class.java)
            startActivity(openSubTopicPage)
        }
    }
}