package com.example.blackofferassignment.view.explore

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.blackofferassignment.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.TabbedExploreDisplay() {
    val tabTextList = remember {
        mutableStateListOf("Personal", "Business", "Merchant")
    }
    var currentTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabTextList.size
    }

    LaunchedEffect(currentTabIndex) {
        pagerState.animateScrollToPage(currentTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            currentTabIndex = pagerState.currentPage
        }
    }
    TabRow(
        selectedTabIndex = currentTabIndex,
        divider = {},
        indicator = { tabPositions ->
            if (currentTabIndex < tabPositions.size) {
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[currentTabIndex]),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {
        for (i in 0..<tabTextList.size) {
            Tab(
                selected = currentTabIndex == i,
                text = {
                    Text(
                        text = tabTextList[i],
                        style = MaterialTheme.typography.titleMedium,
                        color =
                        if (currentTabIndex == i)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                },
                onClick = { currentTabIndex = i },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.weight(1f, true)
    ) {
        CommonBody()
    }
}

@Composable
fun CommonBody() {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SearchField()
            IconButton(onClick = {}) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_filter),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "Filter"
                )
            }
        }
        val exploreItemDataList = List(20) {
            ExploreItemData(
                name = "Michael Murphy",
                invited = false,
                location = "San Francisco, within 1 Km",
                fraction = Math.random().toFloat(),
                purpose = mutableStateListOf("Friendship", "Coffee", "Hangout"),
                status = "Hi community! I am open to new connections"
            )
        }.toMutableStateList()
//        val exploreItemData by remember {
//            mutableStateOf(
//                ExploreItemData(
//                    name = "Michael Murphy",
//                    invited = false,
//                    location = "San Francisco, within 1 Km",
//                    fraction = 0.5f,
//                    purpose = mutableStateListOf("Friendship", "Cofee", "Hangout"),
//                    status = "Hi community! I am open to new connections"
//                )
//            )
//        }
        LazyColumn {
            items(20) {
                ExploreItem(exploreItemDataList[it]) {
                    exploreItemDataList[it] = exploreItemDataList[it].getInvertedInvitation()
                }
            }
        }
    }
}

@Composable
fun RowScope.SearchField() {
    var searchFieldText by remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = searchFieldText,
        onValueChange = {
            searchFieldText = it
        },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.outlineVariant),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.outline
        ),
        decorationBox = { innerTextField ->
            val searchFieldEmpty by remember {
                derivedStateOf {
                    searchFieldText.isEmpty()
                }
            }
            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    tint = MaterialTheme.colorScheme.outlineVariant,
                    contentDescription = "Search",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Box(modifier = Modifier.weight(1f, true)) {
                    if (searchFieldEmpty) {
                        Text(
                            text = "Search",
                            color = MaterialTheme.colorScheme.outlineVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    innerTextField()
                }
            }
        },
        modifier = Modifier.weight(1f, true)
    )
}