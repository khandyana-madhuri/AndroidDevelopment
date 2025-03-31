package com.example.androiddevelopment.presentation.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddevelopment.R
import com.example.androiddevelopment.domain.utils.NotificationPreferences
import com.example.androiddevelopment.presentation.adapter.ProductAdapter
import com.example.androiddevelopment.data.model.ProductEntity
import com.example.androiddevelopment.databinding.ActivityProductBinding
import com.example.androiddevelopment.domain.repository.ProductRepository
import com.example.androiddevelopment.presentation.viewmodel.ProductViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private val productViewModel: ProductViewModel by inject()
    private lateinit var adapter: ProductAdapter
    private val repository: ProductRepository by inject()
    private var shouldFetchFromApi = true
    private lateinit var notificationPrefs: NotificationPreferences

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        notificationPrefs.setNotificationsEnabled(isGranted)
        binding.switchNotifications.isChecked = isGranted
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        shouldFetchFromApi = savedInstanceState?.getBoolean("SHOULD_FETCH", false) ?: true

        if (shouldFetchFromApi) {
            fetchProductsAndStore()
            shouldFetchFromApi = false
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(
            products = emptyList(),
            onEditClick = { product ->
                showUpdateDialog(product)
            },
            onDeleteClick = { product ->
                productViewModel.deleteProduct(product)
                showLocalNotification(product.name)
            }
        )
        recyclerView.adapter = adapter

        notificationPrefs = NotificationPreferences(this)
        setupNotificationToggle()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.products.collect { products ->
                    adapter.submitList(products)
                }
            }
        }

        productViewModel.errorMessage.observe(this) { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
    }

    private fun setupNotificationToggle() {
        binding.switchNotifications.isChecked = notificationPrefs.areNotificationsEnabled()

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    notificationPrefs.setNotificationsEnabled(true)
                }
            } else {
                notificationPrefs.setNotificationsEnabled(false)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("SHOULD_FETCH", shouldFetchFromApi)
    }

    private fun fetchProductsAndStore() {
        lifecycleScope.launch {
            try {
                if (productViewModel.products.value.isEmpty()) {
                    val apiResponse = repository.getProducts()
                    val products = apiResponse.map { ProductEntity.fromApiResponse(it) }
                    products.forEach {
                        productViewModel.insertProduct(it)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun showLocalNotification(productName: String) {

        if (!notificationPrefs.areNotificationsEnabled()) {
            return
        }

        val channelId = "delete_notification_channel"
        val notificationId = 1

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("The product '$productName' has been deleted")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Delete Notifications", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    private fun showUpdateDialog(product: ProductEntity) {
        val context = this
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32.dpToPx(context), 32.dpToPx(context), 32.dpToPx(context), 32.dpToPx(context))
        }

        val inputFields = mutableMapOf<String, EditText>()
        val originalValues = mutableMapOf<String, Any?>()

        val propertyMap = mapOf(
            "name" to "Name",
            "color" to "Color",
            "capacity" to "Capacity",
            "price" to "Price",
            "generation" to "Generation",
            "year" to "Year",
            "cpuModel" to "CPU Model",
            "hardDiskSize" to "Storage",
            "strapColour" to "Strap Color",
            "caseSize" to "Case Size",
            "screenSize" to "Screen Size",
            "description" to "Description"
        )

        propertyMap.forEach { (propertyName, displayName) ->
            val value = product.getPropertyValue(propertyName)
            if (value != null) {
                originalValues[propertyName] = value

                val label = TextView(context).apply {
                    text = displayName
                    setTextAppearance(android.R.style.TextAppearance_Medium)
                }
                layout.addView(label)

                val editText = EditText(context).apply {
                    setText(value.toString())
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = 16.dpToPx(context)
                    }
                }
                layout.addView(editText)
                inputFields[propertyName] = editText
            }
        }

        AlertDialog.Builder(context)
            .setTitle("Update ${product.name}")
            .setView(layout)
            .setPositiveButton("Update") { _, _ ->
                val updatedProduct = product.copy(
                    name = inputFields["name"]?.text?.toString() ?: product.name,
                    color = inputFields["color"]?.text?.toString() ?: product.color,
                    capacity = inputFields["capacity"]?.text?.toString() ?: product.capacity,
                    price = inputFields["price"]?.text?.toString()?.toDoubleOrNull() ?: product.price,
                    generation = inputFields["generation"]?.text?.toString() ?: product.generation,
                    year = inputFields["year"]?.text?.toString()?.toIntOrNull() ?: product.year,
                    cpuModel = inputFields["cpuModel"]?.text?.toString() ?: product.cpuModel,
                    hardDiskSize = inputFields["hardDiskSize"]?.text?.toString() ?: product.hardDiskSize,
                    strapColour = inputFields["strapColour"]?.text?.toString() ?: product.strapColour,
                    caseSize = inputFields["caseSize"]?.text?.toString() ?: product.caseSize,
                    screenSize = inputFields["screenSize"]?.text?.toString()?.toDoubleOrNull() ?: product.screenSize,
                    description = inputFields["description"]?.text?.toString() ?: product.description
                )
                productViewModel.updateProduct(updatedProduct)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun ProductEntity.getPropertyValue(propertyName: String): Any? {
        return when (propertyName) {
            "name" -> name
            "color" -> color
            "capacity" -> capacity
            "price" -> price
            "generation" -> generation
            "year" -> year
            "cpuModel" -> cpuModel
            "hardDiskSize" -> hardDiskSize
            "strapColour" -> strapColour
            "caseSize" -> caseSize
            "screenSize" -> screenSize
            "description" -> description
            else -> null
        }
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

}