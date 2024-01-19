package com.example.telead_xml.view.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFullMentorCoursesBinding
import com.example.telead_xml.databinding.ItemPopularCoursesHomeBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.listener.CourseListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.coroutines.CoroutineContext


class MentorCoursesAdapter(val list: ArrayList<CoursesData>, val listener: CourseListener) : RecyclerView.Adapter<MentorCoursesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFullMentorCoursesBinding.bind(itemView)
        val resources = itemView.resources
        val context = itemView.context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_full_mentor_courses, parent, false)
        return ViewHolder(view)
    }


 override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val courses = list[position]
        holder.binding.name.text = courses.name
        holder.binding.favorite.isSelected = false
        try{
            holder.binding.category.text = courses.benefits[0].name
        }catch (e: IndexOutOfBoundsException){

        }
        holder.binding.rating.text = courses.rating.toString()
        holder.binding.std.text = courses.countStudents.toString()
        holder.binding.price.text = courses.price.toString()
        Picasso.with(holder.context)
            .load(courses.imageUrl)
            .into(holder.binding.image)

        holder.itemView.setOnClickListener {
            listener.click(courses.id)
        }
        holder.binding.favorite.setOnClickListener {
            val stateSelected = holder.binding.favorite.isSelected
            if (stateSelected){
                holder.binding.favorite.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_favourites))
                listener.removeBookmark(courses.id)
            }else{
                holder.binding.favorite.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_favorite_active))
                listener.addBookmark(courses.id)
            }
            holder.binding.favorite.isSelected = !stateSelected
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



}