package com.example.telead_xml.view.adapter

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemTopMentorHomeBinding
import com.example.telead_xml.domen.objects.MentorData
import com.example.telead_xml.view.listener.MentorListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class TopMentorHomeAdapter(val list: ArrayList<MentorData>,val listener: MentorListener) : RecyclerView.Adapter<TopMentorHomeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemTopMentorHomeBinding.bind(itemView)
        val resources = itemView.resources
        val context = itemView.context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_top_mentor_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mentor = list[position]
        holder.binding.name.text = mentor.name
        Picasso.with(holder.context)
            .load(mentor.image)
            .into(holder.binding.image)
        holder.itemView.setOnClickListener {
            listener.click(mentor.name?:"")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}