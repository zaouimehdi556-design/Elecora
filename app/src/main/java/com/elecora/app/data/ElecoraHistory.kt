package com.elecora.app.data

import androidx.compose.runtime.mutableStateListOf

data class HistoryItem(
    val icon: String,
    val title: String,
    val result: String,
    val detail: String
)

object ElecoraHistory {

    val items = mutableStateListOf<HistoryItem>()

    fun add(item: HistoryItem) {
        items.add(0, item)

        if (items.size > 50) {
            items.removeAt(items.lastIndex)
        }
    }

    fun clear() {
        items.clear()
    }
}
