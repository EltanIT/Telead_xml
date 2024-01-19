package com.example.telead_xml.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemCallBinding
import com.example.telead_xml.domen.objects.CallData
import com.example.telead_xml.view.listener.CallListener
import com.squareup.picasso.Picasso

class CallsAdapter(val list: ArrayList<CallData>, val listener: CallListener) : RecyclerView.Adapter<CallsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCallBinding.bind(itemView)
        val resources = itemView.resources
        val context = itemView.context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val call = list[position]

        holder.binding.name.text = call.name
        holder.binding.date.text = call.date
        holder.binding.type.text = call.type

        holder.binding.call.setOnClickListener {
            listener.call(call.name?:"")
        }

        if (call.type.equals("Исходящий")){
            holder.binding.iconType.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_outcoming_type))
        }else if(call.type.equals("Входящий")){
            holder.binding.iconType.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_incoming_type))
        }else if(call.type.equals("Пропущенный")){
            holder.binding.iconType.setImageDrawable(holder.resources.getDrawable(R.drawable.ic_missed_type))
        }

        Picasso.with(holder.context)
            .load(call.image)
            .into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}