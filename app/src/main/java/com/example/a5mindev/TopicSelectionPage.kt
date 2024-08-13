package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.a5mindev.databinding.ActivityTopicSelectionPageBinding

class TopicSelectionPage : AppCompatActivity() {
    private lateinit var binding: ActivityTopicSelectionPageBinding

    val topicButtonClickListener = View.OnClickListener { view ->
        val data = when(view.id){
            R.id.btn_web -> "Web development"
            R.id.btn_app -> "App development"
            R.id.btn_game -> "Game development"
            R.id.btn_aiml -> "AI and machine learning"
            else -> ""
        }
        handleButtonClick(data)
        Log.i("topic",data)
    }

    private fun handleButtonClick(data: String) {
        val openSubTopicPage = Intent(this, SubTopicSelectionPage::class.java)
        openSubTopicPage.putExtra("topic", data)
        startActivity(openSubTopicPage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopicSelectionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWeb.setOnClickListener(topicButtonClickListener)
        binding.btnApp.setOnClickListener(topicButtonClickListener)
        binding.btnGame.setOnClickListener(topicButtonClickListener)
        binding.btnAiml.setOnClickListener(topicButtonClickListener)
    }
}