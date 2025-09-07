# 🎂 MyBirthdayApp

This is a small Android test project created as part of an assignment for one of the companies.

The goal was to build a simple and fun birthday screen for a baby, allowing the user to add a photo and share a celebratory screen.

The assets and UI requirements were provided by the customer via Figma.

---

<details>
  <summary>📋 Specifications from the customer</summary>


The emphasis in the project should be on demonstrating development capabilities
and practices. The product should look clean, follow UI design and function
properly. The code should be as readable as possible. And the most important thing is paying attention to details.

---

1. Users should be able to open the app and see the details screen. The UI for this
   screen is not too important. No need to invest time in it. Screen should include the
   following elements:  
   a. App title  
   b. Name  
   c. Birthday  
   d. Picture  
   e. “Show birthday screen” button (disabled while name & birthday are empty)

2. Users should be able to edit the elements. All elements should be persistent
   between app launches:  
   a. Name - text  
   b. Birthday - date picker  
   c. Picture - select from gallery or take a photo

3. Users should be able to see the birthday screen by pushing the “Show birthday
   screen” button. Birthday screen should follow the design and use the attached UI
   assets. For this screen the design is important. Pay attention to details and
   stick to original design. Font types can be ignored. Screen logic:  
   a. The birthday screen has 3 visual options. One should be chosen randomly
   every time you enter the screen.  
   b. Birthdays are shown by months until 1 year and then in years. The correct
   age should be displayed according to the baby's birthday.  
   c. If the name is too long for one line the title will occupy two lines (see screen
   design).  
   d. Pushing the close button (top left corner) will return to the previous screen.

4. Users should be able to change picture:  
   a. “Add camera” icon according to screen design.  
   b. Pushing the icon will prompt you to choose a picture from the gallery or take
   a photo.  
   c. After the picture was chosen it should replace the default picture.

5. Users should be able to share the screen:  
   a. Add the “Share the news” button.  
   b. Pushing the button will open the share menu.  
   c. The shared image should not include the share button, the camera icon or
   the close button.

</details>

---

## 🛠️ Technical Implementation (Developer’s choice)

- **Language:** Kotlin
- **Architecture:** Clean Architecture / MVI
- **UI:** Jetpack Compose
- **Asynchronous:** Coroutines / Flow
- **Dependency Injection:** Hilt
- **Database:** Room
- **Screenshot capturing:** [Capturable](https://github.com/PatilShreyas/Capturable)

---

## ✅ Conclusion

The project was completed, and all requirements were successfully implemented.