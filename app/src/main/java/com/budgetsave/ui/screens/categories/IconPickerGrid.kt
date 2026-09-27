package com.budgetsave.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class IconItem(
    val name: String,
    val icon: ImageVector
)

val categoryIcons = listOf(
    // Basic
    IconItem("restaurant", Icons.Default.Restaurant),
    IconItem("directions_car", Icons.Default.DirectionsCar),
    IconItem("shopping_bag", Icons.Default.ShoppingBag),
    IconItem("movie", Icons.Default.LocalMovies),
    IconItem("school", Icons.Default.School),
    IconItem("payments", Icons.Default.Payments),
    IconItem("home", Icons.Default.Home),
    IconItem("subscriptions", Icons.Default.Subscriptions),
    IconItem("more_horiz", Icons.Default.MoreHoriz),
    // New icons
    IconItem("child_care", Icons.Default.ChildCare),
    IconItem("face", Icons.Default.Face),
    IconItem("account_balance", Icons.Default.AccountBalance),
    IconItem("receipt_long", Icons.Default.Receipt),
    IconItem("checkroom", Icons.Default.Checkroom),
    IconItem("devices", Icons.Default.Devices),
    IconItem("security", Icons.Default.Security),
    IconItem("local_laundry_service", Icons.Default.LocalLaundryService),
    IconItem("local_parking", Icons.Default.LocalParking),
    IconItem("sports_soccer", Icons.Default.SportsSoccer),
    IconItem("account_balance_wallet", Icons.Default.AccountBalanceWallet),
    IconItem("flight", Icons.Default.Flight)
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IconPickerGrid(
    selectedIcon: String,
    onIconSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categoryIcons.forEach { iconItem ->
            val isSelected = selectedIcon == iconItem.name
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    )
                    .then(
                        if (isSelected) Modifier.border(
                            2.dp,
                            MaterialTheme.colorScheme.primary,
                            CircleShape
                        ) else Modifier
                    )
                    .clickable { onIconSelected(iconItem.name) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconItem.icon,
                    contentDescription = iconItem.name,
                    tint = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
