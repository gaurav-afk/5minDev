# 5-Min Dev App

Welcome to the 5-Min Dev app! This Android application provides quick and insightful summaries across various topics like Web Development, App Development, Game Development, and AI/Machine Learning. The app generates detailed, easy-to-understand summaries for developers and enthusiasts looking to learn about different subtopics within these domains.

## Features
Dynamic Content Generation: Fetches detailed summaries on various topics using the Gemini AI API.
Topic Selection: Choose from categories such as Web Development, App Development, Game Development, and AI/ML.
Five Summaries: Generates five unique summaries for the selected topic and subtopic.
Summary Display: View summaries with a title, description, key points, and a conclusion.
Offline Access: Previously generated summaries are saved locally using Room Database and can be accessed offline.
Edge-to-Edge UI: Modern UI design with edge-to-edge support for a seamless user experience.

## Screenshots

Getting Started
Prerequisites
Kotlin 1.7 or later
An active API key for the Gemini AI API

Installation
1. Clone the Repository:

```
git clone https://github.com/your-username/5mindev-app.git
cd 5mindev-app
```

2. Open in Android Studio:

    Open Android Studio, and select the 5mindev-app folder to open the project.

3. Add API Key:

    In local.properties, add your API key for the Gemini AI API:

  ``API_KEY=your_api_key_here``

4. Build and Run:

    Sync the project with Gradle files, build the project, and run it on an emulator or physical device.

## Project Structure

1. Activities:

  a. TopicScreen: Main screen where users select a development topic.
  
  b. SubTopicScreen: Screen for selecting subtopics and categories under the chosen topic.
  
  c. FiveShorts: Displays five summaries generated for the selected topic, subtopic, and category.
  
  d. ShortsDetail: Shows detailed information about a specific summary.

2. Data Layer:

  a. Shorts: Data class representing a single summary.
  
  b. ShortsDatabase: Room database for storing and retrieving summaries.
  
  c. ShortsDao: Data Access Object (DAO) for database operations.

3. Networking:
  a. Uses GenerativeModel class to fetch summaries from the Gemini AI API.

## Key Dependencies

a. Kotlin Coroutines: For asynchronous programming.
 
b. Room Database: For local storage of summaries.
  
c. Gemini AI API Client: For generating content.
  
d. Shimmer: For loading animations.
  
e. ConstraintLayout: For modern UI layouts.

## Usage

1. Select a Topic: Choose a topic from the main screen (e.g., Web Development, App Development).
   
2. View Summaries: The app will generate five unique summaries. You can tap on any summary to view details.
  
3. Access Offline: Previously generated summaries are saved locally and can be viewed without an internet connection.
  
##  Contributing

Contributions to the 5-Min Dev are greatly appreciated. Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Commit your changes (git commit -m 'Add some feature').
4. Push to the branch (git push origin feature-branch).
5. Create a pull request.

##  License
This project is licensed under the MIT License. See the LICENSE file for details.

##  Contact
For any questions or feedback, feel free to contact me at rawat.gaurav1080@gmail.com
