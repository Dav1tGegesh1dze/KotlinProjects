package com.example.task15

import androidx.lifecycle.ViewModel
import com.example.task15.ListAdapter.Card
import com.example.task15.ListAdapter.cardBackgrond


class CardViewModel: ViewModel() {
    private var _cards = mutableListOf(
        Card(
            id = 1,
            cardNumber = "4111-1111-1111-1111",
            ownerName = "Zaur Menteshashvili",
            expiryDate = "11/25",
            cardImage = cardBackgrond.Visa,
            ccv = "123"
        ),
        Card(
            id = 2,
            cardNumber = "5555-5555-5555-4444",
            ownerName = "Max Verstappen",
            expiryDate = "01/28",
            cardImage = cardBackgrond.Mastercard,
            ccv = "456"
        )
    )

    var cards: List<Card> = _cards
        private set

    fun removeCard(card: Card) {
        _cards.removeAll { it.id == card.id }
        cards = _cards.toList()
    }

    fun addCard(card: Card) {
        _cards.add(card)
        cards = _cards.toList()
    }
}