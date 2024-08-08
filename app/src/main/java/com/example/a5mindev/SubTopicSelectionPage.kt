package com.example.a5mindev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a5mindev.databinding.ActivitySubTopicSelectionPageBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.runBlocking


class SubTopicSelectionPage : AppCompatActivity() {
    private lateinit var binding: ActivitySubTopicSelectionPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubTopicSelectionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnFrontEnd.setOnClickListener {
            val query = "write about web dev front end in 5 line"
            getResponse(query)
        }

        binding.tvTopics.setOnClickListener {
            finish()
        }
    }

    private fun getResponse(query: String) {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.API_KEY
        )

        runBlocking {
            val prompt = query
            val response = generativeModel.generateContent(prompt)
            binding.tvApiResponse.text = response.text
        }

    }
}
