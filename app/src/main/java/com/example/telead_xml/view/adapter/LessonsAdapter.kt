package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemLessonBinding
import com.example.telead_xml.domen.objects.LessonData
import com.example.telead_xml.view.listener.SectionListener

class LessonsAdapter(val list: ArrayList<LessonData>, val listener: SectionListener, val permission: Boolean) : RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {
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
        val number = if (holder.adapterPosition<10){
            "0${holder.adapterPosition+1}"
        }else{
            "${holder.adapterPosition+1}"
        }
        holder.binding.id.text = number
        holder.binding.time.text = "${lesson.durationInMinutes.toString()} Мин"

        if (permission){
            holder.binding.play.setOnClickListener {
                listener.play(lesson.id?:"")
            }
        }else{
            holder.binding.play.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_castle))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}