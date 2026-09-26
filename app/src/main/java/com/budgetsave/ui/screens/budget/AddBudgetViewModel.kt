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
    val selectedCategory: CategoryEntity? = null,
    val categories: List<CategoryEntity> = emptyList(),
    val amount: String = "",
    val categoryError: String? = null,
    val amountError: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class AddBudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBudgetUiState())
    val uiState: StateFlow<AddBudgetUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
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
                id = 0,
                categoryId = category.id,
                amount = amount,
                month = month,
                year = year
            )
            budgetRepository.insertBudget(budget)
        }
    }
}
