# BookXpert

## User Authentication

This feature allows users to sign in using **Google Sign-In** via **Firebase Authentication**. The user's details are then stored locally in **Room Database** for offline access.

### Key Components

✅ **Google Sign-In Integration**  
- Uses **Firebase Authentication** to authenticate users via **Google Sign-In**.  
- Implements **GoogleSignInClient** and **FirebaseAuth** for secure authentication.  

✅ **Network Check**  
- Validates internet connectivity before attempting sign-in.  
- Shows a **toast message** if no internet is available.  

✅ **Error Handling**  
- Handles failed sign-in attempts gracefully with appropriate **error messages**.  
- Logs errors for **debugging purposes**.  

✅ **User Session Management**  
- Automatically **redirects authenticated users** to the **MainActivity**.  
- Stores user details in **Room Database** via `SignInViewModel`.    

## PDF Viewer

This feature enables users to **view PDFs** in the application. The PDF is **downloaded from a URL** and stored in the cache for offline access.

### Key Components

✅ **PDF Download & Caching**  
- Downloads the PDF from a remote **URL** using **OkHttpClient**.  
- Saves the file in the **cache directory** for quick offline access.  
- If the file is already cached, it loads the PDF without downloading it again.  

✅ **Network Check**  
- Validates **internet connectivity** before attempting to download the PDF.  
- Displays a **toast message** if there is no internet connection.  

✅ **LiveData & ViewModel**  
- Uses **LiveData** to observe the PDF file and **update the UI** dynamically.  
- Handles PDF download and caching logic in the `PdfViewerViewModel`.  

✅ **Error Handling**  
- Logs errors if the **download fails** or **file saving encounters an issue**.  
- Displays appropriate messages in case of failures.  

## Image Capture & Gallery Selection  

This feature enables users to **capture an image using the camera** or **select an image from the gallery** and display it in an `ImageView`.  

### Key Components  

✅ **Camera Integration**  
- Captures an image using the **device camera**.  
- Uses `ActivityResultContracts.TakePicture()` for camera capture.  
- Stores the captured image **securely** using `FileProvider`.  

✅ **Gallery Selection**  
- Allows users to **select an image from the gallery**.  
- Uses `Intent.ACTION_PICK` to access external media storage.  
- Handles the selected image and updates the UI dynamically.  

✅ **LiveData & ViewModel**  
- Uses `LiveData` in `ImageViewerViewModel` to **trigger UI updates**.  
- Separates **UI logic** from **business logic** using MVVM architecture.  

✅ **State Management**  
- Saves and restores image **state across configuration changes**.  
- Ensures the **selected image persists** when the device rotates or activity is recreated.  

# Api Integration & Notifications.

This Android application allows users to **view, update, and delete product information**. It retrieves product data from an **API**, stores it in a **Room Database**, and provides **push notifications using Firebase Cloud Messaging (FCM)** when a product is deleted.

### Key Components

✅ **Fetch Product Data from API** 
– Retrieves product details from [`https://api.restful-api.dev/objects`](https://api.restful-api.dev/objects) and saves them in **Room Database**.  

✅ **Offline Support with Room Database** 
– Enables users to access product data even without an internet connection.  

✅ **Edit & Update Product Details** 
– Users can update product information via an alert dialog.  

✅ **Delete Products with Notifications** 
– When a product is deleted, it is removed from the database, and a push notification is sent.  

✅ **Push Notifications (FCM)** 
– Sends real-time notifications when a product is deleted, allowing better user engagement.  

✅ **MVVM Architecture** 
– Uses a clean and scalable **Model-View-ViewModel** pattern for efficient data handling.  
