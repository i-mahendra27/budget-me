package com.budgetsave.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetsave.data.local.db.entity.CategoryEntity
import com.budgetsave.data.local.db.entity.TransactionEntity
import com.budgetsave.data.local.repository.CategoryRepository
import com.budgetsave.data.local.repository.TransactionRepository
import com.budgetsave.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val balance: Double = 0.0,
    val monthlyIncome: Double = 0.0,
    val monthlyExpense: Double = 0.0,
    val recentTransactions: List<TransactionEntity> = emptyList(),
    val categoryMap: Map<Long, CategoryEntity> = emptyMap(),
    val isLoading: Boolean = true
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            val (year, month) = DateUtils.getCurrentMonthYear()
            val startOfMonth = DateUtils.getStartOfMonth(year, month)
            val endOfMonth = DateUtils.getEndOfMonth(year, month)

            combine(
                transactionRepository.getTotalByTypeAndDateRange("INCOME", startOfMonth, endOfMonth),
                transactionRepository.getTotalByTypeAndDateRange("EXPENSE", startOfMonth, endOfMonth),
                transactionRepository.getAllTransactions(),
                categoryRepository.getAllCategories()
            ) { income, expense, transactions, categories ->
                val categoryMap = categories.associateBy { it.id }
                val totalIncome = transactions
                    .filter { it.type == "INCOME" }
                    .sumOf { it.amount }
                val totalExpense = transactions
                    .filter { it.type == "EXPENSE" }
                    .sumOf { it.amount }

                DashboardUiState(
                    balance = totalIncome - totalExpense,
                    monthlyIncome = income ?: 0.0,
                    monthlyExpense = expense ?: 0.0,
                    recentTransactions = transactions.take(5),
                    categoryMap = categoryMap,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
