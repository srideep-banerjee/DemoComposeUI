package com.example.blackofferassignment.view.refine

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.blackofferassignment.ui.theme.BlackOfferAssignmentTheme
import kotlin.math.roundToInt

class RefineActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackOfferAssignmentTheme(darkTheme = false, dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        RefineTopbar()
                    }
                ) {
                    val focusManager = LocalFocusManager.current
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(it)
                            .padding(24.dp)
                            .fillMaxSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                focusManager.clearFocus()
                            }
                            .verticalScroll(rememberScrollState())
                    ) {
                        AvailabilitySelector()
                        Spacer(Modifier.height(16.dp))
                        StatusField()
                        DistanceSelector()
                        Spacer(modifier = Modifier.height(16.dp))
                        ChipsSelector()
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { /*TODO*/ }) {
                            Text("Save & Explore")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefineTopbar() {
    TopAppBar(
        title = { 
            Text(text = "Refine")
        },
        navigationIcon = {
            val activity = (LocalContext.current as? Activity)
            IconButton(onClick = {
                activity?.finish()
            }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailabilitySelector() {
    val focusManager = LocalFocusManager.current
    Column(
        Modifier.clickable(
            interactionSource = remember{ MutableInteractionSource() },
            indication = null
        ) {
            focusManager.clearFocus()
        }
    ) {
        var currentAvailabilityEntry by remember {
            mutableStateOf(AvailabilityEntries.AVAILABLE)
        }
        var availabilityPopupExpanded by remember {
            mutableStateOf(false)
        }
        var selectorSize by remember {
            mutableStateOf(Size.Zero)
        }

        Text(
            text = "Select Your Availability",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = availabilityPopupExpanded,
            onExpandedChange = {
                availabilityPopupExpanded = it
                focusManager.clearFocus()
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        selectorSize = it.size.toSize()
                    }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .menuAnchor()
            ) {
                Text(
                    text = getTextOfAvailabilityEntry(currentAvailabilityEntry),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, true)
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "drop down"
                )
            }
            ExposedDropdownMenu(
                expanded = availabilityPopupExpanded,
                onDismissRequest = { availabilityPopupExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { selectorSize.width.toDp() })
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                AvailabilityEntries.entries.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = getTextOfAvailabilityEntry(it),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = {
                            currentAvailabilityEntry = it
                            availabilityPopupExpanded = false
                                  },
                    )
                }
            }
        }
    }
}

fun getTextOfAvailabilityEntry(availabilityEntries: AvailabilityEntries): String {
    return when (availabilityEntries) {
        AvailabilityEntries.AVAILABLE -> "Available | Hey Let Us Connect"
        AvailabilityEntries.AWAY -> "Away | Stay Discrete And Watch"
        AvailabilityEntries.BUSY -> "Busy | Do Not Disturb | Will Catch U Later"
        AvailabilityEntries.SOS -> "SOS | Emergency! Need Assistance!"
    }
}

@Composable
fun StatusField() {
    Column {
        Text(
            text = "Add Your Status",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))

        var textFieldValue by remember {
            mutableStateOf(
                TextFieldValue(
                    text = "Hi community! I am open to new connections \"\uD83D\uDE0A\"",
                )
            )
        }
        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()

        LaunchedEffect(isFocused) {

            val endRange = if (isFocused) textFieldValue.text.length else 0

            textFieldValue = textFieldValue.copy(
                selection = TextRange(
                    start = 0,
                    end = endRange
                )
            )
        }
        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                if(it.text.length <= 250) textFieldValue = it
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            interactionSource = interactionSource,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                it()
            }
        }
        Text(
            text = "${textFieldValue.text.length}/250",
            color =
            if (textFieldValue.text.length < 250)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.error,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun ChipsSelector() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Select Purpose",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        ChipsPanel()
    }
}

@Composable
fun ChipsPanel(
    chipList: SnapshotStateList<ChipData> = remember {
        mutableStateListOf(
            ChipData("Coffee",true),
            ChipData("Business", true),
            ChipData("Hobbies", false),
            ChipData("Friendship", true),
            ChipData("Movies", false),
            ChipData("Dinning", false),
            ChipData("Dating", false),
            ChipData("Matrimony", false)
        )
    },
    verticalPadding: Dp = 4.dp,
    horizontalPadding: Dp = 8.dp
) {
    Layout(
        content = {
                  for (i in 0..<chipList.size) {
                      Chip(
                          text = chipList[i].text,
                          selected = chipList[i].selected
                      ) {
                          chipList[i] = chipList[i].selected(!chipList[i].selected)
                      }
                  }
        },
        measurePolicy = {measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints.copy(minWidth = 0, minHeight = 0))
            }
            val verticalDist = verticalPadding.toPx().roundToInt()
            val horizontalDist = horizontalPadding.toPx().roundToInt()
            val rows: MutableList<MutableList<Placeable>> = mutableListOf(mutableListOf())
            var currentColumnWidth = 0
            for (placeable in placeables) {
                if (rows.last().size == 0) {
                    rows.last().add(placeable)
                    currentColumnWidth = placeable.width
                }
                else if (currentColumnWidth + horizontalDist + placeable.width > constraints.maxWidth) {
                    rows.add(mutableListOf(placeable))
                    currentColumnWidth = placeable.width
                }
                else {
                    rows.last().add(placeable)
                    currentColumnWidth += horizontalDist + placeable.width
                }
            }

            val maxHeightPerRow = rows.map {
                it.maxBy {placeable ->
                    placeable.height
                }
            }
            var totalHeight =   maxHeightPerRow.sumOf { it.height }
            if (placeables.size > 1) {
                totalHeight += (rows.size - 1) * verticalDist
            }

            var yOffset = 0
            return@Layout layout(width = constraints.maxWidth, height = totalHeight) {
                for (i in 0..<rows.size) {
                    val row = rows[i]
                    currentColumnWidth = 0
                    for (placeable in row) {
                        placeable.place(x = currentColumnWidth, y = yOffset)
                        currentColumnWidth += placeable.width + horizontalDist
                    }
                    currentColumnWidth = 0
                    yOffset += maxHeightPerRow[i].height + verticalDist
                }
            }
        }
    )
}

@Composable
fun Chip(text: String, selected: Boolean, onClick: ()->Unit) {
    SuggestionChip(
        onClick = onClick,
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = CircleShape,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
            labelColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        ),
        border = SuggestionChipDefaults
            .suggestionChipBorder(
                borderColor = MaterialTheme.colorScheme.primary,
                borderWidth = 1.dp
            )
    )
}