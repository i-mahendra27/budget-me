package com.budgetsave.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetsave.data.local.db.entity.BudgetEntity
import com.budgetsave.data.local.db.entity.CategoryEntity
import com.budgetsave.data.local.repository.BudgetRepository
import com.budgetsave.data.local.repository.CategoryRepository
import com.budgetsave.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddBudgetUiState(
    val budgetId: Long? = null,
    val selectedCategory: CategoryEntity? = null,
    val categories: List<CategoryEntity> = emptyList(),
    val amount: String = "",
    val categoryError: String? = null,
    val amountError: String? = null,
    val existingBudgetCategoryIds: Set<Long> = emptySet(),
    val isLoading: Boolean = false
)

@HiltViewModel
class AddBudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository,
    private val budgetId: Long? = null
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBudgetUiState(budgetId = budgetId))
    val uiState: StateFlow<AddBudgetUiState> = _uiState.asStateFlow()

    init {
        loadData()
        if (budgetId != null) {
            loadBudget(budgetId)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            val (year, month) = DateUtils.getCurrentMonthYear()
            budgetRepository.getBudgetsByMonthYear(month, year).collect { existingBudgets ->
                val existingCategoryIds = existingBudgets.map { it.categoryId }.toSet()
                _uiState.update { it.copy(existingBudgetCategoryIds = existingCategoryIds) }
            }
        }
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { categories ->
                val existingIds = _uiState.value.existingBudgetCategoryIds
                val filteredCategories = if (budgetId != null) {
                    categories
                } else {
                    categories.filter { it.id !in existingIds }
                }
                _uiState.update { it.copy(categories = filteredCategories) }
            }
        }
    }

    private fun loadBudget(id: Long) {
        viewModelScope.launch {
            budgetRepository.getBudgetById(id)?.let { budget ->
                categoryRepository.getCategoryById(budget.categoryId)?.let { category ->
                    _uiState.update {
                        it.copy(
                            selectedCategory = category,
                            amount = budget.amount.toLong().toString()
                        )
                    }
                }
            }
        }
    }

    fun updateCategory(category: CategoryEntity) {
        _uiState.update { it.copy(selectedCategory = category, categoryError = null) }
    }

    fun updateAmount(amount: String) {
        _uiState.update { it.copy(amount = amount, amountError = null) }
    }

    fun validate(): Boolean {
        var isValid = true
        val state = _uiState.value

        if (state.selectedCategory == null) {
            _uiState.update { it.copy(categoryError = "Please select a category") }
            isValid = false
        }

        val amount = state.amount.replace("[^\\d.]".toRegex(), "").toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _uiState.update { it.copy(amountError = "Please enter a valid amount") }
            isValid = false
        }

        return isValid
    }

    fun saveBudget() {
        val state = _uiState.value
        val amount = state.amount.replace("[^\\d.]".toRegex(), "").toDoubleOrNull() ?: return
        val category = state.selectedCategory ?: return
        val (year, month) = DateUtils.getCurrentMonthYear()

        viewModelScope.launch {
            val budget = BudgetEntity(
                id = state.budgetId ?: 0,
                categoryId = category.id,
                amount = amount,
                month = month,
                year = year
            )
            if (state.budgetId != null) {
                budgetRepository.updateBudget(budget)
            } else {
                budgetRepository.insertBudget(budget)
            }
        }
    }
}
