package com.budgetsave.data.local.repository

import com.budgetsave.data.local.db.dao.BudgetDao
import com.budgetsave.data.local.db.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {
    fun getBudgetsByMonthYear(month: Int, year: Int): Flow<List<BudgetEntity>> =
        budgetDao.getBudgetsByMonthYear(month, year)

    suspend fun getBudgetByCategoryAndMonthYear(categoryId: Long, month: Int, year: Int): BudgetEntity? =
        budgetDao.getBudgetByCategoryAndMonthYear(categoryId, month, year)

    suspend fun getBudgetById(id: Long): BudgetEntity? = budgetDao.getBudgetById(id)

    suspend fun insertBudget(budget: BudgetEntity): Long = budgetDao.insertBudget(budget)

    suspend fun updateBudget(budget: BudgetEntity) = budgetDao.updateBudget(budget)

    suspend fun deleteBudget(budget: BudgetEntity) = budgetDao.deleteBudget(budget)
}
