package com.budgetsave.data.local.repository

import com.budgetsave.data.local.db.dao.CategoryDao
import com.budgetsave.data.local.db.entity.CategoryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class CategoryRepositoryTest {

    @Mock private lateinit var categoryDao: CategoryDao

    private lateinit var repository: CategoryRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = CategoryRepository(categoryDao)
    }

    @Test
    fun `getAllCategories returns flow from dao`() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Food", icon = "restaurant", color = "#FF0000")
        )
        whenever(categoryDao.getAllCategories()).thenReturn(kotlinx.coroutines.flow.flowOf(categories))

        val result = repository.getAllCategories().first()

        assertEquals(1, result.size)
        assertEquals("Food", result[0].name)
    }

    @Test
    fun `insertCategory returns id from dao`() = runTest {
        whenever(categoryDao.insertCategory(any())).thenReturn(1L)

        val id = repository.insertCategory(
            CategoryEntity(name = "Test", icon = "test", color = "#000000")
        )

        assertEquals(1L, id)
    }
}
