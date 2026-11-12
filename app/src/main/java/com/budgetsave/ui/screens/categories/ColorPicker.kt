package com.budgetsave.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val categoryColors = listOf(
    "#FF9800", "#2196F3", "#E91E63", "#9C27B0",
    "#F44336", "#3F51B5", "#4CAF50", "#607D8B"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPicker(
    selectedColor: String,
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categoryColors.forEach { color ->
            val parsedColor = try {
                Color(android.graphics.Color.parseColor(color))
            } catch (e: Exception) {
                Color.Gray
            }
            val isSelected = selectedColor == color
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = parsedColor,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
                    .then(
                        if (isSelected) Modifier.border(
                            3.dp,
                            MaterialTheme.colorScheme.primary,
                            androidx.compose.foundation.shape.CircleShape
                        ) else Modifier
                    )
                    .clickable { onColorSelected(color) },
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
