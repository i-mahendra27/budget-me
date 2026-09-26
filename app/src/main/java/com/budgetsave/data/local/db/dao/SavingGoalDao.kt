package com.budgetsave.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingGoalDao {
    @Query("SELECT * FROM saving_goals ORDER BY isCompleted ASC, name ASC")
    fun getAllSavingGoals(): Flow<List<SavingGoalEntity>>

    @Query("SELECT * FROM saving_goals WHERE isCompleted = 0 ORDER BY name ASC")
    fun getActiveSavingGoals(): Flow<List<SavingGoalEntity>>

    @Query("SELECT * FROM saving_goals WHERE isCompleted = 1 ORDER BY name ASC")
    fun getCompletedSavingGoals(): Flow<List<SavingGoalEntity>>

    @Query("SELECT * FROM saving_goals WHERE id = :id")
    suspend fun getSavingGoalById(id: Long): SavingGoalEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavingGoal(savingGoal: SavingGoalEntity): Long

    @Update
    suspend fun updateSavingGoal(savingGoal: SavingGoalEntity)

    @Delete
    suspend fun deleteSavingGoal(savingGoal: SavingGoalEntity)
}
