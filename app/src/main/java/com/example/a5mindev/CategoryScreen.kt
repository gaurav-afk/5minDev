package com.example.a5mindev

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a5mindev.databinding.ActivityCategoryScreenBinding
import com.google.android.flexbox.FlexboxLayout

class CategoryScreen : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryScreenBinding
    private lateinit var flexboxLayout: FlexboxLayout
    private lateinit var categories: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        flexboxLayout = binding.flexboxLayout
        val subTopic = intent.getStringExtra("subTopic") ?: "Unknown"
        categories = getCategoriesForSubtopic(subTopic.lowercase())
        displayCategories(categories)
    }

    private fun getCategoriesForSubtopic(subTopic: String): List<String> {
        return when (subTopic) {
            // Web-development
            "front end development" -> listOf("HTML", "CSS", "JavaScript", "React", "Angular", "Vue.js", "Sass", "Less", "Bootstrap", "Tailwind CSS", "jQuery", "Webpack", "Babel", "TypeScript", "Foundation", "Materialize")
            "back end development" -> listOf("Node.js", "Express", "Django", "Flask", "Ruby on Rails", "Spring Boot", "ASP.NET", "Laravel", "Symfony", "Java", "C#", "Go", "PHP", "Kotlin", "Hapi.js")
            "full stack development" -> listOf("MERN Stack", "MEAN Stack", "LAMP Stack", "LEMP Stack", "JAMstack", "Serverless Architectures", "Docker", "Kubernetes", "GraphQL", "RESTful APIs", "Firebase", "AWS Lambda")

            // App-development
            "android development" -> listOf("Room DB", "Retrofit", "Hilt", "Kotlin", "Jetpack Compose", "Dagger", "LiveData", "ViewModel", "WorkManager", "Navigation Component", "Data Binding", "Paging", "Firebase", "ExoPlayer", "MVVM", "MVP", "MVI")
            "ios development" -> listOf("Swift", "Objective-C", "Xcode", "UIKit", "SwiftUI", "CocoaPods", "CoreData", "AVFoundation")
            "cross-platform development" -> listOf("Xamarin", "Flutter", "React Native", "Ionic", "PhoneGap", "Apache Cordova", "Unity", "Electron")
            "flutter" -> listOf("Dart", "Widgets", "Flutter SDK", "Flutter Flow", "Flutter Web", "Flutter Desktop", "Riverpod", "Bloc", "Provider")
            "react native" -> listOf("JavaScript", "TypeScript", "React", "React Native CLI", "Expo", "Redux", "MobX", "React Navigation", "NativeBase")

            // AI and machine learning
            "machine learning" -> listOf("Scikit-learn", "XGBoost", "LightGBM", "MLlib", "H2O.ai", "CatBoost", "RapidMiner", "Weka", "KNN", "SVM", "Naive Bayes", "Logistic Regression", "Random Forest", "AdaBoost", "Gradient Boosting Machines")
            "deep learning" -> listOf("TensorFlow", "PyTorch", "Keras", "MXNet", "Caffe", "Theano", "Chainer", "DL4J", "FastAI", "Hugging Face Transformers", "ONNX", "PaddlePaddle", "OpenCV", "TFLite", "Detectron2", "YOLO")
            "data science" -> listOf("Pandas", "NumPy", "SciPy", "Matplotlib", "Seaborn", "Jupyter Notebook", "Google Colab", "Scikit-learn", "StatsModels", "H2O.ai", "Tableau", "Power BI", "D3.js", "Plotly", "Apache Spark", "Hadoop", "Excel", "KNIME")

            else -> emptyList()
        }
    }

    private fun displayCategories(categories: List<String>) {
        for (category in categories) {
            val categoryView = LayoutInflater.from(this).inflate(R.layout.item_category, flexboxLayout, false)
            val button = categoryView.findViewById<Button>(R.id.btn_category)
            button.text = category
            flexboxLayout.addView(categoryView)
        }
    }
}