package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val openTopicSelectionPage = Intent(this@MainActivity,  TopicSelectionPage::class.java)
        startActivity(openTopicSelectionPage)
    }
}