package com.budgetsave.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.budgetsave.ui.screens.budget.AddBudgetScreen
import com.budgetsave.ui.screens.budget.BudgetScreen
import com.budgetsave.ui.screens.categories.AddCategoryScreen
import com.budgetsave.ui.screens.categories.CategoriesScreen
import com.budgetsave.ui.screens.dashboard.DashboardScreen
import com.budgetsave.ui.screens.saving.AddSavingGoalScreen
import com.budgetsave.ui.screens.saving.SavingGoalsScreen
import com.budgetsave.ui.screens.saving.SavingGoalDetailScreen
import com.budgetsave.ui.screens.settings.SettingsScreen
import com.budgetsave.ui.screens.transactions.AddTransactionScreen
import com.budgetsave.ui.screens.transactions.TransactionsScreen

@Composable
fun BudgetSaveNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.route in listOf(
        Screen.Dashboard.route,
        Screen.Transactions.route,
        Screen.Budget.route,
        Screen.Saving.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.screen.route
                        } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Bottom Navigation Screens
            composable(Screen.Dashboard.route) {
                DashboardScreen(
                    onNavigateToAddTransaction = {
                        navController.navigate(Screen.AddTransaction.route)
                    },
                    onNavigateToTransactions = {
                        navController.navigate(Screen.Transactions.route)
                    },
                    onNavigateToSettings = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }

            composable(Screen.Transactions.route) {
                TransactionsScreen(
                    onNavigateToAddTransaction = {
                        navController.navigate(Screen.AddTransaction.route)
                    },
                    onNavigateToEditTransaction = { transactionId ->
                        navController.navigate(Screen.EditTransaction.createRoute(transactionId))
                    },
                    onNavigateToSearch = {
                        navController.navigate(Screen.Search.route)
                    }
                )
            }

            composable(Screen.Budget.route) {
                BudgetScreen(
                    onNavigateToAddBudget = {
                        navController.navigate(Screen.AddBudget.route)
                    },
                    onNavigateToEditBudget = { budgetId ->
                        navController.navigate(Screen.EditBudget.createRoute(budgetId))
                    }
                )
            }

            composable(Screen.Saving.route) {
                SavingGoalsScreen(
                    onNavigateToAddGoal = {
                        navController.navigate(Screen.AddSavingGoal.route)
                    },
                    onNavigateToGoalDetail = { goalId ->
                        navController.navigate(Screen.SavingGoalDetail.createRoute(goalId))
                    }
                )
            }

            // Transaction Screens
            composable(Screen.AddTransaction.route) {
                AddTransactionScreen(
                    transactionId = null,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.EditTransaction.route,
                arguments = listOf(navArgument("transactionId") { type = NavType.LongType })
            ) { backStackEntry ->
                val transactionId = backStackEntry.arguments?.getLong("transactionId")
                AddTransactionScreen(
                    transactionId = transactionId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Budget Screens
            composable(Screen.AddBudget.route) {
                AddBudgetScreen(
                    budgetId = null,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.EditBudget.route,
                arguments = listOf(navArgument("budgetId") { type = NavType.LongType })
            ) { backStackEntry ->
                val budgetId = backStackEntry.arguments?.getLong("budgetId")
                AddBudgetScreen(
                    budgetId = budgetId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Saving Screens
            composable(Screen.AddSavingGoal.route) {
                AddSavingGoalScreen(
                    goalId = null,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.EditSavingGoal.route,
                arguments = listOf(navArgument("goalId") { type = NavType.LongType })
            ) { backStackEntry ->
                val goalId = backStackEntry.arguments?.getLong("goalId")
                AddSavingGoalScreen(
                    goalId = goalId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.SavingGoalDetail.route,
                arguments = listOf(navArgument("goalId") { type = NavType.LongType })
            ) { backStackEntry ->
                val goalId = backStackEntry.arguments?.getLong("goalId") ?: return@composable
                SavingGoalDetailScreen(
                    goalId = goalId,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToEdit = {
                        navController.navigate(Screen.EditSavingGoal.createRoute(goalId))
                    }
                )
            }

            // Category Screens
            composable(Screen.Categories.route) {
                CategoriesScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToAddCategory = {
                        navController.navigate(Screen.AddCategory.route)
                    },
                    onNavigateToEditCategory = { categoryId ->
                        navController.navigate(Screen.EditCategory.createRoute(categoryId))
                    }
                )
            }

            composable(Screen.AddCategory.route) {
                AddCategoryScreen(
                    categoryId = null,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.EditCategory.route,
                arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getLong("categoryId")
                AddCategoryScreen(
                    categoryId = categoryId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // Settings
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToCategories = {
                        navController.navigate(Screen.Categories.route)
                    }
                )
            }
        }
    }
}
