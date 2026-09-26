package com.budgetsave.ui.screens.saving

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

data class SavingGoalsUiState(
    val goals: List<SavingGoalEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class SavingGoalsViewModel @Inject constructor(
    private val savingGoalRepository: SavingGoalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SavingGoalsUiState())
    val uiState: StateFlow<SavingGoalsUiState> = _uiState.asStateFlow()

    init {
        loadGoals()
    }

    private fun loadGoals() {
        viewModelScope.launch {
            savingGoalRepository.getAllSavingGoals().collect { goals ->
                _uiState.value = SavingGoalsUiState(
                    goals = goals,
                    isLoading = false
                )
            }
        }
    }
}
