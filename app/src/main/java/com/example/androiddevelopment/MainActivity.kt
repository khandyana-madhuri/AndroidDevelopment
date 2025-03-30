package com.example.androiddevelopment

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androiddevelopment.databinding.ActivityMainBinding
import com.example.androiddevelopment.presentation.ui.ImageViewerActivity
import com.example.androiddevelopment.presentation.ui.PdfViewerActivity
import com.example.androiddevelopment.presentation.ui.ProductActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.pdfViewer.setOnClickListener {
            startActivity(Intent(this, PdfViewerActivity::class.java))
        }

        binding.imgViewer.setOnClickListener {
            startActivity(Intent(this, ImageViewerActivity::class.java))
        }

        binding.userData.setOnClickListener {
            startActivity(Intent(this, ProductActivity::class.java))
        }
    }
}

