package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemSectionBinding
import com.example.telead_xml.domen.objects.SectionsData
import com.example.telead_xml.view.listener.SectionListener

class SectionsAdapter(val list: ArrayList<SectionsData>, val listener: SectionListener, val permission: Boolean) : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {
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
        val section = list[position]

        holder.binding.name.text = section.name
        val number = if (holder.adapterPosition<10){
            "0${holder.adapterPosition}"
        }else{
            "${holder.adapterPosition}"
        }
        holder.binding.id.text = number
        holder.binding.time.text = "${section.durationInMinutes.toString()} Мин"

        if (permission){
            holder.binding.play.setOnClickListener {
                listener.play(section.id!!)
            }
        }else{
            holder.binding.play.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_castle))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}