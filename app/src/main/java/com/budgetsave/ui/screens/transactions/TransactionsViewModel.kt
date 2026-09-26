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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TransactionsUiState(
    val transactions: List<TransactionEntity> = emptyList(),
    val categoryMap: Map<Long, CategoryEntity> = emptyMap(),
    val selectedFilter: TransactionFilter = TransactionFilter.ALL,
    val isLoading: Boolean = true
)

enum class TransactionFilter {
    ALL, INCOME, EXPENSE
}

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionsUiState())
    val uiState: StateFlow<TransactionsUiState> = _uiState.asStateFlow()

    private var allTransactions: List<TransactionEntity> = emptyList()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            combine(
                transactionRepository.getAllTransactions(),
                categoryRepository.getAllCategories()
            ) { transactions, categories ->
                allTransactions = transactions
                val categoryMap = categories.associateBy { it.id }
                TransactionsUiState(
                    transactions = applyFilter(transactions, _uiState.value.selectedFilter),
                    categoryMap = categoryMap,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun setFilter(filter: TransactionFilter) {
        _uiState.value = _uiState.value.copy(
            selectedFilter = filter,
            transactions = applyFilter(allTransactions, filter)
        )
    }

    private fun applyFilter(transactions: List<TransactionEntity>, filter: TransactionFilter): List<TransactionEntity> {
        return when (filter) {
            TransactionFilter.ALL -> transactions
            TransactionFilter.INCOME -> transactions.filter { it.type == "INCOME" }
            TransactionFilter.EXPENSE -> transactions.filter { it.type == "EXPENSE" }
        }
    }

    fun deleteTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(transaction)
        }
    }

    fun filterByDateRange(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            combine(
                transactionRepository.getTransactionsByDateRange(startDate, endDate),
                categoryRepository.getAllCategories()
            ) { transactions, categories ->
                allTransactions = transactions
                val categoryMap = categories.associateBy { it.id }
                TransactionsUiState(
                    transactions = applyFilter(transactions, _uiState.value.selectedFilter),
                    categoryMap = categoryMap,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }
}
