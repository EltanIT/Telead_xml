package com.example.telead_xml.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFullCoursesBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.listener.CourseListener
import java.lang.IndexOutOfBoundsException

class PopularFullCoursesAdapter(val list: ArrayList<CoursesData>, val listener: CourseListener) : RecyclerView.Adapter<PopularFullCoursesAdapter.ViewHolder>() {
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


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courses = list[position]
        holder.binding.name.text = courses.name
        try{
            holder.binding.category.text = courses.benefits[0].name
        }catch (_: IndexOutOfBoundsException){

        }
        holder.binding.rating.text = courses.rating.toString()
        holder.binding.std.text = courses.countStudents.toString()
        holder.binding.price.text = courses.price.toString()
        holder.binding.priceFull.text = courses.price.toString()
        holder.binding.image.setImageURI(Uri.parse(courses.imageUrl))

        holder.itemView.setOnClickListener {
            listener.click(courses.id?:"")
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}