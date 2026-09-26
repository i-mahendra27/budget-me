package com.budgetsave.data.local.repository

import com.budgetsave.data.local.db.dao.SavingGoalDao
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class SavingGoalRepositoryTest {

    @Mock private lateinit var savingGoalDao: SavingGoalDao

    private lateinit var repository: SavingGoalRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = SavingGoalRepository(savingGoalDao)
    }

    @Test
    fun `getAllSavingGoals returns flow from dao`() = runTest {
        val goals = listOf(
            SavingGoalEntity(id = 1, name = "Vacation", targetAmount = 5000.0, currentAmount = 1000.0, icon = "flight")
        )
        whenever(savingGoalDao.getAllSavingGoals()).thenReturn(kotlinx.coroutines.flow.flowOf(goals))

        val result = repository.getAllSavingGoals().first()

        assertEquals(1, result.size)
        assertEquals("Vacation", result[0].name)
    }

    @Test
    fun `insertSavingGoal returns id from dao`() = runTest {
        whenever(savingGoalDao.insertSavingGoal(any())).thenReturn(1L)

        val id = repository.insertSavingGoal(
            SavingGoalEntity(name = "Car", targetAmount = 20000.0, icon = "car")
        )

        assertEquals(1L, id)
    }
}
