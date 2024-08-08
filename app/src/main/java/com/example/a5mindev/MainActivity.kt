package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {
    var client: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openTopicSelectionPage = Intent(this@MainActivity,  TopicSelectionPage::class.java)
        startActivity(openTopicSelectionPage)
    }
}