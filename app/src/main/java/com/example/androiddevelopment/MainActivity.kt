package com.example.androiddevelopment

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androiddevelopment.presentation.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var userId: TextView
    private lateinit var userTitle: TextView
    private lateinit var userBody: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userId = findViewById(R.id.userId)
        userTitle = findViewById(R.id.userTitle)
        userBody = findViewById(R.id.userBody)


        CoroutineScope(Dispatchers.Main).launch {
            val user = mainViewModel.fetchUser()
            user?.let {
                userId.text = it.id.toString()
                userTitle.text = it.username
                userBody.text = it.email
            }
        }


    }
}

