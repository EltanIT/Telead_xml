package com.example.telead_xml.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemPaymentMethodBinding
import com.example.telead_xml.domen.objects.CardData
import com.example.telead_xml.view.listener.PayMethodsListener

class PaymentMethodsAdapter(val list: ArrayList<CardData>, val listener: PayMethodsListener) : RecyclerView.Adapter<PaymentMethodsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPaymentMethodBinding.bind(itemView)
        val resources = itemView.resources
    }

    private var selectedItem = list.size-1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_method, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardData = list[position]

        if (holder.adapterPosition <= 2){
            holder.binding.name.text = cardData.name
        }else{
            holder.binding.name.text = cardData.number
        }

        holder.binding.check.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                listener.check(cardData)
            }
            selectedItem = holder.adapterPosition
            notifyDataSetChanged()
        }

        holder.binding.check.isChecked = (holder.adapterPosition == selectedItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}