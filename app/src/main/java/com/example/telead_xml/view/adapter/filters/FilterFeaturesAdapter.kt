package com.example.telead_xml.view.adapter.filters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemFilterBinding
import com.example.telead_xml.domen.objects.FilterCategoryData
import com.example.telead_xml.view.listener.FilterListener

class FilterFeaturesAdapter(val list: ArrayList<FilterCategoryData>, val listener: FilterListener) : RecyclerView.Adapter<FilterFeaturesAdapter.ViewHolder>() {
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
        val filter = list[position]

        holder.binding.name.text = filter.name
        holder.binding.checkBox.isChecked = filter.check!!

        holder.binding.checkBox.setOnCheckedChangeListener { compoundButton, state ->
            if (state){
                listener.add(filter.id!!)
            }
            else{
                listener.remove(filter.id!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}