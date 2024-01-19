package com.example.telead_xml.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ItemNotificationSettingBinding
import com.example.telead_xml.domen.objects.SettingNotificationData
import com.example.telead_xml.view.listener.SettingNotificationListener

class NotificationSettingAdapter(val list: ArrayList<SettingNotificationData>, val listener: SettingNotificationListener) : RecyclerView.Adapter<NotificationSettingAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemNotificationSettingBinding.bind(itemView)
        val resources = itemView.resources
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification_setting, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val setting = list[position]

        holder.binding.name.text = setting.name
        holder.binding.check.isChecked = setting.check

        holder.binding.check.setOnCheckedChangeListener { compoundButton, check ->
            setting.check = check
            listener.check(setting)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}