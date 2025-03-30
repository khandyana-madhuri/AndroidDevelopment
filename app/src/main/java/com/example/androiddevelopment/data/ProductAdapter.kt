package com.example.androiddevelopment.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddevelopment.R

class ProductAdapter(
    private var products: List<ProductEntity>,
    private val onEditClick: (ProductEntity) -> Unit,
    private val onDeleteClick: (ProductEntity) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun submitList(newList: List<ProductEntity>) {
        products = newList
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val layoutDynamicFields: LinearLayout = itemView.findViewById(R.id.layoutDynamicFields)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        init {

            btnEdit.setOnClickListener {
                onEditClick(products[adapterPosition])
            }

            btnDelete.setOnClickListener {
                onDeleteClick(products[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        val context = holder.itemView.context

        holder.tvName.text = product.name

        if (holder.layoutDynamicFields.childCount > 2) {
            holder.layoutDynamicFields.removeViews(1, holder.layoutDynamicFields.childCount - 2)
        }

        fun addFieldIfPresent(label: String, value: Any?) {
            value?.let {
                TextView(context).apply {
                    text = "$label: $it"
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 4.dpToPx(context), 0, 0)
                    }
                }.also { textView ->
                    holder.layoutDynamicFields.addView(textView, holder.layoutDynamicFields.childCount - 1)
                }
            }
        }

        with(product) {
            addFieldIfPresent("Color", color)
            addFieldIfPresent("Capacity", capacity)
            addFieldIfPresent("Price", price?.let { "$$it" })
            addFieldIfPresent("Generation", generation)
            addFieldIfPresent("Year", year)
            addFieldIfPresent("CPU Model", cpuModel)
            addFieldIfPresent("Storage", hardDiskSize)
            addFieldIfPresent("Strap Color", strapColour)
            addFieldIfPresent("Case Size", caseSize)
            addFieldIfPresent("Screen Size", screenSize)
            addFieldIfPresent("Description", description)
        }
    }

    private fun Int.dpToPx(context: Context): Int =
        (this * context.resources.displayMetrics.density).toInt()

    override fun getItemCount(): Int = products.size
}