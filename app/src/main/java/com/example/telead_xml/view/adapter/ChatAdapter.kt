package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemChatBinding
import com.example.telead_xml.domen.objects.CallData
import com.example.telead_xml.domen.objects.ChatData
import com.squareup.picasso.Picasso

class ChatAdapter(val list: ArrayList<ChatData>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemChatBinding.bind(itemView)
        val resources = itemView.resources
        val context = itemView.context
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

        holder.binding.name.text = chat.name
        holder.binding.time.text = chat.time
        holder.binding.description.text = chat.lastMessage

        Picasso.with(holder.context)
            .load(chat.image)
            .into(holder.binding.image)


        if (chat.message != null && chat.message > 0){
            holder.binding.messageCount.text = chat.message.toString()
            holder.binding.messageCountView.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}