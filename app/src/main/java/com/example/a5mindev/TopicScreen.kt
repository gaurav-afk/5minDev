package com.towerofapp.a5mindev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.towerofapp.a5mindev.databinding.ActivityTopicScreenBinding

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

    private val clickListener_for_recent = View.OnClickListener {
        val openSubTopicPage = Intent(this, FiveShorts::class.java)
        openSubTopicPage.putExtra("fromTopic", true)
        startActivity(openSubTopicPage)
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

        binding.tvRecentShorts.setOnClickListener(clickListener_for_recent)
        binding.logoRecent.setOnClickListener(clickListener_for_recent)

        binding.cardWeb.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardApp.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardGame.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardAiml.customTopicCard.setOnClickListener(topicButtonClickListener)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAndRemoveTask()
            }
        })
    }

    private fun setCardData() {

        binding.cardWeb.tvTopic.text = "Web development"
        binding.cardApp.tvTopic.text = "App development"
        binding.cardGame.tvTopic.text = "Game development"
        binding.cardAiml.tvTopic.text = "AI/ML"

        binding.cardWeb.topicDescription.text = "Blending what we see with the hidden work that makes it all run smoothly."
        binding.cardApp.topicDescription.text = "Crafting digital dreams, where design and code blend to make your device come alive."
        binding.cardGame.topicDescription.text = "Sculpting realms of play, where creativity and code dance to spark adventure and fun."
        binding.cardAiml.topicDescription.text = "Molding the future, where data and algorithms blend to create machines that learn and think."

        binding.cardWeb.customTopicCard.setCardBackgroundColor(getColor(R.color.web_color))
        binding.cardApp.customTopicCard.setCardBackgroundColor(getColor(R.color.exp))
        binding.cardGame.customTopicCard.setCardBackgroundColor(getColor(R.color.game_color))
        binding.cardAiml.customTopicCard.setCardBackgroundColor(getColor(R.color.aiml_color))

        binding.cardWeb.tvLogo.setImageResource(R.drawable.internet)
        binding.cardApp.tvLogo.setImageResource(R.drawable.ic_smartphone_24)
        binding.cardGame.tvLogo.setImageResource(R.drawable.game)
        binding.cardAiml.tvLogo.setImageResource(R.drawable.aiml)

        binding.cardWeb.tvLogo.setColorFilter(ContextCompat.getColor(this, R.color.green))
        binding.cardApp.tvLogo.setColorFilter(ContextCompat.getColor(this, R.color.white))
        binding.cardGame.tvLogo.setColorFilter(ContextCompat.getColor(this, R.color.white))
        binding.cardAiml.tvLogo.setColorFilter(ContextCompat.getColor(this, R.color.aiml_logo_color))
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        finish()
    }
}