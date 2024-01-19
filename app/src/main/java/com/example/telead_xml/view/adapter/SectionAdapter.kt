package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemSectionBinding
import com.example.telead_xml.domen.objects.LessonData
import com.example.telead_xml.domen.objects.SectionData
import com.example.telead_xml.view.listener.SectionListener

class SectionAdapter(val sectionsList: ArrayList<SectionData>, val listener: SectionListener, val statePermission: Boolean) : RecyclerView.Adapter<SectionAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSectionBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = sectionsList[position]

        holder.binding.name.text = lesson.name
        var duration = 0
        for (i in lesson.sections){
            duration += i.durationInMinutes?:0
        }
        val number = if (holder.adapterPosition<10){
            "0${holder.adapterPosition+1}"
        }else{
            "${holder.adapterPosition+1}"
        }
        holder.binding.number.text = number
        holder.binding.duration.text = "$duration Мин"

        val play = object : SectionListener{
            override fun play(id: String) {
                listener.play(id)
            }
        }

        if (!statePermission){
            if (holder.adapterPosition>0){
                holder.binding.sectionsRv.adapter = LessonsAdapter(lesson.sections, play, false)
            }else{
                holder.binding.sectionsRv.adapter = LessonsAdapter(lesson.sections, play, true)
            }
        }else{
            holder.binding.sectionsRv.adapter = LessonsAdapter(lesson.sections, play, true)
        }

    }

    override fun getItemCount(): Int {
        return sectionsList.size
    }

}