package com.example.androiddevelopment.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androiddevelopment.databinding.ActivityImageViewerBinding
import com.example.androiddevelopment.presentation.viewmodel.ImageViewerViewModel
import org.koin.android.ext.android.inject
import java.io.File

class ImageViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageViewerBinding
    private val viewModel: ImageViewerViewModel by inject()

    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private lateinit var imageBitmap: Uri
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityImageViewerBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(savedInstanceState!= null){
            imageBitmap = savedInstanceState.getString("image_bitmap")?.let { uriString -> Uri.parse(uriString) }!!
            imageBitmap.let { uri -> binding.imageView.setImageURI(uri) }

            imageUri = savedInstanceState.getString("image_uri")?.let { uriString -> Uri.parse(uriString) }
            imageUri?.let { uri -> binding.imageView.setImageURI(uri) }
        } else {
            binding.imageView.setImageDrawable(null)
        }


        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if(result) {
                binding.imageView.setImageURI(null)
                binding.imageView.setImageURI(imageBitmap)
            }
        }

        imageBitmap = createImageUri()

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                imageUri = result.data?.data
                binding.imageView.setImageURI(imageUri)
            }
        }

        viewModel.openCamera.observe(this) {
            cameraLauncher.launch(imageBitmap)
        }

        viewModel.openGallery.observe(this) {
            openGallery()
        }
    }

    private fun createImageUri() : Uri {
        val image = File(filesDir, "camera_photos.png")
        return FileProvider.getUriForFile(
            this,
            "com.example.androiddevelopment.presentation.ui.FileProvider",
            image
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("image_bitmap", imageBitmap.toString())

        imageUri?.let {
            outState.putString("image_uri", it.toString())
        }
    }

    private fun openGallery() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(pickPhotoIntent)
    }
}
