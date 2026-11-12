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
        observeGoal()
    }

    private fun observeGoal() {
        viewModelScope.launch {
            savingGoalRepository.observeSavingGoalById(goalId).collect { goal ->
                _uiState.value = SavingGoalDetailUiState(goal = goal, isLoading = false)
            }
        }
    }

    fun withdrawFromSavings(amount: Double) {
        viewModelScope.launch {
            val goal = _uiState.value.goal ?: return@launch
            if (amount <= 0 || amount > goal.currentAmount) return@launch

            val newAmount = goal.currentAmount - amount
            savingGoalRepository.updateSavingGoal(
                goal.copy(
                    currentAmount = newAmount,
                    isCompleted = newAmount >= goal.targetAmount
                )
            )
        }
    }
}
