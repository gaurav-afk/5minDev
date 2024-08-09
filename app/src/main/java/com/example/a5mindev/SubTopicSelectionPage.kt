package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.a5mindev.databinding.ActivitySubTopicSelectionPageBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.runBlocking


class SubTopicSelectionPage : AppCompatActivity() {
    private lateinit var binding: ActivitySubTopicSelectionPageBinding
    private lateinit var topic: String

    private val subTopicButtonClickListener = View.OnClickListener { view ->
        val data = when (view.id) {
            R.id.btn_front_end -> "Front end development"
            R.id.btn_back_end -> "Back end development"
            R.id.btn_full_stack -> "Full stack development"
            else -> ""
        }
        handleButtonClick(data)
        Log.i("sub-topic",data)
    }

    private fun handleButtonClick(subTopic: String) {
        val openHomePage = Intent(this, HomePage::class.java)
        openHomePage.putExtra("topic", topic)
        openHomePage.putExtra("subTopic", subTopic)
        startActivity(openHomePage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubTopicSelectionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topic = intent.getStringExtra("topic").toString()
        binding.btnFrontEnd.setOnClickListener(subTopicButtonClickListener)
        binding.btnBackEnd.setOnClickListener(subTopicButtonClickListener)
        binding.btnFullStack.setOnClickListener(subTopicButtonClickListener)

        binding.tvTopics.setOnClickListener {
            finish()
        }
    }
}
