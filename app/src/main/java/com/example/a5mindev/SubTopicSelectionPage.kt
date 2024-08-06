package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a5mindev.databinding.ActivityHomePageBinding
import com.example.a5mindev.databinding.ActivitySubTopicSelectionPageBinding
import java.util.zip.Inflater

class SubTopicSelectionPage : AppCompatActivity() {
    private lateinit var binding: ActivitySubTopicSelectionPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubTopicSelectionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWebFrontDev.setOnClickListener{
            val openHomePage = Intent(this, HomePage::class.java)
            startActivity(openHomePage)
        }
    }
}