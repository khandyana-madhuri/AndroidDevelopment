package com.example.androiddevelopment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.parcelize.Parcelize
import org.parceler.Parcel
import org.parceler.Parcels

/*data class User(
    val name: String
) :Serializable*/

@Parcelize
data class User(
    val name: String
) : Parcelable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // implicit intents
        val button : Button = findViewById(R.id.webSiteBtn)
        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.example.com")
            startActivity(intent)
        }
    }

    fun navigateToAnotherActivityUsingIntents(view: View) {
        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
    }

    fun passDataBetweenActivities(view: View) {
        val intent = Intent(this, FirstActivity::class.java)
        intent.putExtra("name", "Madhuri")
        startActivity(intent)
    }

    /*fun passObjectsBetweenActivitiesUsingSerializable(view: View) {
        val intent = Intent(this, FirstActivity::class.java)
        intent.putExtra("person", User("Madhuri"))
        startActivity(intent)
    }*/

    fun passDataBetweenActivitiesUsingParcelizeAndParcelable(view: View) {
        val intent = Intent(this, FirstActivity::class.java)
        intent.putExtra("person", User("Madhuri"))
        startActivity(intent)
    }

    fun sendMultipleDataUSingBundles(view: View) {
        // SendingActivity.kt
        val intent = Intent(this, FirstActivity::class.java)
        val bundle = Bundle()

        bundle.putString("name", "Madhuri")
        bundle.putInt("age", 24)
        bundle.putString("gender", "female")

        intent.putExtras(bundle)
        startActivity(intent)

    }


}

