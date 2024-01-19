package com.example.telead_xml.view.adapter.filters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFilterBinding
import com.example.telead_xml.domen.objects.FilterRatingData
import com.example.telead_xml.view.listener.FilterRatingListener

class FilterRatingAdapter(val list: ArrayList<FilterRatingData>, val filter: Double?, val listener: FilterRatingListener) : RecyclerView.Adapter<FilterRatingAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFilterBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]

        if (filter != null){
            holder.binding.checkBox.isChecked = category.rating == filter
        }
        holder.binding.name.text = category.name
        holder.binding.checkBox.isChecked = category.check

        holder.binding.checkBox.setOnCheckedChangeListener { compoundButton, state ->
            if (state){
                listener.addRating(category.rating)
            }
            else{
                listener.removeRating(category.rating)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}