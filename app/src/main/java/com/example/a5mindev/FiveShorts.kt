package com.towerofapp.a5mindev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.ai.client.generativeai.GenerativeModel
import com.towerofapp.a5mindev.data.Shorts
import com.towerofapp.a5mindev.data.ShortsDatabase
import com.towerofapp.a5mindev.databinding.ActivityFiveShortsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException


class FiveShorts : AppCompatActivity() {
    private lateinit var binding: ActivityFiveShortsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFiveShortsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val topic = intent.getStringExtra("topic").orEmpty()
        val subTopic = intent.getStringExtra("subTopic").orEmpty()
        val category = intent.getStringExtra("category").orEmpty()
        val fromTopic = intent.getBooleanExtra("fromTopic", false)

        if (fromTopic) {
            CoroutineScope(Dispatchers.Main).launch {
                val shortsList = getShortsFromDatabase()
                displayShorts(shortsList)
            }
        } else {
            fetchResponse(generateQuery(topic, subTopic, category))
        }

        binding.tvGoBack.setOnClickListener {
            finish()
        }
    }

    private fun generateQuery(topic: String, subTopic: String, category: String): String {
        return """
            Create five summaries, each around 300 words, on the $topic, $subTopic especially focusing on its category $category. Ensure each summary is random and should not be repeated and is detailed and directly related to the subtopic. Each summary should include:
            - **Title:** A concise and relevant title.
            - **Description:** A detailed explanation in simple language of the aspect of the subtopic.
            - **Key Points:** Main points that highlight the essential information. Add newline \n at the end of each key point and • at the start of each key point.
            - **Conclusion:** A brief summary of the findings or implications related to the subtopic.
            
            Make sure the summaries are informative, relevant, and strictly adhere to the subtopic and category and also simple to understand. Return the summaries in pure JSON format.
            
            Ensure the format is:
            ```json
            [
              {
                "title": "title",
                "description": "description",
                "keyPoints": "•keypoint1.\n •keypoint2",
                "conclusion": "conclusion"
              }
            ]
            ```
        """.trimIndent()
    }
    private fun fetchResponse(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            showLoading(true)

            try {
                val response = withContext(Dispatchers.IO) {
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-pro", apiKey = BuildConfig.API_KEY
                    )
                    generativeModel.generateContent(query).text.toString()
                }

                val shortsList = parseResponse(response)
                displayShorts(shortsList)
                saveShortsToDatabase(shortsList)

            } catch (e: Exception) {
                Log.e("FiveShorts", "Error fetching response: ${e.message}", e)
                showLoading(false)
            }
        }
    }
    private suspend fun getShortsFromDatabase(): List<Shorts> {
        return withContext(Dispatchers.IO) {
            ShortsDatabase.getDatabase(this@FiveShorts).shortsDao().getAllShorts()
        }
    }
    private fun saveShortsToDatabase(shortsList: List<Shorts>) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = ShortsDatabase.getDatabase(this@FiveShorts)
            db.shortsDao().deleteAllShorts()
            db.shortsDao().insertAll(*shortsList.toTypedArray())
        }
    }
    private fun parseResponse(response: String): List<Shorts> {
        val shortsList = mutableListOf<Shorts>()
        try {
            Log.d("Raw Response", response)

            val cleanedResponse = response.trim()
                .removePrefix("```json")
                .removeSuffix("```")
                .trim()

            val jsonArray = JSONArray(cleanedResponse)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val title = jsonObject.optString("title", "")
                val description = jsonObject.optString("description", "")
                val keyPoints = jsonObject.optString("keyPoints", "")
                val conclusion = jsonObject.optString("conclusion", "")

                shortsList.add(Shorts(title, description, keyPoints, conclusion))
            }
        } catch (e: JSONException) {
            Log.e("FiveShorts", "Error parsing JSON response: ${e.message}", e)
        }

        Log.d("Parsed Shorts List", shortsList.toString())
        return shortsList
    }
    private fun displayShorts(shortsList: List<Shorts>) {
        showLoading(false)
        if(shortsList.isEmpty()){
            binding.tvNoShorts.visibility = View.VISIBLE
        }
        else {
            val adapter = ShortsAdapter(this, shortsList)
            binding.lvShorts.adapter = adapter
            binding.lvShorts.setOnItemClickListener { _, _, position, _ ->
                val selectedShort = shortsList[position]

                val intent = Intent(this, ShortsDetail::class.java).apply {
                    putExtra(ShortsDetail.ARG_TITLE, selectedShort.title)
                    putExtra(ShortsDetail.ARG_DESCRIPTION, selectedShort.description)
                    putExtra(ShortsDetail.ARG_KEY_POINTS, selectedShort.keyPoints)
                    putExtra(ShortsDetail.ARG_CONCLUSION, selectedShort.conclusion)
                }

                startActivity(intent)
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.shimmerLayout.startShimmer()
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.lvShorts.visibility = View.GONE
        } else {
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.lvShorts.visibility = View.VISIBLE
        }
    }
}
