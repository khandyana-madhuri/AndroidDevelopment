package com.example.androiddevelopment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.androiddevelopment.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FoodAdapter
    private lateinit var foodList : ArrayList<Food>
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        foodList = ArrayList()
        adapter = FoodAdapter(foodList)
        foodListItems()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun foodListItems() {
        foodList.add(Food(R.drawable.ic_launcher_background,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_foreground,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_background,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_foreground,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_background,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_foreground,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_background,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_foreground,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_background,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_foreground,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_background,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_foreground,"pizza"))
        foodList.add(Food(R.drawable.ic_launcher_background,"pizza"))
    }
}

