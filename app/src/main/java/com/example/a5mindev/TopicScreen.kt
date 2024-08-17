package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a5mindev.databinding.ActivityTopicScreenBinding

class TopicScreen : AppCompatActivity() {
    private lateinit var binding: ActivityTopicScreenBinding

    private val topicButtonClickListener = View.OnClickListener { view ->
        val data = when(view.id){
            R.id.card_web -> "Web development"
            R.id.card_app -> "App development"
            R.id.card_game -> "Game development"
            R.id.card_aiml -> "AI and machine learning"
            else -> ""
        }
        handleButtonClick(data)
        Log.i("topic",data)
    }

    private fun handleButtonClick(data: String) {
        val openSubTopicPage = Intent(this, SubTopicScreen::class.java)
        openSubTopicPage.putExtra("topic", data)

        startActivity(openSubTopicPage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopicScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setCardData()


        binding.cardWeb.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardApp.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardGame.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardAiml.customTopicCard.setOnClickListener(topicButtonClickListener)
    }

    private fun setCardData() {


        binding.cardWeb.topic.text = "Web development"
        binding.cardApp.topic.text = "App development"
        binding.cardGame.topic.text = "Game development"
        binding.cardAiml.topic.text = "AI/ML"

        binding.cardWeb.topicDescription.text = "blending what we see with the hidden work that makes it all run smoothly."
        binding.cardApp.topicDescription.text = "crafting digital dreams, where design and code blend to make your device come alive."
        binding.cardGame.topicDescription.text = "sculpting realms of play, where creativity and code dance to spark adventure and fun."
        binding.cardAiml.topicDescription.text = "molding the future, where data and algorithms blend to create machines that learn and think."

        binding.cardWeb.customTopicCard.setCardBackgroundColor(getColor(R.color.web_color))
        binding.cardApp.customTopicCard.setCardBackgroundColor(getColor(R.color.app_color))
        binding.cardGame.customTopicCard.setCardBackgroundColor(getColor(R.color.game_color))
        binding.cardAiml.customTopicCard.setCardBackgroundColor(getColor(R.color.aiml_color))

        binding.cardWeb.logo.setImageResource(R.drawable.internet)
        binding.cardApp.logo.setImageResource(R.drawable.ic_smartphone_24)
        binding.cardGame.logo.setImageResource(R.drawable.game)
        binding.cardAiml.logo.setImageResource(R.drawable.aiml)

        binding.cardWeb.logo.setColorFilter(ContextCompat.getColor(this, R.color.green))
        binding.cardApp.logo.setColorFilter(ContextCompat.getColor(this, R.color.white))
        binding.cardGame.logo.setColorFilter(ContextCompat.getColor(this, R.color.white))
        binding.cardAiml.logo.setColorFilter(ContextCompat.getColor(this, R.color.aiml_logo_color))
    }
}
