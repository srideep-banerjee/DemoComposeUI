package com.example.blackofferassignment.view.refine

import androidx.compose.runtime.Immutable

@Immutable
data class ChipData(val text: String, val selected: Boolean) {
    fun selected(selected: Boolean): ChipData = ChipData(text, selected)
}