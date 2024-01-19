package com.example.telead_xml.view.listener

import com.example.telead_xml.domen.objects.SettingNotificationData

interface SettingNotificationListener {
    fun check(settingNotificationData: SettingNotificationData)
}