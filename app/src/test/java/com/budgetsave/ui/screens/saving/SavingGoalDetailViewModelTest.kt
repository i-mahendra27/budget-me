package com.budgetsave.ui.screens.saving

import androidx.lifecycle.SavedStateHandle
import com.budgetsave.data.local.db.dao.SavingGoalDao
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import com.budgetsave.data.local.repository.SavingGoalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavingGoalDetailViewModelTest {

    private class FakeSavingGoalDao(initial: SavingGoalEntity?) : SavingGoalDao {
        val goalState = MutableStateFlow(initial)

        override fun getAllSavingGoals(): Flow<List<SavingGoalEntity>> = flowOf(listOfNotNull(goalState.value))

        override fun getActiveSavingGoals(): Flow<List<SavingGoalEntity>> =
            getAllSavingGoals().map { list -> list.filter { !it.isCompleted } }

        override fun getCompletedSavingGoals(): Flow<List<SavingGoalEntity>> =
            getAllSavingGoals().map { list -> list.filter { it.isCompleted } }

        override suspend fun getSavingGoalById(id: Long): SavingGoalEntity? =
            goalState.value?.takeIf { it.id == id }

        override fun observeSavingGoalById(id: Long): Flow<SavingGoalEntity?> = goalState

        override suspend fun insertSavingGoal(savingGoal: SavingGoalEntity): Long = 1L

        override suspend fun updateSavingGoal(savingGoal: SavingGoalEntity) {
            goalState.value = savingGoal
        }

        override suspend fun deleteSavingGoal(savingGoal: SavingGoalEntity) {
            goalState.value = null
        }
    }

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun goal(current: Double = 0.0, target: Double = 1000.0) = SavingGoalEntity(
        id = 1L,
        name = "Vacation",
        targetAmount = target,
        currentAmount = current,
        icon = "flight"
    )

    private fun createViewModel(dao: FakeSavingGoalDao) = SavingGoalDetailViewModel(
        savedStateHandle = SavedStateHandle(mapOf("goalId" to 1L)),
        savingGoalRepository = SavingGoalRepository(dao)
    )

    @Test
    fun `detail view model exposes loaded goal`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 250.0))
        val viewModel = createViewModel(dao)

        assertEquals(250.0, viewModel.uiState.value.goal?.currentAmount ?: 0.0, 0.001)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `detail view model reflects goal updates`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 250.0))
        val viewModel = createViewModel(dao)

        dao.goalState.value = goal(current = 900.0, target = 1000.0)

        assertEquals(900.0, viewModel.uiState.value.goal?.currentAmount ?: 0.0, 0.001)
    }

    @Test
    fun `withdrawFromSavings reduces current amount`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 500.0))
        val viewModel = createViewModel(dao)

        viewModel.withdrawFromSavings(200.0)

        assertEquals(300.0, viewModel.uiState.value.goal?.currentAmount ?: 0.0, 0.001)
    }

    @Test
    fun `withdrawFromSavings rejects amount above current savings`() = runTest {
        val dao = FakeSavingGoalDao(goal(current = 500.0))
        val viewModel = createViewModel(dao)

        viewModel.withdrawFromSavings(900.0)

        assertEquals(500.0, viewModel.uiState.value.goal?.currentAmount ?: 0.0, 0.001)
    }
}
