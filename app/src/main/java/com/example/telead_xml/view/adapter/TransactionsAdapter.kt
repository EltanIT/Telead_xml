package com.example.telead_xml.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemTransactionBinding
import com.example.telead_xml.domen.objects.TransactionsData

class TransactionsAdapter(val list: ArrayList<TransactionsData>) : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemTransactionBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = list[position]

        holder.binding.name.text = transaction.name
        holder.binding.category.text = transaction.category


    }

    override fun getItemCount(): Int {
        return list.size
    }

}