package com.example.blackofferassignment.view.explore

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList

@Stable
data class ExploreItemData(
    val name: String,
    val invited: Boolean,
    val location: String,
    val fraction: Float,
    val purpose: SnapshotStateList<String>,
    val status: String
) {
    fun getInvertedInvitation(): ExploreItemData {
        return copy(invited = !invited)
    }
}