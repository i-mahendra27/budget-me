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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_GOAL_ICON = "flight"

data class AddSavingGoalUiState(
    val name: String = "",
    val targetAmount: String = "",
    val icon: String = DEFAULT_GOAL_ICON,
    val nameError: String? = null,
    val amountError: String? = null,
    val existingGoal: SavingGoalEntity? = null
)

@HiltViewModel
class AddSavingGoalViewModel @Inject constructor(
    private val savingGoalRepository: SavingGoalRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val goalId: Long? = savedStateHandle.get<Long>("goalId")

    private val _uiState = MutableStateFlow(AddSavingGoalUiState())
    val uiState: StateFlow<AddSavingGoalUiState> = _uiState.asStateFlow()

    init {
        goalId?.let { loadGoal(it) }
    }

    private fun loadGoal(id: Long) {
        viewModelScope.launch {
            savingGoalRepository.getSavingGoalById(id)?.let { goal ->
                _uiState.update {
                    it.copy(
                        name = goal.name,
                        targetAmount = formatAmount(goal.targetAmount),
                        icon = goal.icon,
                        existingGoal = goal
                    )
                }
            }
        }
    }

    private fun formatAmount(value: Double): String =
        if (value % 1.0 == 0.0) value.toLong().toString() else value.toString()

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name, nameError = null) }
    }

    fun updateTargetAmount(amount: String) {
        _uiState.update { it.copy(targetAmount = amount, amountError = null) }
    }

    fun updateIcon(icon: String) {
        _uiState.update { it.copy(icon = icon) }
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

    /**
     * Builds the goal entity from the current form state, or null when the amount is invalid.
     */
    fun buildGoal(): SavingGoalEntity? {
        val state = _uiState.value
        val amount = state.targetAmount.replace("[^\\d.]".toRegex(), "").toDoubleOrNull() ?: return null

        val existing = state.existingGoal
        return SavingGoalEntity(
            id = existing?.id ?: 0,
            name = state.name.trim(),
            targetAmount = amount,
            currentAmount = existing?.currentAmount ?: 0.0,
            icon = state.icon,
            isCompleted = existing?.isCompleted ?: false
        )
    }
}
