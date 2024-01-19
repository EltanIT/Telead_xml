package com.example.telead_xml.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFullCoursesBinding
import com.example.telead_xml.domen.objects.CoursesData
import com.example.telead_xml.view.listener.BookmarkListener

class MyBookmarkAdapter(val list: ArrayList<CoursesData>, val listener: BookmarkListener) : RecyclerView.Adapter<MyBookmarkAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFullCoursesBinding.bind(itemView)
        val resources = itemView.resources
    }

    private val bookmarks = ArrayList<CoursesData>()
    private val filteringBookmarks = ArrayList<CoursesData>()
    init {
        bookmarks.addAll(list)
        filteringBookmarks.addAll(list)
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
        val course = filteringBookmarks[position]
        holder.binding.name.text = course.name
        if (course.benefits.size > 0){
            holder.binding.category.text = course.benefits[position].name
        }
        holder.binding.rating.text = course.rating.toString()
        holder.binding.std.text = course.countStudents.toString()
        holder.binding.price.text = course.price.toString()

        holder.binding.favorite.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_favorite_active))

        holder.itemView.setOnClickListener {
            listener.click(course.id?:"")
        }
        holder.binding.favorite.setOnClickListener {
            listener.remove(course.id?:"")
        }

    }

    override fun getItemCount(): Int {
        return filteringBookmarks.size
    }

    fun remove(id: String) {
        filteringBookmarks.removeIf {
            it.id?.equals(id) == true
        }
        notifyDataSetChanged()
    }

    fun filter(name: String?) {
        filteringBookmarks.clear()
        if (name.equals("Все")){
            filteringBookmarks.addAll(bookmarks)
        }else{
            for (item in bookmarks){
                for (category in item.benefits){
                    if (category.name?.equals(name) == true){
                        filteringBookmarks.add(item)
                    }
                }
            }
        }
        notifyDataSetChanged()
    }

}