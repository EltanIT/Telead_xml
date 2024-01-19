package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemPopularCoursesHomeBinding
import com.example.telead_xml.databinding.ItemRecommendationsHomeBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.listener.CourseListener
import java.lang.IndexOutOfBoundsException

class RecommendationsHomeAdapter(val list: ArrayList<CoursesData>) : RecyclerView.Adapter<RecommendationsHomeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRecommendationsHomeBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommendations_home, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courses = list[position]
        holder.binding.name.text = courses.name

    }

    override fun getItemCount(): Int {
        return list.size
    }

}