package com.example.androiddevelopment.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androiddevelopment.databinding.ActivityPdfViewerBinding
import com.example.androiddevelopment.presentation.ui.SignInActivity.Companion.isNetworkAvailable
import com.example.androiddevelopment.presentation.viewmodel.PdfViewerViewModel
import org.koin.android.ext.android.inject

class PdfViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfViewerBinding
    private val viewModel: PdfViewerViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeViewModel()
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        } else
            viewModel.loadPdf()

    }

    private fun observeViewModel() {
        viewModel.pdfFile.observe(this) { file ->
            file?.let {
                binding.pdfView.fromFile(it).load()
            }
        }
    }
}