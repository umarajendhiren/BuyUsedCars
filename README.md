# BuyUsedCars
### Firebase Project


**The app has following packages:**

* **adapter**:Contain Recyclerviewadapters for views and binding adapters for databinding.
* **di**:Contain Dependency providing classes using Dagger2.
* **model:** Contain basic POJO classes.
* **repository:** Contain repository class handles data operation and provide clean API to the Viewmodel.
* **ui**:Contain View classes.this is responsible for drawing data to the screen.   
 * **utilities**:Contain utility classes.
 * **viewmodel**:Contain viewmodel for view.this is responsible for holding and processing all the data needed for UI.
 
**Libraries Used:**

* **Data Binding**:bind observable data to UI elements and to avoid boilerplate code like findViewById().
* **ViewModel**: Store UI-related data that isn't destroyed on app rotations.
* **LiveData**:Build data objects that notify views when the underlying database changes.
* **google authentication**:To store user data under particular user.
* **firebase authentication**:get the identity of app user To make personalized app.
* **Lifecycles extention**: Create a UI that automatically responds to lifecycle events.
* **dagger**:DI library to avoid boilerplate code.
 * **Firebase Realtime Database**:NoSQL cloud database used as backend To Store meta data of image.
* **Firebase Cloud Firestore**: NoSQL cloud data model used as backend  to store data in documents and make queries to retrieve individual, specific documents.
* **Firebase storage**:To store images in cloud.
* **volley**:to parse a  json object .
* **picasso**:to load image from internet

![Resized1](https://user-images.githubusercontent.com/28978071/85892562-28d67000-b7bf-11ea-95df-9ea5d5a6bd64.gif)


