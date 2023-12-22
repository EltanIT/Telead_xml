package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemChatBinding
import com.example.telead_xml.domen.objects.CallData
import com.example.telead_xml.domen.objects.ChatData

class ChatAdapter(val list: ArrayList<ChatData>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemChatBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = list[position]
        val messageCount = chat.message

        holder.binding.name.text = chat.name
        holder.binding.time.text = chat.time
        holder.binding.description.text = chat.lastMessage


        if (messageCount>0){
            holder.binding.messageCount.text = messageCount.toString()
            holder.binding.messageCount.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}