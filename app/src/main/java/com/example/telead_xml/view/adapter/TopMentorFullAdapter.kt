package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFullTopMentorBinding
import com.example.telead_xml.databinding.ItemTopMentorHomeBinding
import com.example.telead_xml.domen.objects.MentorData
import com.example.telead_xml.view.listener.MentorListener

class TopMentorFullAdapter(val list: ArrayList<MentorData>, val listener: MentorListener) : RecyclerView.Adapter<TopMentorFullAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFullTopMentorBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_full_top_mentor, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mentor = list[position]
        holder.binding.name.text = "${mentor.surname} ${mentor.patronymic} ${mentor.name}"
        holder.binding.category.text = mentor.category

        holder.itemView.setOnClickListener {
            listener.click(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}