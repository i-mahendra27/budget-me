package com.budgetsave.ui.screens.saving

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import com.budgetsave.data.local.repository.SavingGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddSavingGoalUiState(
    val name: String = "",
    val targetAmount: String = "",
    val nameError: String? = null,
    val amountError: String? = null
)

@HiltViewModel
class AddSavingGoalViewModel @Inject constructor(
    private val savingGoalRepository: SavingGoalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddSavingGoalUiState())
    val uiState: StateFlow<AddSavingGoalUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun updateTargetAmount(amount: String) {
        _uiState.update { it.copy(targetAmount = amount, amountError = null) }
    }

    fun validate(): Boolean {
        var isValid = true
        val state = _uiState.value

        if (state.name.isBlank()) {
            _uiState.update { it.copy(nameError = "Please enter a goal name") }
            isValid = false
        }

        val amount = state.targetAmount.replace("[^\\d.]".toRegex(), "").toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _uiState.update { it.copy(amountError = "Please enter a valid amount") }
            isValid = false
        }

        return isValid
    }

    fun saveGoal() {
        val state = _uiState.value
        val amount = state.targetAmount.replace("[^\\d.]".toRegex(), "").toDoubleOrNull() ?: return

        viewModelScope.launch {
            val goal = SavingGoalEntity(
                id = 0,
                name = state.name,
                targetAmount = amount,
                currentAmount = 0.0,
                icon = "savings"
            )
            savingGoalRepository.insertSavingGoal(goal)
        }
    }
}
