package com.example.androiddevelopment

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.parceler.Parcels

class FirstActivity : AppCompatActivity() {
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textView = findViewById(R.id.textView)

        // passDataBetweenActivities
        /*val data = intent.getStringExtra("name")
        textView.text = data*/

        // passDataBetweenActivitiesUsingSerializable
        /*val data = intent.getSerializableExtra("person") as User
        textView.text = data.name*/

        // passDataBetweenActivitiesUsingParcelizeAndParcelable
        /*val data = intent.getParcelableExtra<User>("person")
        textView.text = data?.name*/

        // sendMultipleDataUSingBundles
        val data = intent.extras
        textView.text = data?.getString("name") + data?.getInt("age") + data?.getString("gender")
    }
}