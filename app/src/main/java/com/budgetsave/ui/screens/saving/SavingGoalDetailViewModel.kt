package com.budgetsave.ui.screens.saving

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import com.budgetsave.data.local.repository.SavingGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SavingGoalDetailUiState(
    val goal: SavingGoalEntity? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class SavingGoalDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val savingGoalRepository: SavingGoalRepository
) : ViewModel() {

    private val goalId: Long = savedStateHandle["goalId"] ?: 0

    private val _uiState = MutableStateFlow(SavingGoalDetailUiState())
    val uiState: StateFlow<SavingGoalDetailUiState> = _uiState.asStateFlow()

    init {
        loadGoal()
    }

    private fun loadGoal() {
        viewModelScope.launch {
            val goal = savingGoalRepository.getSavingGoalById(goalId)
            _uiState.value = SavingGoalDetailUiState(goal = goal, isLoading = false)
        }
    }

    fun addToSavings(amount: Double) {
        viewModelScope.launch {
            val goal = _uiState.value.goal ?: return@launch
            val newAmount = goal.currentAmount + amount
            val isCompleted = newAmount >= goal.targetAmount

            val updatedGoal = goal.copy(
                currentAmount = newAmount,
                isCompleted = isCompleted
            )
            savingGoalRepository.updateSavingGoal(updatedGoal)
            _uiState.value = _uiState.value.copy(goal = updatedGoal)
        }
    }

    fun deleteGoal() {
        viewModelScope.launch {
            val goal = _uiState.value.goal ?: return@launch
            savingGoalRepository.deleteSavingGoal(goal)
        }
    }
}
