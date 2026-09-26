package com.budgetsave.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.budgetsave.data.local.db.dao.BudgetDao
import com.budgetsave.data.local.db.dao.CategoryDao
import com.budgetsave.data.local.db.dao.SavingGoalDao
import com.budgetsave.data.local.db.dao.TransactionDao
import com.budgetsave.data.local.db.entity.BudgetEntity
import com.budgetsave.data.local.db.entity.CategoryEntity
import com.budgetsave.data.local.db.entity.SavingGoalEntity
import com.budgetsave.data.local.db.entity.TransactionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class,
        BudgetEntity::class,
        SavingGoalEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun savingGoalDao(): SavingGoalDao

    companion object {
        const val DATABASE_NAME = "budget_save_db"
    }
}

class DatabaseCallback(
    private val categoryDaoProvider: Provider<CategoryDao>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            populateDatabase()
        }
    }

    private suspend fun populateDatabase() {
        val categoryDao = categoryDaoProvider.get()

        // Only seed if no categories exist
        if (categoryDao.getCategoryCount() == 0) {
            val defaultCategories = listOf(
                CategoryEntity(name = "Food & Drinks", icon = "restaurant", color = "#FF9800"),
                CategoryEntity(name = "Transportation", icon = "directions_car", color = "#2196F3"),
                CategoryEntity(name = "Shopping", icon = "shopping_bag", color = "#E91E63"),
                CategoryEntity(name = "Entertainment", icon = "movie", color = "#9C27B0"),
                CategoryEntity(name = "Health", icon = "medical_services", color = "#F44336"),
                CategoryEntity(name = "Education", icon = "school", color = "#3F51B5"),
                CategoryEntity(name = "Salary", icon = "payments", color = "#4CAF50"),
                CategoryEntity(name = "Others", icon = "more_horiz", color = "#607D8B")
            )
            categoryDao.insertCategories(defaultCategories)
        }
    }
}
