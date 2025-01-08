package com.example.androiddevelopment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    private lateinit var primaryTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        primaryTextView = findViewById(R.id.primaryTextView)
    }

    @SuppressLint("SetTextI18n")
    fun clickedOne(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "1"
    }

    // handle the button 2 and append the 2 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedTwo(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "2"
    }

    // handle the button 3 and append the 3 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedThree(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "3"
    }

    // handle the button 4 and append the 4 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedFour(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "4"
    }

    // handle the button 5 and append the 5 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedFive(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "5"
    }

    // handle the button 6 and append the 6 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedSix(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "6"
    }

    // handle the button 7 and append the 7 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedSeven(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "7"
    }

    // handle the button 8 and append the 8 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedEight(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "8"
    }

    // handle the button 9 and append the 9 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedNine(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "9"
    }

    // handle the button 0 and append the 0 to the end of the TextView
    @SuppressLint("SetTextI18n")
    fun clickedZero(view: View?) {
        primaryTextView.text = primaryTextView.text.toString() + "0"
    }
}


