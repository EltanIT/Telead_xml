package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemMentorReviewsBinding
import com.example.telead_xml.databinding.ItemReviewsBinding
import com.example.telead_xml.domen.objects.ReviewData

class MentorReviewsAdapter(val list: ArrayList<ReviewData>) : RecyclerView.Adapter<MentorReviewsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMentorReviewsBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mentor_reviews, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = list[position]
        holder.binding.name.text = review.name
        holder.binding.rating.text = review.rating.toString()
        holder.binding.description.text = review.description
        holder.binding.likeCount.text = review.likes.toString()
        holder.binding.time.text = review.date
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun filter(name: String?) {

    }

}