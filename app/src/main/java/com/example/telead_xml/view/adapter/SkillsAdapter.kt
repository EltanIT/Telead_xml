package com.example.telead_xml.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemSkillBinding
import com.example.telead_xml.domen.objects.SkillData

class SkillsAdapter(val list: ArrayList<SkillData>) : RecyclerView.Adapter<SkillsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSkillBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val call = list[position]

        holder.binding.name.text = call.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

}