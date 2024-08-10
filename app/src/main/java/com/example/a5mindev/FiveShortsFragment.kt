package com.example.a5mindev

import DetailsFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a5mindev.databinding.FragmentFiveShortsBinding
import com.example.a5mindev.sampledata.Shorts
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException

class FiveShortsFragment : Fragment() {

    private var _binding: FragmentFiveShortsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_TOPIC = "topic"
        private const val ARG_SUB_TOPIC = "subTopic"

        fun newInstance(topic: String, subTopic: String): FiveShortsFragment {
            return FiveShortsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TOPIC, topic)
                    putString(ARG_SUB_TOPIC, subTopic)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiveShortsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topic = arguments?.getString(ARG_TOPIC)
        val subTopic = arguments?.getString(ARG_SUB_TOPIC)

        if (!topic.isNullOrEmpty() && !subTopic.isNullOrEmpty()) {
            val query = """Create five concise summaries on the topic of $topic and subtopic $subTopic. Each summary should include:
 
            Title: [Title]
            Description: [Description]
            Key Points: [Key Points]
            Conclusion: [Conclusion]

            Ensure the summaries are informative and relevant to the topic and subtopic. also return data in pure json"""
            fetchResponse(query)
        }
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
//                    handleError(e)
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
        val adapter = ShortsAdapter(requireContext(), shortsList)
        binding.lvShorts.adapter = adapter

        binding.lvShorts.setOnItemClickListener { _, _, position, _ ->
            val selectedShort = shortsList[position]

            val detailsFragment = DetailsFragment.newInstance(
                selectedShort.title,
                selectedShort.description,
                selectedShort.keypoints,
                selectedShort.conclusion
            )

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_content, detailsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
