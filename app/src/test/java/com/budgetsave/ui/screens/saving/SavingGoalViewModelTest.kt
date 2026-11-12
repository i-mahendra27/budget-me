package com.budgetsave.ui.screens.saving

import com.budgetsave.data.local.db.dao.SavingGoalDao
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import com.budgetsave.data.local.repository.SavingGoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SavingGoalViewModelTest {

    private class FakeSavingGoalDao(initial: SavingGoalEntity?) : SavingGoalDao {
        val goalState = MutableStateFlow(initial)
        val updatedGoals = mutableListOf<SavingGoalEntity>()
        val deletedGoals = mutableListOf<SavingGoalEntity>()

        override fun getAllSavingGoals(): Flow<List<SavingGoalEntity>> = flowOf(listOfNotNull(goalState.value))

        override fun getActiveSavingGoals(): Flow<List<SavingGoalEntity>> =
            getAllSavingGoals().map { list -> list.filter { !it.isCompleted } }

        override fun getCompletedSavingGoals(): Flow<List<SavingGoalEntity>> =
            getAllSavingGoals().map { list -> list.filter { it.isCompleted } }

        override suspend fun getSavingGoalById(id: Long): SavingGoalEntity? =
            goalState.value?.takeIf { it.id == id }

        override fun observeSavingGoalById(id: Long): Flow<SavingGoalEntity?> = goalState

        override suspend fun insertSavingGoal(savingGoal: SavingGoalEntity): Long {
            goalState.value = savingGoal
            return 1L
        }

        override suspend fun updateSavingGoal(savingGoal: SavingGoalEntity) {
            updatedGoals += savingGoal
            goalState.value = savingGoal
        }

        override suspend fun deleteSavingGoal(savingGoal: SavingGoalEntity) {
            deletedGoals += savingGoal
            goalState.value = null
        }
    }

    private fun goal(current: Double = 0.0, target: Double = 1000.0) = SavingGoalEntity(
        id = 1L,
        name = "Vacation",
        targetAmount = target,
        currentAmount = current,
        icon = "flight"
    )

    private fun viewModel(dao: FakeSavingGoalDao) = SavingGoalViewModel(SavingGoalRepository(dao))

    @Test
    fun `addSavingGoal inserts the goal`() = runTest {
        val dao = FakeSavingGoalDao(null)
        val viewModel = viewModel(dao)

        val id = viewModel.addSavingGoal(goal())

        assertEquals(1L, id)
        assertEquals("Vacation", dao.goalState.value?.name)
    }

    @Test
    fun `updateSavingGoal persists changes`() = runTest {
        val dao = FakeSavingGoalDao(goal())
        val viewModel = viewModel(dao)

        viewModel.updateSavingGoal(goal(current = 100.0))

        assertEquals(100.0, dao.updatedGoals.last().currentAmount, 0.001)
    }

    @Test
    fun `deleteSavingGoal removes the goal`() = runTest {
        val dao = FakeSavingGoalDao(goal())
        val viewModel = viewModel(dao)

        viewModel.deleteSavingGoal(goal())

        assertEquals(1, dao.deletedGoals.size)
    }

    @Test
    fun `addToSavings increases current amount`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 200.0))
        val viewModel = viewModel(dao)

        val completed = viewModel.addToSavings(goal(current = 200.0), 300.0)

        assertFalse(completed)
        assertEquals(500.0, dao.updatedGoals.last().currentAmount, 0.001)
    }

    @Test
    fun `addToSavings rejects non positive amount`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 200.0))
        val viewModel = viewModel(dao)

        val completed = viewModel.addToSavings(goal(current = 200.0), 0.0)
        viewModel.addToSavings(goal(current = 200.0), -50.0)

        assertFalse(completed)
        assertTrue(dao.updatedGoals.isEmpty())
    }

    @Test
    fun `addToSavings auto completes goal when target reached`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 900.0))
        val viewModel = viewModel(dao)

        val completed = viewModel.addToSavings(goal(current = 900.0), 150.0)

        assertTrue(completed)
        assertTrue(dao.updatedGoals.last().isCompleted)
        assertEquals(1050.0, dao.updatedGoals.last().currentAmount, 0.001)
    }

    @Test
    fun `addToSavings emits goal completed event when target reached`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 900.0))
        val viewModel = viewModel(dao)

        viewModel.addToSavings(goal(current = 900.0), 150.0)

        assertEquals(SavingGoalEvent.GoalCompleted, viewModel.events.first())
    }

    @Test
    fun `markAsCompleted marks goal completed and emits event`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 100.0))
        val viewModel = viewModel(dao)

        viewModel.markAsCompleted(goal(current = 100.0))

        assertTrue(dao.updatedGoals.last().isCompleted)
        assertEquals(1000.0, dao.updatedGoals.last().currentAmount, 0.001)
        assertEquals(SavingGoalEvent.GoalCompleted, viewModel.events.first())
    }
}
