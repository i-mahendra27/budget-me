package com.budgetsave.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetsave.data.local.db.entity.CategoryEntity
import com.budgetsave.data.local.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoriesUiState(
    val categories: List<CategoryEntity> = emptyList(),
    val isLoading: Boolean = true,
    val categoryToDelete: CategoryEntity? = null,
    val hasTransactions: Boolean = false
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { categories ->
                _uiState.value = CategoriesUiState(
                    categories = categories,
                    isLoading = false
                )
            }
        }
    }

    suspend fun getCategoryById(id: Long): CategoryEntity? {
        return categoryRepository.getCategoryById(id)
    }

    fun addCategory(name: String, icon: String, color: String) {
        viewModelScope.launch {
            categoryRepository.insertCategory(
                CategoryEntity(name = name, icon = icon, color = color)
            )
        }
    }

    fun updateCategory(category: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.updateCategory(category)
        }
    }

    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
            _uiState.update { it.copy(categoryToDelete = null, hasTransactions = false) }
        }
    }

    fun showDeleteConfirmation(category: CategoryEntity) {
        viewModelScope.launch {
            val hasTx = categoryRepository.hasTransactions(category.id)
            _uiState.update { it.copy(categoryToDelete = category, hasTransactions = hasTx) }
        }
    }

    fun dismissDeleteConfirmation() {
        _uiState.update { it.copy(categoryToDelete = null, hasTransactions = false) }
    }
}
