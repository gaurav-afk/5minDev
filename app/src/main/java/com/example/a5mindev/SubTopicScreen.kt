package com.example.a5mindev

import SubTopicAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.a5mindev.databinding.ActivitySubTopicScreenBinding

class SubTopicScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySubTopicScreenBinding
    private lateinit var topic: String
    private lateinit var subtopics: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubTopicScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topic = intent.getStringExtra("topic") ?: "Unknown"
        binding.tvTopics.text = topic
        subtopics = getSubtopicsForTopic(topic)
        changeLogoImage(topic.lowercase())


        if (subtopics.isNotEmpty()) {
            val adapter = SubTopicAdapter(this, subtopics) { selectedSubtopic ->
                handleButtonClick(selectedSubtopic)
            }
            binding.listViewSubtopics.adapter = adapter
        } else {
            Log.w("SubTopicScreen", "No subtopics found for topic: $topic")
        }
        binding.tvTopics.setOnClickListener {
            finish()
        }
    }

    private fun getSubtopicsForTopic(topic: String): List<String> {
        return when (topic) {
            "Web development" -> listOf("Front end development", "Back end development", "Full stack development")
            "App development" -> listOf("Android development", "iOS development", "Cross-platform development", "Flutter", "React Native")
            "Game development" -> listOf("Game Design", "Game Programming", "Art and Animation", "Audio Design", "Game Physics", "Game Engines", "UI/UX Design", "Testing and Debugging", "VR/AR Development", "Unity Development", "Unreal Engine Development")
            "AI and machine learning" -> listOf("Machine learning", "Deep learning", "Data science")
            else -> emptyList()
        }
    }

    private fun handleButtonClick(subTopic: String) {
            val openCategoryScreen = Intent(this, CategoryScreen::class.java)
        openCategoryScreen.putExtra("topic", topic)
        openCategoryScreen.putExtra("subTopic", subTopic)
            startActivity(openCategoryScreen)
    }

    private fun changeLogoImage(topic: String) {
        when (topic) {
            "web development" -> binding.logo.setImageResource(R.drawable.ic_globe)
            "app development" -> binding.logo.setImageResource(R.drawable.ic_smartphone_24)
            "game development" -> binding.logo.setImageResource(R.drawable.ic_videogame_asset_24)
            "ai and machine learning" -> binding.logo.setImageResource(R.drawable.ic_ai_24)
            else -> binding.logo.setImageResource(R.drawable.ic_shorts_24)
        }
        when (topic) {
            "web development" -> binding.root.background = ContextCompat.getDrawable(this, R.drawable.gradient_web)
            "app development" -> binding.root.background = ContextCompat.getDrawable(this, R.drawable.gradient_app)
            "game development" -> binding.root.background = ContextCompat.getDrawable(this, R.drawable.gradient_game)
            "ai and machine learning" -> binding.root.background = ContextCompat.getDrawable(this, R.drawable.gradient_aiml)
            else -> binding.root.background = ContextCompat.getDrawable(this, R.drawable.gradient_web)
        }
        when (topic) {
            "web development" -> binding.logo.setColorFilter(ContextCompat.getColor(this, R.color.spaceblue))
            "app development" -> binding.logo.setColorFilter(ContextCompat.getColor(this, R.color.oldMandarin))
            "game development" -> binding.logo.setColorFilter(ContextCompat.getColor(this, R.color.parisianNight))
            "ai and machine learning" -> binding.logo.setColorFilter(ContextCompat.getColor(this, R.color.bayou))
            else -> binding.logo.setColorFilter(ContextCompat.getColor(this, R.color.oldMandarin))
        }
    }
}