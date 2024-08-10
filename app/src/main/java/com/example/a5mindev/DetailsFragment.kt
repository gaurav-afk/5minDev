import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a5mindev.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private var title: String? = null
    private var description: String? = null
    private var keyPoints: String? = null
    private var conclusion: String? = null
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_KEY_POINTS = "key_points"
        private const val ARG_CONCLUSION = "conclusion"

        fun newInstance(title: String, description: String, keyPoints: String, conclusion: String): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_KEY_POINTS, keyPoints)
                    putString(ARG_CONCLUSION, conclusion)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
            keyPoints = it.getString(ARG_KEY_POINTS)
            conclusion = it.getString(ARG_CONCLUSION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val binding = _binding!!

        binding.tvTitle.text = title
        binding.tvDescription.text = description
        binding.tvKeyPoints.text = keyPoints
        binding.tvConclusion.text = conclusion

        binding.tvGoBack.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
