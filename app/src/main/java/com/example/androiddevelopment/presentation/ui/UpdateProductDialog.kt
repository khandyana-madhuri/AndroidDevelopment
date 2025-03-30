package com.example.androiddevelopment.presentation.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.androiddevelopment.data.ProductEntity
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties


class UpdateProductDialog(
    private val product: ProductEntity,
    private val onUpdate: (ProductEntity) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val inputFields = mutableMapOf<String, EditText>()

        product::class.memberProperties.forEach { property ->
            val value = (property as KProperty1<ProductEntity, Any?>).get(product)?.toString() ?: ""
            if (property.name != "id") { // Exclude 'id' field
                val editText = EditText(context).apply {
                    hint = property.name.capitalize()
                    setText(value)
                }
                layout.addView(editText)
                inputFields[property.name] = editText
            }
        }

        return AlertDialog.Builder(context)
            .setTitle("Update Product")
            .setView(layout)
            .setPositiveButton("Update") { _, _ ->
                val updatedProduct = product.copy(
                    name = inputFields["name"]?.text.toString(),
                    color = inputFields["color"]?.text.toString(),
                    capacity = inputFields["capacity"]?.text.toString(),
                    price = inputFields["price"]?.text.toString().toDoubleOrNull(),
                    generation = inputFields["generation"]?.text.toString(),
                    year = inputFields["year"]?.text.toString().toIntOrNull(),
                    cpuModel = inputFields["cpuModel"]?.text.toString(),
                    hardDiskSize = inputFields["hardDiskSize"]?.text.toString(),
                    strapColour = inputFields["strapColour"]?.text.toString(),
                    caseSize = inputFields["caseSize"]?.text.toString(),
                    screenSize = inputFields["screenSize"]?.text.toString().toDoubleOrNull(),
                    description = inputFields["description"]?.text.toString()
                )
                onUpdate(updatedProduct)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
