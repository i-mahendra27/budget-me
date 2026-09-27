package com.budgetsave.data.local.repository

import com.budgetsave.data.local.db.dao.CategoryDao
import com.budgetsave.data.local.db.dao.TransactionDao
import com.budgetsave.data.local.db.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao
) {
    fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    suspend fun getCategoryById(id: Long): CategoryEntity? = categoryDao.getCategoryById(id)

    suspend fun insertCategory(category: CategoryEntity): Long = categoryDao.insertCategory(category)

    suspend fun updateCategory(category: CategoryEntity) = categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: CategoryEntity) = categoryDao.deleteCategory(category)

    suspend fun getCategoryCount(): Int = categoryDao.getCategoryCount()

    suspend fun hasTransactions(categoryId: Long): Boolean = transactionDao.getTransactionCountByCategory(categoryId) > 0
}
