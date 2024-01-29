package com.example.blackofferassignment.view.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.blackofferassignment.R

@Composable
fun ExploreItem(exploreItemData: ExploreItemData, onInviteClick: () -> Unit) {
    val imageSize = 80.dp
    val imageNXOffset = 24.dp //Image negative X Offset

    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Row {
            Spacer(modifier = Modifier.width(imageNXOffset))
            ExploreItemCard(
                exploreItemData,
                imageNXOffset,
                imageSize,
                onInviteClick
            )
        }
        ExploreItemImage(imageSize)
    }
}

@Composable
fun RowScope.ExploreItemCard(exploreItemData: ExploreItemData, imageNXOffset: Dp, imageSize: Dp, onInviteClick: ()->Unit) {
    Column(
        modifier = Modifier
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .weight(1f, true)
    ) {
        Column(
            Modifier
                .height(32.dp + imageSize)
                .fillMaxWidth()) {
            Row(modifier = Modifier
                .fillMaxWidth()
            ) {
                Spacer(Modifier.weight(1f, true))
                Text(
                    text = if(!exploreItemData.invited) "+ INVITE" else "PENDING",
                    style = MaterialTheme.typography.labelMedium,
                    color = with(MaterialTheme.colorScheme) {
                        if(exploreItemData.invited) {
                            surfaceVariant
                        } else {
                            primary
                        }
                    },
                    modifier = Modifier.clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        onInviteClick()
                    }
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = imageSize - imageNXOffset)
            ) {
                Text(
                    text = exploreItemData.name,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = exploreItemData.location,
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .width(80.dp)
                        .height(10.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Spacer(
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxHeight()
                            .weight(exploreItemData.fraction, true)
                            .background(color = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(
                        Modifier.weight(1f - exploreItemData.fraction)
                    )
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = exploreItemData.purpose.joinToString(separator = " | "),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = exploreItemData.status,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BoxScope.ExploreItemImage(imageSize: Dp) {
    AsyncImage(
        model = R.drawable.demo,
        contentScale = ContentScale.Crop,
        contentDescription = "Profile Picture",
        placeholder = painterResource(id = R.drawable.baseline_image_24),
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 24.dp)
            .clip(RoundedCornerShape(16.dp))
            .size(imageSize, imageSize + 8.dp)
            .background(MaterialTheme.colorScheme.primary)
    )
}