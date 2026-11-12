package com.budgetsave.di

import android.content.Context
import androidx.room.Room
import com.budgetsave.data.local.db.AppDatabase
import com.budgetsave.data.local.db.DatabaseCallback
import com.budgetsave.data.local.db.dao.BudgetDao
import com.budgetsave.data.local.db.dao.CategoryDao
import com.budgetsave.data.local.db.dao.SavingGoalDao
import com.budgetsave.data.local.db.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        categoryDaoProvider: Provider<CategoryDao>
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .addCallback(DatabaseCallback(categoryDaoProvider))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideBudgetDao(database: AppDatabase): BudgetDao {
        return database.budgetDao()
    }

    @Provides
    @Singleton
    fun provideSavingGoalDao(database: AppDatabase): SavingGoalDao {
        return database.savingGoalDao()
    }
}
