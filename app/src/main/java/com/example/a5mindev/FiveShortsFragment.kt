package com.example.a5mindev

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a5mindev.databinding.FragmentFiveShortsBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val query = "write 5 shorts on $topic $subTopic with title, description, keypoints and conclusion"
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

                withContext(Dispatchers.Main) {
                    handleResponse(response)
                }
            } catch (e: Exception) {
                Log.e("FiveShortsFragment", "Error fetching response: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    handleError(e)
                }
            }
        }
    }

    private fun handleResponse(response: String) {
        binding.tvResponse.text = response
        Log.i("FiveShortsFragment", "Generated Response: $response")
    }

    private fun handleError(exception: Exception) {
        binding.tvResponse.text = "Error: ${exception.message}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
