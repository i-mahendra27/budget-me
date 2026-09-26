package com.budgetsave.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    // Bottom Navigation
    object Dashboard : Screen("dashboard")
    object Transactions : Screen("transactions")
    object Budget : Screen("budget")
    object Saving : Screen("saving")

    // Detail Screens
    object AddTransaction : Screen("transactions/add")
    object EditTransaction : Screen("transactions/edit/{transactionId}") {
        fun createRoute(transactionId: Long) = "transactions/edit/$transactionId"
    }

    object AddBudget : Screen("budget/add")
    object EditBudget : Screen("budget/edit/{budgetId}") {
        fun createRoute(budgetId: Long) = "budget/edit/$budgetId"
    }

    object AddSavingGoal : Screen("saving/add")
    object EditSavingGoal : Screen("saving/edit/{goalId}") {
        fun createRoute(goalId: Long) = "saving/edit/$goalId"
    }
    object SavingGoalDetail : Screen("saving/detail/{goalId}") {
        fun createRoute(goalId: Long) = "saving/detail/$goalId"
    }

    // Other Screens
    object Categories : Screen("categories")
    object AddCategory : Screen("categories/add")
    object EditCategory : Screen("categories/edit/{categoryId}") {
        fun createRoute(categoryId: Long) = "categories/edit/$categoryId"
    }

    object Accounts : Screen("accounts")
    object AddAccount : Screen("accounts/add")
    object EditAccount : Screen("accounts/edit/{accountId}") {
        fun createRoute(accountId: Long) = "accounts/edit/$accountId"
    }
    object Transfer : Screen("transfer")

    object Reports : Screen("reports")
    object MonthlyReport : Screen("reports/monthly/{year}/{month}") {
        fun createRoute(year: Int, month: Int) = "reports/monthly/$year/$month"
    }

    object Settings : Screen("settings")
    object Search : Screen("search")
    object RecurringTransactions : Screen("settings/recurring")
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Dashboard,
        label = "Dashboard",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        screen = Screen.Transactions,
        label = "Transactions",
        selectedIcon = Icons.Filled.Receipt,
        unselectedIcon = Icons.Outlined.Receipt
    ),
    BottomNavItem(
        screen = Screen.Budget,
        label = "Budget",
        selectedIcon = Icons.Filled.AccountBalanceWallet,
        unselectedIcon = Icons.Outlined.AccountBalanceWallet
    ),
    BottomNavItem(
        screen = Screen.Saving,
        label = "Saving",
        selectedIcon = Icons.Filled.Savings,
        unselectedIcon = Icons.Outlined.Savings
    )
)
