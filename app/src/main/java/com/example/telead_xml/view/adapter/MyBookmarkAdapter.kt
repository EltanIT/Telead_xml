package com.example.telead_xml.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFullCoursesBinding
import com.example.telead_xml.databinding.ItemPopularCoursesHomeBinding
import com.example.telead_xml.domen.objects.CategoryData
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.listener.BookmarkListener

class MyBookmarkAdapter(val list: ArrayList<CoursesData>, val listener: BookmarkListener) : RecyclerView.Adapter<MyBookmarkAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFullCoursesBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_full_courses, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courses = list[position]
        holder.binding.name.text = courses.name
        holder.binding.category.text = courses.benefits[position].name
        holder.binding.rating.text = courses.rating.toString()
        holder.binding.std.text = courses.countStudents.toString()
        holder.binding.price.text = courses.price.toString()

        holder.binding.favorite.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_favorite_active))

        holder.itemView.setOnClickListener {
            listener.click(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}