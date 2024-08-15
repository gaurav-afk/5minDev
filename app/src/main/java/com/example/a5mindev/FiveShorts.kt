package com.example.a5mindev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
        enableEdgeToEdge()
        setContentView(binding.root)
        val query =
            """Create five summaries, each around 300 words, on the topic of $topic focusing specifically on the subtopic of $subTopic. Ensure each summary is random and should not be repeated and is detailed and directly related to the subtopic. Each summary should include:
            - **Title:** A concise and relevant title.
            - **Description:** A detail explanation in simple language of the aspect of the subtopic.
- **Key Points:** Main points that highlight the essential information. add newline \n at last of each key point and circle at the starting of each key point.
- **Conclusion:** A brief summary of the findings or implications related to the subtopic.
Make sure the summaries are informative, relevant, and strictly adhere to the subtopic. Return the summaries in pure JSON format.
"""
        fetchResponse(query)

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
                    modelName = "gemini-pro",
                    apiKey = BuildConfig.API_KEY
                )
                val response = generativeModel.generateContent(query).text.toString()

                val shortsList = parseResponse(response)
                withContext(Dispatchers.Main) {
                    displayShorts(shortsList)
                }
                Log.i("response: ",response)
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
                putExtra(ShortsDetail.ARG_KEY_POINTS, selectedShort.keypoints)
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
                .replace("\\n", "")  // Removes any newline characters
                .trim()

            val jsonArray = JSONArray(cleanedResponse)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val title = jsonObject.optString("Title", "")
                val description = jsonObject.optString("Description", "")

                // Clean up key points by removing any unexpected line breaks or formatting
                val keyPoints = jsonObject.optString("Key Points", "")
                    .replace("\n", " ")  // Replace line breaks with a space
                    .trim()

                val conclusion = jsonObject.optString("Conclusion", "")
                    .replace("\n", " ")  // Replace line breaks with a space
                    .trim()

                shortsList.add(Shorts(title, description, keyPoints, "Conclusion: $conclusion"))
            }
        } catch (e: JSONException) {
            Log.e("FiveShortsFragment", "Error parsing JSON response: ${e.message}", e)
        }

        Log.d("Parsed Shorts List", shortsList.toString())
        return shortsList
    }

//    private fun parseResponse(response: String): List<Shorts> {
//        val shortsList = mutableListOf<Shorts>()
//
//        try {
//            Log.d("Raw Response", response)
//
//            val cleanedResponse = response
//                .trim()
//                .removePrefix("```json")
//                .removeSuffix("```")
//                .trim()
//
//            val jsonArray = JSONArray(cleanedResponse)
//
//            for (i in 0 until jsonArray.length()) {
//                val jsonObject = jsonArray.getJSONObject(i)
//
//                val title = jsonObject.optString("Title", "")
//                val description = jsonObject.optString("Description", "")
//
//                val keyPoints = jsonObject.optString("Key Points", "")
//
//                val conclusion = jsonObject.optString("Conclusion", "")
//
//                shortsList.add(Shorts(title, description, keyPoints, "Conclusion: $conclusion"))
//            }
//        } catch (e: JSONException) {
//            Log.e("FiveShortsFragment", "Error parsing JSON response: ${e.message}", e)
//        }
//
//        Log.d("Parsed Shorts List", shortsList.toString())
//        return shortsList
//    }


}
