package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemPopularCoursesCategoriesHomeBinding
import com.example.telead_xml.domen.objects.CategoryData

class PopularCoursesCategoriesHomeAdapter(val list: ArrayList<CategoryData>) : RecyclerView.Adapter<PopularCoursesCategoriesHomeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPopularCoursesCategoriesHomeBinding.bind(itemView)
        val resources = itemView.resources
    }
    private var selectedItem = 1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_courses_categories_home, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]
        holder.binding.name.text = category.name


        if (position == selectedItem){
            holder.binding.name.isSelected = true
            holder.binding.name.setTextColor(holder.resources.getColor(R.color.category_btn_text_selected_color))
        }
        else{
            holder.binding.name.isSelected = false
            holder.binding.name.setTextColor(holder.resources.getColor(R.color.category_btn_text_unselected_color))
        }

        holder.binding.name.setOnClickListener {
            val oldItem = selectedItem
            selectedItem = holder.adapterPosition
            notifyItemChanged(oldItem)
            notifyItemChanged(selectedItem)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}