package com.example.androiddevelopment

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolBar = findViewById<Toolbar>(R.id.toolBar)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        setSupportActionBar(toolBar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FirstFragment())
            .commit()

        tabLayout.addTab(tabLayout.newTab().setText("Chats"))
        tabLayout.addTab(tabLayout.newTab().setText("Calls"))
        tabLayout.addTab(tabLayout.newTab().setText("Status"))

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val fragment = when(tab?.position) {
                    0 -> FirstFragment()
                    1 -> SecondFragment()
                    2 -> ThirdFragment()
                    else -> FirstFragment()
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean {

        menuInflater.inflate(R.menu.toolbar_menu, menu)

        // change the text color of the menu.
        for(i in 0 until (menu?.size() ?: 0)) {
            val menuItem = menu?.getItem(i)
            val spannable = SpannableString(menuItem?.title)
            spannable.setSpan(ForegroundColorSpan(Color.WHITE), 0, spannable.length, 0)
            menuItem?.title = spannable
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        return when(item.itemId) {
            R.id.settings -> {
                Toast.makeText(this, "Selected Settings", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.profile -> {
                Toast.makeText(this, "Selected Profile", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

