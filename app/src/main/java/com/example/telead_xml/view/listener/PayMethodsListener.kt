package com.example.telead_xml.view.listener

import com.example.telead_xml.domen.objects.CardData

interface PayMethodsListener {
    fun check(cardData: CardData)
}