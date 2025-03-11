HLine - Emergency Services App
Welcome to HLine! This Android app provides a quick and efficient way to connect with emergency services (ambulance, police, fire department) with just a tap. Whether you're at home or traveling, HLine ensures that you can reach emergency services instantly when every second counts.

Features
Instant Emergency Calling: Quickly call local emergency services with a simple tap.
Auto-Dial for Quick Access: Double-tap to automatically dial the emergency number.
Easy-to-Use Interface: Simple design for quick navigation and immediate access to emergency numbers.
Local Emergency Numbers: Automatically detects and uses local emergency numbers based on your region.
Flight Mode Compatibility: Supports calling emergency numbers even while in flight mode (dependent on carrier and region).
Screenshots
<img src="https://github.com/user-attachments/assets/ca09d2ee-f2fb-48d5-88b4-9e417a51a504" width="200" height="400"> <img src="https://github.com/user-attachments/assets/10a5a3a9-df87-4f70-b947-0932b424bf82" width="200" height="400"> <img src="https://github.com/user-attachments/assets/ae43e78b-6ee0-47d5-847f-186ed8bc20e3" width="200" height="400">
Getting Started
Prerequisites
Android Studio: Version 4.0 or later.
Kotlin: Version 1.7 or later.
Installation
Clone the Repository:
bash
Copy
Edit
git clone https://github.com/your-username/hline-app.git
cd hline-app
Open in Android Studio:

Open Android Studio, and select the hline-app folder to open the project.

Add Permissions:

In your AndroidManifest.xml, ensure the following permissions are granted:

xml
Copy
Edit
<uses-permission android:name="android.permission.CALL_PHONE"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
Build and Run:

Sync the project with Gradle files, build the project, and run it on an emulator or physical device.

Project Structure
Activities:

a. MainActivity: The main screen where users can select emergency services.

b. CallScreen: Screen for placing calls to the selected emergency number.

c. Settings: Settings screen for configuring user preferences.

Data Layer:

a. EmergencyNumbers: Data class representing emergency numbers for different regions.

b. EmergencyDatabase: Room database for storing local emergency numbers.

Networking:

a. Uses APIs to get local emergency numbers based on the user's location.

Key Dependencies
Kotlin Coroutines: For asynchronous programming and network calls.
Room Database: For storing local emergency numbers.
Google Play Services: For location-based features.
Shimmer: For loading animations.
Usage
Select Emergency Service: Tap to select the emergency service (ambulance, police, fire).
Call: Tap the emergency service to call or double-tap to auto-dial.
Access Offline: Even if you're offline, the app will help with emergency calling if the emergency number is saved locally.
Contributing
Contributions to the HLine app are greatly appreciated! Please follow these steps to contribute:

Fork the repository.
Create a new branch:
bash
Copy
Edit
git checkout -b feature-branch
Commit your changes:
bash
Copy
Edit
git commit -m 'Add some feature'
Push to the branch:
bash
Copy
Edit
git push origin feature-branch
Create a pull request.
License
This project is licensed under the MIT License. See the LICENSE file for details.

Contact
For any questions or feedback, feel free to contact me at towerofapp@gmail.com

