package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a5mindev.data.Shorts
import com.example.a5mindev.data.ShortsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {
    var client: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openTopicSelectionPage = Intent(this@MainActivity,  TopicScreen::class.java)
        startActivity(openTopicSelectionPage)
    }
}