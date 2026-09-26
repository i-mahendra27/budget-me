package com.budgetsave.ui.screens.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetsave.data.local.db.entity.CategoryEntity
import com.budgetsave.data.local.db.entity.TransactionEntity
import com.budgetsave.data.local.repository.CategoryRepository
import com.budgetsave.data.local.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddTransactionUiState(
    val transactionId: Long? = null,
    val amount: String = "",
    val type: String = "EXPENSE",
    val selectedCategory: CategoryEntity? = null,
    val categories: List<CategoryEntity> = emptyList(),
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val amountError: String? = null,
    val categoryError: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadTransaction(transactionId: Long) {
        viewModelScope.launch {
            val transaction = transactionRepository.getTransactionById(transactionId)
            if (transaction != null) {
                val category = categoryRepository.getCategoryById(transaction.categoryId)
                _uiState.update {
                    it.copy(
                        transactionId = transaction.id,
                        amount = transaction.amount.toLong().toString(),
                        type = transaction.type,
                        selectedCategory = category,
                        description = transaction.description,
                        date = transaction.date
                    )
                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { categories ->
                _uiState.update { it.copy(categories = categories) }
            }
        }
    }

    fun updateAmount(amount: String) {
        _uiState.update { it.copy(amount = amount, amountError = null) }
    }

    fun updateType(type: String) {
        _uiState.update { it.copy(type = type) }
    }

    fun updateCategory(category: CategoryEntity) {
        _uiState.update { it.copy(selectedCategory = category, categoryError = null) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updateDate(date: Long) {
        _uiState.update { it.copy(date = date) }
    }

    fun validate(): Boolean {
        var isValid = true
        val state = _uiState.value

        val amount = state.amount.replace("[^\\d.]".toRegex(), "").toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _uiState.update { it.copy(amountError = "Please enter a valid amount") }
            isValid = false
        }

        if (state.selectedCategory == null) {
            _uiState.update { it.copy(categoryError = "Please select a category") }
            isValid = false
        }

        return isValid
    }

    fun saveTransaction() {
        val state = _uiState.value
        val amount = state.amount.replace("[^\\d.]".toRegex(), "").toDoubleOrNull() ?: return
        val category = state.selectedCategory ?: return

        viewModelScope.launch {
            val transaction = TransactionEntity(
                id = state.transactionId ?: 0,
                amount = amount,
                type = state.type,
                categoryId = category.id,
                description = state.description,
                date = state.date
            )
            if (state.transactionId != null) {
                transactionRepository.updateTransaction(transaction)
            } else {
                transactionRepository.insertTransaction(transaction)
            }
        }
    }
}
