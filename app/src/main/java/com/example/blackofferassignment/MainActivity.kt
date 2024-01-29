package com.example.blackofferassignment

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.blackofferassignment.ui.theme.BlackOfferAssignmentTheme
import com.example.blackofferassignment.view.explore.TabbedExploreDisplay
import com.example.blackofferassignment.view.refine.RefineActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackOfferAssignmentTheme(darkTheme = false, dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        MainTopbar {
                            startActivity(Intent(this, RefineActivity::class.java))
                        }
                    }
                ) {
                    Column(
                        Modifier
                            .padding(it)
                            .fillMaxSize()) {
                        TabbedExploreDisplay()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopbar(onRefineClicked: ()->Unit) {
    TopAppBar(
        title = {
            Column(Modifier.padding(start = 16.dp)) {
                Text(
                    text = "Howdy Firstname Lastname !!",
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location Icon",
                        contentScale = ContentScale.FillHeight,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        text = "San Francisco, California",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_drawer_icon),
                    contentDescription = "Drawer icon"
                )
            }
                         },
        actions = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember{ MutableInteractionSource() },
                        indication = null
                    ) { onRefineClicked() }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_refine_tab),
                    contentDescription = "Refine",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Refine",
                    style = MaterialTheme.typography.bodySmall
                )
            }
                  },
        colors = TopAppBarDefaults
            .topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
    )
}