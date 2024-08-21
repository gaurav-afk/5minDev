package com.example.a5mindev

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.a5mindev.databinding.ActivityShortsDetailBinding

class ShortsDetail : AppCompatActivity() {

    private var title: String? = null
    private var description: String? = null
    private var keyPoints: String? = null
    private var examples: String? = null
    private var conclusion: String? = null
    private lateinit var binding: ActivityShortsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityShortsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra(ARG_TITLE)
        description = intent.getStringExtra(ARG_DESCRIPTION)
        keyPoints = intent.getStringExtra(ARG_KEY_POINTS)
        conclusion = intent.getStringExtra(ARG_CONCLUSION)

        binding.tvDescTitle.text = title
        binding.tvDescription.text = description
        binding.tvKeyPoints.text = keyPoints
        binding.tvConclusion.text = conclusion

        binding.tvGoBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ARG_TITLE = "title"
        const val ARG_DESCRIPTION = "description"
        const val ARG_KEY_POINTS = "key_points"
        const val ARG_CONCLUSION = "conclusion"
    }
}
