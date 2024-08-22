package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.a5mindev.data.ShortsDatabase
import com.example.a5mindev.databinding.ActivityFiveShortsBinding
import com.example.a5mindev.data.Shorts
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
        val category = intent.getStringExtra("category").orEmpty()
        val fromTopic = intent.getBooleanExtra("fromTopic", false)
        binding = ActivityFiveShortsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val query =
            """Create five summaries, each around 300 words, on the $topic, $subTopic especially focusing on it's category $category. Ensure each summary is random and should not be repeated and is detailed and directly related to the subtopic. Each summary should include:
            - **Title:** A concise and relevant title.
            - **Description:** A detail explanation in simple language of the aspect of the subtopic.
- **Key Points:** Main points that highlight the essential information. add newline \n at last of each key point and • at the starting of each key point.
- **Conclusion:** A brief summary of the findings or implications related to the subtopic.
Make sure the summaries are informative, relevant, and strictly adhere to the subtopic and category and also simple to understand. Return the summaries in pure JSON format.

make sure it's in the following exact format:
```json
[
  {
    "title": “title”,
    "description": “description.”,
    "keyPoints": "•keypoint1.\n •keypoint2",
    "conclusion": “conclusion”
  },
]```
"""

        if(fromTopic){
            val shortsList = getShortsFromDatabase()
            displayShorts(shortsList)
        }else {
            fetchResponse(query)
        }
        binding.tvGoBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchResponse(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            binding.shimmerLayout.startShimmer()
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.lvShorts.visibility = View.GONE
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val generativeModel = GenerativeModel(
                    modelName = "gemini-pro", apiKey = BuildConfig.API_KEY
                )
                val response = generativeModel.generateContent(query).text.toString()

                val shortsList = parseResponse(response)
                withContext(Dispatchers.Main) {
                    displayShorts(shortsList)
                    saveShortsToDatabase(shortsList)
                }
                Log.i("response: ", response)
            } catch (e: Exception) {
                Log.e("FiveShortsFragment", "Error fetching response: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun displayShorts(shortsList: List<Shorts>) {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.lvShorts.visibility = View.VISIBLE

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

                val title = jsonObject.optString("title", "")
                val description = jsonObject.optString("description", "")
                val keyPoints = jsonObject.optString("keyPoints", "")
                val conclusion = jsonObject.optString("conclusion", "")

                shortsList.add(Shorts( title, description, keyPoints, conclusion))
            }
        } catch (e: JSONException) {
            Log.e("FiveShortsFragment", "Error parsing JSON response: ${e.message}", e)
        }

        Log.d("Parsed Shorts List", shortsList.toString())
        return shortsList
    }
    private fun saveShortsToDatabase(shortsList: List<Shorts>) {
        val db = ShortsDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            db.shortsDao().deleteAllShorts()
            db.shortsDao().insertAll(*shortsList.toTypedArray())
        }
    }

    private fun getShortsFromDatabase(): List<Shorts> {
        val db = ShortsDatabase.getDatabase(this)
        var shortsList: List<Shorts> = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            shortsList = db.shortsDao().getAllShorts()
            withContext(Dispatchers.Main) {
                Log.i("roomdb:", shortsList.toString())
                displayShorts(shortsList)
            }
        }
        return shortsList
    }
}