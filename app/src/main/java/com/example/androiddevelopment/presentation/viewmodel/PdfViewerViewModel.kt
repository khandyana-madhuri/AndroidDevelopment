package com.example.androiddevelopment.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfViewerViewModel(application: Application) : AndroidViewModel(application) {

    private val _pdfFile = MutableLiveData<File?>()
    val pdfFile: LiveData<File?> get() = _pdfFile

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val pdfUrl =
        "https://fssservices.bookxpert.co/GeneratedPDF/Companies/nadc/2024-2025/BalanceSheet.pdf"

    private val cacheFile: File
        get() = File(getApplication<Application>().cacheDir, "cached_pdf.pdf")

    fun loadPdf() {
        if (cacheFile.exists()) {
            _pdfFile.postValue(cacheFile)
        } else {
            downloadPdf()
        }
    }

    private fun downloadPdf() {
        _loading.postValue(true)

        val client = OkHttpClient()
        val request = Request.Builder().url(pdfUrl).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("PdfViewerViewModel", "PDF Download Failed: ${e.message}")
                _loading.postValue(false)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { body ->
                    try {
                        FileOutputStream(cacheFile).use { it.write(body.bytes()) }
                        _pdfFile.postValue(cacheFile)
                    } catch (e: IOException) {
                        Log.e("PdfViewerViewModel", "Error saving PDF: ${e.message}")
                    }
                }
                _loading.postValue(false)
            }
        })
    }
}
