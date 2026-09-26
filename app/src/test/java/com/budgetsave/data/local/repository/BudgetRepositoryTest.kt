package com.budgetsave.data.local.repository

import com.budgetsave.data.local.db.dao.BudgetDao
import com.budgetsave.data.local.db.entity.BudgetEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class BudgetRepositoryTest {

    @Mock private lateinit var budgetDao: BudgetDao

    private lateinit var repository: BudgetRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = BudgetRepository(budgetDao)
    }

    @Test
    fun `getBudgetsByMonthYear returns flow from dao`() = runTest {
        val budgets = listOf(
            BudgetEntity(id = 1, categoryId = 1, amount = 500.0, month = 9, year = 2026)
        )
        whenever(budgetDao.getBudgetsByMonthYear(9, 2026)).thenReturn(kotlinx.coroutines.flow.flowOf(budgets))

        val result = repository.getBudgetsByMonthYear(9, 2026).first()

        assertEquals(1, result.size)
        assertEquals(500.0, result[0].amount, 0.01)
    }

    @Test
    fun `insertBudget returns id from dao`() = runTest {
        whenever(budgetDao.insertBudget(any())).thenReturn(1L)

        val id = repository.insertBudget(
            BudgetEntity(categoryId = 1, amount = 1000.0, month = 9, year = 2026)
        )

        assertEquals(1L, id)
    }
}
