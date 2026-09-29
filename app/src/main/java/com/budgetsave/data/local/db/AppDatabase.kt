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
    version = 2,
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
                // Existing
                CategoryEntity(name = "Food & Drinks", icon = "restaurant", color = "#FF9800"),
                CategoryEntity(name = "Transportation", icon = "directions_car", color = "#2196F3"),
                CategoryEntity(name = "Shopping", icon = "shopping_bag", color = "#E91E63"),
                CategoryEntity(name = "Entertainment", icon = "movie", color = "#9C27B0"),
                CategoryEntity(name = "Health", icon = "medical_services", color = "#F44336"),
                CategoryEntity(name = "Education", icon = "school", color = "#3F51B5"),
                CategoryEntity(name = "Salary", icon = "payments", color = "#4CAF50"),
                CategoryEntity(name = "Others", icon = "more_horiz", color = "#607D8B"),
                // New
                CategoryEntity(name = "Subscription", icon = "subscriptions", color = "#00BCD4"),
                CategoryEntity(name = "Baby", icon = "child_care", color = "#FFEB3B"),
                CategoryEntity(name = "Beauty", icon = "face", color = "#E91E63"),
                CategoryEntity(name = "Balancing", icon = "account_balance", color = "#795548"),
                CategoryEntity(name = "Bills", icon = "receipt_long", color = "#9C27B0"),
                CategoryEntity(name = "Car", icon = "directions_car", color = "#3F51B5"),
                CategoryEntity(name = "Clothing", icon = "checkroom", color = "#FF5722"),
                CategoryEntity(name = "Electronics", icon = "devices", color = "#607D8B"),
                CategoryEntity(name = "Insurance", icon = "security", color = "#00BCD4"),
                CategoryEntity(name = "Home", icon = "home", color = "#8BC34A"),
                CategoryEntity(name = "Laundry", icon = "local_laundry_service", color = "#03A9F4"),
                CategoryEntity(name = "Parking", icon = "local_parking", color = "#FF9800"),
                CategoryEntity(name = "Sport", icon = "sports_soccer", color = "#4CAF50"),
                CategoryEntity(name = "Tax", icon = "account_balance_wallet", color = "#9E9E9E")
            )
            categoryDao.insertCategories(defaultCategories)
        }
    }
}
