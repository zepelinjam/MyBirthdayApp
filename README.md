🎂 **MyBirthdayApp – Android Test Project**

This is a test Android project created as part of an assignment. The goal was to build a simple and fun birthday screen for a baby, allowing the user to add a photo and share a celebratory screen.

📱 **Overview**
The app displays a birthday screen with the following features:

Replace the default image with one from the camera or gallery.

Display a custom baby birthday layout.

Share the screen (excluding UI buttons like "Share", "Camera", and "Close").

✅ **Key Features**
1. Camera Icon Functionality
Added a camera icon as per UI design.

Clicking it opens a chooser to:

Select an image from the gallery.

Capture a photo using the camera.

Replaces the default image with the chosen one.

2. **Share Functionality**
Added a "Share the news" button.

Clicking it opens the native share menu.

Shared image excludes:

Share button

Camera icon

Close button

🧰 **Tech Stack**
Kotlin

Jetpack Compose

Room

Clean Architecture (with MVI pattern)

Hilt for dependency injection

[dev.shreyaspatil:capturable](https://github.com/PatilShreyas/Capturable) for composable screenshot capturing

🗂️ **Project Structure**
The project follows Clean Architecture principles:

Presentation (Jetpack Compose UI + MVI)

Domain (Use cases, models)

Data (Room DB, repository implementation)
