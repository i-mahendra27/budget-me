package com.budgetsave.data.local.repository

import com.budgetsave.data.local.db.dao.TransactionDao
import com.budgetsave.data.local.db.entity.TransactionEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class TransactionRepositoryTest {

    @Mock private lateinit var transactionDao: TransactionDao

    private lateinit var repository: TransactionRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = TransactionRepository(transactionDao)
    }

    @Test
    fun `getAllTransactions returns flow from dao`() = runTest {
        val transactions = listOf(
            TransactionEntity(id = 1, amount = 100.0, type = "EXPENSE", categoryId = 1, description = "Test", date = 0)
        )
        whenever(transactionDao.getAllTransactions()).thenReturn(kotlinx.coroutines.flow.flowOf(transactions))

        val result = repository.getAllTransactions().first()

        assertEquals(1, result.size)
        assertEquals(100.0, result[0].amount, 0.01)
    }

    @Test
    fun `insertTransaction returns id from dao`() = runTest {
        whenever(transactionDao.insertTransaction(any())).thenReturn(1L)

        val id = repository.insertTransaction(
            TransactionEntity(amount = 50.0, type = "INCOME", categoryId = 1, description = "Test", date = 0)
        )

        assertEquals(1L, id)
    }
}
