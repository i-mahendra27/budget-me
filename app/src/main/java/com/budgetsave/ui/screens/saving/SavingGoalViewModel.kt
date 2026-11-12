package com.budgetsave.ui.screens.saving

import androidx.lifecycle.ViewModel
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import com.budgetsave.data.local.repository.SavingGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

sealed interface SavingGoalEvent {
    data object GoalCompleted : SavingGoalEvent
}

@HiltViewModel
class SavingGoalViewModel @Inject constructor(
    private val savingGoalRepository: SavingGoalRepository
) : ViewModel() {

    private val eventChannel = Channel<SavingGoalEvent>(Channel.BUFFERED)
    val events = eventChannel.receiveAsFlow()

    suspend fun addSavingGoal(goal: SavingGoalEntity): Long =
        savingGoalRepository.insertSavingGoal(goal)

    suspend fun updateSavingGoal(goal: SavingGoalEntity) {
        savingGoalRepository.updateSavingGoal(goal)
    }

    suspend fun deleteSavingGoal(goal: SavingGoalEntity) {
        savingGoalRepository.deleteSavingGoal(goal)
    }

    /**
     * Adds [amount] to the goal savings. Returns true when the goal reached its target.
     */
    suspend fun addToSavings(goal: SavingGoalEntity, amount: Double): Boolean {
        if (amount <= 0.0) return false

        val newAmount = goal.currentAmount + amount
        if (newAmount >= goal.targetAmount) {
            markAsCompleted(goal.copy(currentAmount = newAmount))
            return true
        }

        savingGoalRepository.updateSavingGoal(goal.copy(currentAmount = newAmount))
        return false
    }

    suspend fun markAsCompleted(goal: SavingGoalEntity) {
        if (goal.isCompleted) return

        val completedGoal = goal.copy(
            currentAmount = maxOf(goal.currentAmount, goal.targetAmount),
            isCompleted = true
        )
        savingGoalRepository.updateSavingGoal(completedGoal)
        eventChannel.send(SavingGoalEvent.GoalCompleted)
    }
}
