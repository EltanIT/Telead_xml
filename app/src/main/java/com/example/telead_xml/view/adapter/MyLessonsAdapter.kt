package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemLessonBinding
import com.example.telead_xml.domen.objects.LessonData
import com.example.telead_xml.view.listener.SectionListener

class MyLessonsAdapter(val list: ArrayList<LessonData>, val listener: SectionListener) : RecyclerView.Adapter<MyLessonsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemLessonBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = list[position]

        holder.binding.name.text = lesson.name
        var duration = 0
        for (i in lesson.sections){
            duration += i.durationInMinutes!!
        }
        val number = if (holder.adapterPosition<10){
            "0${holder.adapterPosition}"
        }else{
            "${holder.adapterPosition}"
        }
        holder.binding.number.text = number
        holder.binding.duration.text = "$duration Мин"


        val play = object : SectionListener{
            override fun play(videoUrl: String) {
                listener.play(videoUrl)
            }
        }

        holder.binding.sectionsRv.adapter = SectionsAdapter(lesson.sections, play, true)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}