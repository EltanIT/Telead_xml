package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemCategoriesHomeBinding
import com.example.telead_xml.domen.objects.CategoryData
import com.example.telead_xml.view.adapter.CategoriesHomeAdapter.ViewHolder

class CategoriesHomeAdapter(val list: ArrayList<CategoryData>) : RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCategoriesHomeBinding.bind(itemView)
    }

    private var selectedItem = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categories_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try{
            val category = list[position]
            holder.binding.name.text = category.name

            holder.binding.name.isSelected = (position == selectedItem)

            holder.binding.name.setOnClickListener {
                val oldItem = selectedItem
                selectedItem = holder.adapterPosition
                notifyItemChanged(oldItem)
                notifyItemChanged(selectedItem)
            }
        }catch (e: Exception){

        }

    }

    override fun getItemCount(): Int {
        return 3
    }

}