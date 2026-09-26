package com.budgetsave.data.local.repository

import com.budgetsave.data.local.db.dao.SavingGoalDao
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavingGoalRepository @Inject constructor(
    private val savingGoalDao: SavingGoalDao
) {
    fun getAllSavingGoals(): Flow<List<SavingGoalEntity>> = savingGoalDao.getAllSavingGoals()

    fun getActiveSavingGoals(): Flow<List<SavingGoalEntity>> = savingGoalDao.getActiveSavingGoals()

    fun getCompletedSavingGoals(): Flow<List<SavingGoalEntity>> = savingGoalDao.getCompletedSavingGoals()

    suspend fun getSavingGoalById(id: Long): SavingGoalEntity? = savingGoalDao.getSavingGoalById(id)

    suspend fun insertSavingGoal(savingGoal: SavingGoalEntity): Long =
        savingGoalDao.insertSavingGoal(savingGoal)

    suspend fun updateSavingGoal(savingGoal: SavingGoalEntity) =
        savingGoalDao.updateSavingGoal(savingGoal)

    suspend fun deleteSavingGoal(savingGoal: SavingGoalEntity) =
        savingGoalDao.deleteSavingGoal(savingGoal)
}
