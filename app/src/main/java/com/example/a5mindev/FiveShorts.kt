package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a5mindev.databinding.ActivityFiveShortsBinding
import com.example.a5mindev.sampledata.Shorts
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException

class FiveShorts : AppCompatActivity() {
    lateinit var binding: ActivityFiveShortsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val topic = intent.getStringExtra("topic").orEmpty()
        val subTopic = intent.getStringExtra("subTopic").orEmpty()
        binding = ActivityFiveShortsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val query = """Create five concise summaries on the topic of $topic and subtopic $subTopic. Each summary should include:
 
            Title: [Title]
            Description: [Description]
            Key Points: [Key Points]
            Conclusion: [Conclusion]

            Ensure the summaries are informative and relevant to the topic and subtopic. also return data in pure json"""
        fetchResponse(query)
    }

    private fun fetchResponse(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val generativeModel = GenerativeModel(
                    modelName = "gemini-pro",
                    apiKey = BuildConfig.API_KEY
                )
                val response = generativeModel.generateContent(query).text.toString()

                val shortsList = parseResponse(response)
                withContext(Dispatchers.Main) {
                    displayShorts(shortsList)
                }
            } catch (e: Exception) {
                Log.e("FiveShortsFragment", "Error fetching response: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    // handleError(e)
                }
            }
        }
    }

    private fun parseResponse(response: String): List<Shorts> {
        val shortsList = mutableListOf<Shorts>()

        try {
            Log.d("Raw Response", response)

            val cleanedResponse = response
                .trim()
                .removePrefix("```json")
                .removeSuffix("```")
                .trim()

            val jsonArray = JSONArray(cleanedResponse)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val title = jsonObject.optString("Title", "")
                val description = jsonObject.optString("Description", "")

                val keyPointsArray = jsonObject.optJSONArray("Key Points")
                val keyPoints = keyPointsArray?.let {
                    (0 until it.length()).joinToString(separator = ", ") { index ->
                        it.optString(index)
                    }
                } ?: ""

                val conclusion = jsonObject.optString("Conclusion", "")

                shortsList.add(Shorts(title, description, keyPoints, conclusion))
            }
        } catch (e: JSONException) {
            Log.e("FiveShortsFragment", "Error parsing JSON response: ${e.message}", e)
        }

        Log.d("Parsed Shorts List", shortsList.toString())
        return shortsList
    }

    private fun displayShorts(shortsList: List<Shorts>) {
        val adapter = ShortsAdapter(this, shortsList)
        binding.lvShorts.adapter = adapter
        binding.lvShorts.setOnItemClickListener { _, _, position, _ ->
            val selectedShort = shortsList[position]

            val intent = Intent(this, ShortsDetail::class.java).apply {
                putExtra(ShortsDetail.ARG_TITLE, selectedShort.title)
                putExtra(ShortsDetail.ARG_DESCRIPTION, selectedShort.description)
                putExtra(ShortsDetail.ARG_KEY_POINTS, selectedShort.keypoints)
                putExtra(ShortsDetail.ARG_CONCLUSION, selectedShort.conclusion)
            }

            startActivity(intent)
        }
    }

}
