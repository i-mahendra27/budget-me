package com.budgetsave.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetsave.data.local.db.entity.BudgetEntity
import com.budgetsave.data.local.db.entity.CategoryEntity
import com.budgetsave.data.local.repository.BudgetRepository
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

data class BudgetWithSpent(
    val budget: BudgetEntity,
    val spent: Double
)

data class BudgetUiState(
    val month: Int = 1,
    val year: Int = 2024,
    val budgets: List<BudgetWithSpent> = emptyList(),
    val categoryMap: Map<Long, CategoryEntity> = emptyMap(),
    val isLoading: Boolean = true
)

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    init {
        val (year, month) = DateUtils.getCurrentMonthYear()
        _uiState.update { it.copy(month = month, year = year) }
        loadBudgets()
    }

    private fun loadBudgets() {
        viewModelScope.launch {
            val state = _uiState.value
            val startOfMonth = DateUtils.getStartOfMonth(state.year, state.month)
            val endOfMonth = DateUtils.getEndOfMonth(state.year, state.month)

            combine(
                budgetRepository.getBudgetsByMonthYear(state.month, state.year),
                categoryRepository.getAllCategories(),
                transactionRepository.getTransactionsByTypeAndDateRange("EXPENSE", startOfMonth, endOfMonth)
            ) { budgets, categories, expenses ->
                val categoryMap = categories.associateBy { it.id }
                val expensesByCategory = expenses.groupBy { it.categoryId }

                val budgetsWithSpent = budgets.map { budget ->
                    val spent = expensesByCategory[budget.categoryId]?.sumOf { it.amount } ?: 0.0
                    BudgetWithSpent(budget, spent)
                }

                BudgetUiState(
                    month = state.month,
                    year = state.year,
                    budgets = budgetsWithSpent,
                    categoryMap = categoryMap,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun previousMonth() {
        _uiState.update { state ->
            var month = state.month - 1
            var year = state.year
            if (month < 1) {
                month = 12
                year -= 1
            }
            state.copy(month = month, year = year)
        }
        loadBudgets()
    }

    fun nextMonth() {
        _uiState.update { state ->
            var month = state.month + 1
            var year = state.year
            if (month > 12) {
                month = 1
                year += 1
            }
            state.copy(month = month, year = year)
        }
        loadBudgets()
    }

    fun deleteBudget(budget: BudgetEntity) {
        viewModelScope.launch {
            budgetRepository.deleteBudget(budget)
        }
    }
}
