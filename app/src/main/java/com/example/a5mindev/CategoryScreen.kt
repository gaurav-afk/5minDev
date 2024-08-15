package com.example.a5mindev

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.a5mindev.databinding.ActivityCategoryScreenBinding
import com.google.android.flexbox.FlexboxLayout

class CategoryScreen : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryScreenBinding
    private lateinit var flexboxLayout: FlexboxLayout
    private lateinit var categories: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        flexboxLayout = binding.flexboxLayout
        val subTopic = intent.getStringExtra("subTopic") ?: "Unknown"
        categories = getCategoriesForSubtopic(subTopic.lowercase())
        binding.tvGoBack.text = subTopic
        binding.tvGoBack.setOnClickListener{
            finish()
        }
        displayCategories(categories)
    }



    private fun getCategoriesForSubtopic(subTopic: String): List<String> {
        return when (subTopic) {
            // Web development
            "front end development" -> listOf("HTML", "CSS", "JavaScript", "React", "Angular", "Vue.js", "Sass", "Less", "Bootstrap", "Tailwind CSS", "jQuery", "Webpack", "Babel", "TypeScript", "Foundation", "Materialize")
            "back end development" -> listOf("Node.js", "Express", "Django", "Flask", "Ruby on Rails", "Spring Boot", "ASP.NET", "Laravel", "Symfony", "Java", "C#", "Go", "PHP", "Kotlin", "Hapi.js")
            "full stack development" -> listOf("MERN Stack", "MEAN Stack", "LAMP Stack", "LEMP Stack", "JAMstack", "Serverless Architectures", "Docker", "Kubernetes", "GraphQL", "RESTful APIs", "Firebase", "AWS Lambda")

            // App development
            "android development" -> listOf("Room DB", "Retrofit", "Hilt", "Kotlin", "Jetpack Compose", "Dagger", "LiveData", "ViewModel", "WorkManager", "Navigation Component", "Data Binding", "Paging", "Firebase", "ExoPlayer", "MVVM", "MVP", "MVI")
            "ios development" -> listOf("Swift", "Objective-C", "Xcode", "UIKit", "SwiftUI", "CocoaPods", "CoreData", "AVFoundation")
            "cross-platform development" -> listOf("Xamarin", "Ionic", "PhoneGap", "Apache Cordova", "Unity", "Electron", "Kotlin Mutiplatform")
            "flutter" -> listOf("Dart", "Widgets", "Flutter SDK", "Flutter Flow", "Flutter Web", "Flutter Desktop", "Riverpod", "Bloc", "Provider")
            "react native" -> listOf("JavaScript", "TypeScript", "React", "React Native CLI", "Expo", "Redux", "MobX", "React Navigation", "NativeBase")

            // Game development
            "game design" -> listOf("Game Mechanics", "Level Design", "Storytelling and Narrative", "Game Balancing", "Player Experience Design", "Gameplay Systems", "Game Prototyping", "Game Economy and Monetization", "User Interface (UI) Design", "Game Design Documentation")
            "game programming" -> listOf("Game Logic and Algorithms", "Physics Simulation", "Artificial Intelligence in Games", "Graphics Programming", "Network Programming", "Performance Optimization", "Memory Management", "Multithreading and Concurrency", "Scripting Languages", "Game Engine Integration")
            "game art and animation" -> listOf("2D Art and Animation", "3D Modeling", "Texturing and Shading", "Rigging and Skinning", "Character Animation", "Environment Art", "Concept Art", "Visual Effects (VFX)", "Animation Principles", "Art Pipeline and Asset Management")
            "game audio" -> listOf("Sound Design", "Music Composition", "Voice Acting", "Sound Effects", "Audio Implementation", "Dialogue Systems", "Spatial Audio", "Audio Middleware (e.g., FMOD, Wwise)", "Audio Mixing and Mastering", "Interactive Audio")
            "game engines" -> listOf("Unity", "Unreal Engine", "Godot", "CryEngine", "GameMaker Studio", "Cocos2d", "Amazon Lumberyard", "Phaser", "Torque3D", "Custom Engine Development")
            "ui/ux design" -> listOf("User Interface Design", "User Experience Design", "Interaction Design", "Wireframing and Prototyping", "Usability Testing", "Visual Design Principles", "Responsive Design", "Accessibility in Games", "Player Feedback Integration", "HUD Design")
            "game testing and debugging" -> listOf("Quality Assurance (QA)", "Bug Tracking and Reporting", "Automated Testing", "Playtesting", "Performance Testing", "Compatibility Testing", "Usability Testing", "Regression Testing", "Debugging Tools and Techniques", "Test Case Design")
            "vr/ar development" -> listOf("Virtual Reality (VR) Development", "Augmented Reality (AR) Development", "Mixed Reality (MR) Development", "VR/AR Interaction Design", "3D Modeling for VR/AR", "Spatial Computing", "VR/AR Hardware Integration", "VR/AR Performance Optimization", "Immersive Experience Design", "VR/AR Prototyping")


            // AI and machine learning
            "machine learning" -> listOf("Supervised Learning", "Unsupervised Learning", "Reinforcement Learning", "Decision Trees", "Random Forests", "Support Vector Machines", "K-Nearest Neighbors")
            "deep learning" -> listOf("Neural Networks", "Convolutional Neural Networks (CNNs)", "Recurrent Neural Networks (RNNs)", "Long Short-Term Memory Networks (LSTMs)", "Generative Adversarial Networks (GANs)", "Autoencoders", "Deep Learning Frameworks")
            "natural language processing (nlp)" -> listOf("Text Analysis and Classification", "Named Entity Recognition (NER)", "Machine Translation", "Sentiment Analysis", "Speech Recognition", "Text Generation", "Part-of-Speech Tagging", "Language Models")
            "computer vision" -> listOf("Image Classification", "Object Detection", "Image Segmentation", "Feature Extraction", "Image Generation", "Optical Character Recognition (OCR)", "Face Recognition")
            "data preparation and processing" -> listOf("Data Collection and Cleaning", "Feature Engineering", "Data Augmentation", "Data Transformation", "Normalization and Scaling", "Handling Missing Data", "Feature Selection")
            "model evaluation and tuning" -> listOf("Cross-Validation", "Hyperparameter Tuning", "Performance Metrics", "Confusion Matrix", "ROC Curve and AUC", "Grid Search and Random Search")
            "generative models" -> listOf("Generative Adversarial Networks (GANs)", "Variational Autoencoders (VAEs)", "Flow-Based Models", "Diffusion Models", "Image Synthesis", "Data Augmentation")
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