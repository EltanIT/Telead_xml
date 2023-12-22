package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFullTopMentorBinding
import com.example.telead_xml.databinding.ItemSearchHistoryBinding
import com.example.telead_xml.domen.objects.SearchHistoryData

class SearchHistorylAdapter(val list: ArrayList<SearchHistoryData>) : RecyclerView.Adapter<SearchHistorylAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSearchHistoryBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_history, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = list[position]
        holder.binding.name.text = history.name
    }

    override fun getItemCount(): Int {
        return list.size
    }

}