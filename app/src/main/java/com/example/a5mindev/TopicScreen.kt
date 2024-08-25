package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.a5mindev.databinding.ActivityTopicScreenBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class TopicScreen : AppCompatActivity() {
    private lateinit var binding: ActivityTopicScreenBinding
    private var mInterstitialAd: InterstitialAd? = null
    private var lastAdTime: Long = 0

    private val topicButtonClickListener = View.OnClickListener { view ->
        val data = when (view.id) {
            R.id.card_web -> "Web development"
            R.id.card_app -> "App development"
            R.id.card_game -> "Game development"
            R.id.card_aiml -> "AI and machine learning"
            else -> ""
        }
        handleButtonClick(data)
    }

    private val clickListener_for_recent = View.OnClickListener {
        val openSubTopicPage = Intent(this, FiveShorts::class.java)
        openSubTopicPage.putExtra("fromTopic", true)
        startActivity(openSubTopicPage)
    }

    private fun handleButtonClick(data: String) {

        showInterAd(data)
    }


    private fun showInterAd(data: String) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastAdTime >= 2 * 60 * 1000) { // 2 minutes in milliseconds
            if (mInterstitialAd != null) {
                Log.i("topic1", data)
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        val openSubTopicPage = Intent(this@TopicScreen, SubTopicScreen::class.java)
                        openSubTopicPage.putExtra("topic", data)
                        startActivity(openSubTopicPage)
                    }
                }
                mInterstitialAd?.show(this)
            } else {
                Log.i("topic2", data)
                val openSubTopicPage = Intent(this, SubTopicScreen::class.java)
                openSubTopicPage.putExtra("topic", data)
                startActivity(openSubTopicPage)
            }
            lastAdTime = currentTime // Update the last ad time
        } else {
            Log.i("topic3", "Ad cooldown not completed $lastAdTime $currentTime")
            val openSubTopicPage = Intent(this, SubTopicScreen::class.java)
            openSubTopicPage.putExtra("topic", data)
            startActivity(openSubTopicPage)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopicScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setCardData()
        loadInterAd()

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

    override fun onResume() {
        super.onResume()
        Log.d("TopicScreen", "Activity resumed")
        binding.cardWeb.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardApp.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardGame.customTopicCard.setOnClickListener(topicButtonClickListener)
        binding.cardAiml.customTopicCard.setOnClickListener(topicButtonClickListener)
    }

    private fun setCardData() {

        binding.cardWeb.tvTopic.text = "Web development"
        binding.cardApp.tvTopic.text = "App development"
        binding.cardGame.tvTopic.text = "Game development"
        binding.cardAiml.tvTopic.text = "AI/ML"

        binding.cardWeb.topicDescription.text =
            "Blending what we see with the hidden work that makes it all run smoothly."
        binding.cardApp.topicDescription.text =
            "Crafting digital dreams, where design and code blend to make your device come alive."
        binding.cardGame.topicDescription.text =
            "Sculpting realms of play, where creativity and code dance to spark adventure and fun."
        binding.cardAiml.topicDescription.text =
            "Molding the future, where data and algorithms blend to create machines that learn and think."

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
        binding.cardAiml.tvLogo.setColorFilter(
            ContextCompat.getColor(
                this,
                R.color.aiml_logo_color
            )
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        finish()
    }

    fun loadInterAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }
}
