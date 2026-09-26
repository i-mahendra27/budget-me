# Budget & Save — Requirements Document

Dokumen ini berisi requirements detail untuk development e2e aplikasi Budget & Save.

**Target:** Android 14 (API 34)
**Versi Dokumen:** 1.0
**Tanggal:** 2026-09-26

---

## Daftar Isi

1. [M1: Project Setup & Foundation](#m1-project-setup--foundation)
2. [M2: Core Data Layer](#m2-core-data-layer)
3. [M3: Transaction Module](#m3-transaction-module)
4. [M4: Category Module](#m4-category-module)
5. [M5: Budget Module](#m5-budget-module)
6. [M6: Saving Goals Module](#m6-saving-goals-module)
7. [M7: Dashboard](#m7-dashboard)
8. [M8: v1.1 Search & Split](#m8-v11-search--split)
9. [M9: v1.1 Charts & Polish](#m9-v11-charts--polish)
10. [M10: v1.2 Accounts Module](#m10-v12-accounts-module)
11. [M11: v1.2 Transfer & Reports](#m11-v12-transfer--reports)
12. [M12: v1.3 Backup/Restore](#m12-v13-backuprestore)
13. [M13: v1.4 Recurring Transactions](#m13-v14-recurring-transactions)
14. [M14: Final Testing & Polish](#m14-final-testing--polish)

---

## M1: Project Setup & Foundation

**Estimasi:** 2-3 hari
**Goal:** Project structure, theme, navigation foundation

### 1.1 Dependencies (build.gradle.kts / libs.versions.toml)

```
plugins:
- com.android.application
- org.jetbrains.kotlin.android
- org.jetbrains.kotlin.plugin.serialization
- com.google.devtools.ksp

libraries:
- androidx.core:core-ktx
- androidx.lifecycle:lifecycle-runtime-ktx
- androidx.lifecycle:lifecycle-viewmodel-compose
- androidx.activity:activity-compose
- androidx.compose.ui:ui
- androidx.compose.ui:ui-graphics
- androidx.compose.ui:ui-tooling-preview
- androidx.compose.material3:material3
- androidx.compose.material:material-icons-extended
- androidx.navigation:navigation-compose
- androidx.room:room-runtime
- androidx.room:room-ktx
- org.jetbrains.kotlinx:kotlinx-coroutines-android
- org.jetbrains.kotlinx:kotlinx-serialization-json
```

### 1.2 Build Configuration

- compileSdk: 34
- minSdk: 34
- targetSdk: 34
- Kotlin version: 1.9.x
- Compose BOM: latest stable
- Room version: 2.6.x

### 1.3 Theme Setup

**Light Theme Colors:**
- Primary: #2E7D32 (Green 800)
- Secondary: #1565C0 (Blue 800)
- Background: #FAFAFA
- Surface: #FFFFFF
- Error: #D32F2F
- On Primary: #FFFFFF
- On Background: #1C1B1F

**Dark Theme Colors:**
- Primary: #66BB6A (Green 400)
- Secondary: #42A5F5 (Blue 400)
- Background: #121212
- Surface: #1E1E1E
- Error: #EF5350
- On Primary: #003300
- On Background: #E6E1E5

### 1.4 Typography

- Display Large: 57sp, Regular
- Display Medium: 45sp, Regular
- Display Small: 36sp, Regular
- Headline Large: 32sp, Regular
- Headline Medium: 28sp, Regular
- Title Large: 22sp, Regular
- Title Medium: 16sp, Medium
- Body Large: 16sp, Regular
- Body Medium: 14sp, Regular
- Label Large: 14sp, Medium
- Label Medium: 12sp, Medium

### 1.5 Navigation Setup

**Bottom Navigation Items:**
1. Dashboard (Home icon)
2. Transactions (Receipt icon)
3. Budget (Wallet icon)
4. Saving (Savings icon)

**Navigation Routes:**
```
- dashboard
- transactions
- transactions/add
- transactions/edit/{id}
- budget
- budget/add
- budget/edit/{id}
- saving
- saving/add
- saving/edit/{id}
- categories
- settings
```

### 1.6 Acceptance Criteria

- [ ] Project builds successfully with ./gradlew assembleDebug
- [ ] Light theme renders correctly
- [ ] Dark theme renders correctly (follows system)
- [ ] Bottom navigation switches between 4 main screens
- [ ] Navigation to add/edit screens works
- [ ] No crashes on app launch

---

## M2: Core Data Layer

**Estimasi:** 2-3 hari
**Goal:** Room database, DAOs, repositories

### 2.1 Entity Classes

```kotlin
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val icon: String,
    val color: String
)

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val type: String, // "INCOME" | "EXPENSE"
    val categoryId: Long,
    val description: String,
    val date: Long,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long,
    val amount: Double,
    val month: Int,
    val year: Int
)

@Entity(tableName = "saving_goals")
data class SavingGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val icon: String,
    val isCompleted: Boolean = false
)
```

### 2.2 DAO Interfaces

```kotlin
@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>
    
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): CategoryEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long
    
    @Update
    suspend fun updateCategory(category: CategoryEntity)
    
    @Delete
    suspend fun deleteCategory(category: CategoryEntity)
}

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>
    
    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<List<TransactionEntity>>
    
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity?
    
    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type AND date BETWEEN :startDate AND :endDate")
    fun getTotalByTypeAndDateRange(type: String, startDate: Long, endDate: Long): Flow<Double?>
    
    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId AND date BETWEEN :startDate AND :endDate")
    fun getTransactionsByCategoryAndDateRange(categoryId: Long, startDate: Long, endDate: Long): Flow<List<TransactionEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long
    
    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)
    
    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
}

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets WHERE month = :month AND year = :year")
    fun getBudgetsByMonthYear(month: Int, year: Int): Flow<List<BudgetEntity>>
    
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: Long): BudgetEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity): Long
    
    @Update
    suspend fun updateBudget(budget: BudgetEntity)
    
    @Delete
    suspend fun deleteBudget(budget: BudgetEntity)
}

@Dao
interface SavingGoalDao {
    @Query("SELECT * FROM saving_goals ORDER BY isCompleted ASC, name ASC")
    fun getAllSavingGoals(): Flow<List<SavingGoalEntity>>
    
    @Query("SELECT * FROM saving_goals WHERE id = :id")
    suspend fun getSavingGoalById(id: Long): SavingGoalEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavingGoal(savingGoal: SavingGoalEntity): Long
    
    @Update
    suspend fun updateSavingGoal(savingGoal: SavingGoalEntity)
    
    @Delete
    suspend fun deleteSavingGoal(savingGoal: SavingGoalEntity)
}
```

### 2.3 Repository Classes

```kotlin
class CategoryRepository(private val categoryDao: CategoryDao) {
    fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()
    suspend fun getCategoryById(id: Long): CategoryEntity? = categoryDao.getCategoryById(id)
    suspend fun insertCategory(category: CategoryEntity): Long = categoryDao.insertCategory(category)
    suspend fun updateCategory(category: CategoryEntity) = categoryDao.updateCategory(category)
    suspend fun deleteCategory(category: CategoryEntity) = categoryDao.deleteCategory(category)
}

class TransactionRepository(private val transactionDao: TransactionDao) {
    fun getAllTransactions(): Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()
    fun getTransactionsByDateRange(startDate: Long, endDate: Long) = transactionDao.getTransactionsByDateRange(startDate, endDate)
    suspend fun getTransactionById(id: Long): TransactionEntity? = transactionDao.getTransactionById(id)
    fun getTotalByTypeAndDateRange(type: String, startDate: Long, endDate: Long) = transactionDao.getTotalByTypeAndDateRange(type, startDate, endDate)
    fun getTransactionsByCategoryAndDateRange(categoryId: Long, startDate: Long, endDate: Long) = transactionDao.getTransactionsByCategoryAndDateRange(categoryId, startDate, endDate)
    suspend fun insertTransaction(transaction: TransactionEntity): Long = transactionDao.insertTransaction(transaction)
    suspend fun updateTransaction(transaction: TransactionEntity) = transactionDao.updateTransaction(transaction)
    suspend fun deleteTransaction(transaction: TransactionEntity) = transactionDao.deleteTransaction(transaction)
}

class BudgetRepository(private val budgetDao: BudgetDao) {
    fun getBudgetsByMonthYear(month: Int, year: Int) = budgetDao.getBudgetsByMonthYear(month, year)
    suspend fun getBudgetById(id: Long): BudgetEntity? = budgetDao.getBudgetById(id)
    suspend fun insertBudget(budget: BudgetEntity): Long = budgetDao.insertBudget(budget)
    suspend fun updateBudget(budget: BudgetEntity) = budgetDao.updateBudget(budget)
    suspend fun deleteBudget(budget: BudgetEntity) = budgetDao.deleteBudget(budget)
}

class SavingGoalRepository(private val savingGoalDao: SavingGoalDao) {
    fun getAllSavingGoals(): Flow<List<SavingGoalEntity>> = savingGoalDao.getAllSavingGoals()
    suspend fun getSavingGoalById(id: Long): SavingGoalEntity? = savingGoalDao.getSavingGoalById(id)
    suspend fun insertSavingGoal(savingGoal: SavingGoalEntity): Long = savingGoalDao.insertSavingGoal(savingGoal)
    suspend fun updateSavingGoal(savingGoal: SavingGoalEntity) = savingGoalDao.updateSavingGoal(savingGoal)
    suspend fun deleteSavingGoal(savingGoal: SavingGoalEntity) = savingGoalDao.deleteSavingGoal(savingGoal)
}
```

### 2.4 Database Class

```kotlin
@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class,
        BudgetEntity::class,
        SavingGoalEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun savingGoalDao(): SavingGoalDao
}
```

### 2.5 Seed Data (Categories)

Insert default categories on first launch:
1. Food & Drinks - icon: restaurant, color: #FF9800
2. Transportation - icon: directions_car, color: #2196F3
3. Shopping - icon: shopping_bag, color: #E91E63
4. Entertainment - icon: movie, color: #9C27B0
5. Health - icon: medical_services, color: #F44336
6. Education - icon: school, color: #3F51B5
7. Salary - icon: payments, color: #4CAF50
8. Others - icon: more_horiz, color: #607D8B

### 2.6 Acceptance Criteria

- [ ] All entities created correctly
- [ ] All DAOs compile without errors
- [ ] All repositories work with Flow
- [ ] Database migrations handled properly
- [ ] Default categories seeded on first run
- [ ] Unit tests for repository methods pass

---

## M3: Transaction Module

**Estimasi:** 3-4 hari
**Goal:** Transaction list, add, edit, delete functionality

### 3.1 Transaction Types

```kotlin
enum class TransactionType {
    INCOME,
    EXPENSE
}
```

### 3.2 Transaction List Screen

**Layout:**
- Top app bar with title "Transactions"
- Filter chips: All, Income, Expense
- Date range picker button
- LazyColumn with transaction items grouped by date
- FAB for adding new transaction

**Transaction Item:**
```
┌────────────────────────────────────────────┐
│ 🍔 Food & Drinks          -Rp 50,000      │
│ Lunch at restaurant        Sep 25, 2026    │
└────────────────────────────────────────────┘
```

**Interactions:**
- Tap item → Navigate to edit screen
- Swipe left → Delete with confirmation
- Tap filter chip → Filter list

### 3.3 Add/Edit Transaction Screen

**Form Fields:**
- Amount (NumericTextField, required)
- Type (SegmentedButton: Income / Expense)
- Category (Dropdown, required)
- Description (TextField, optional)
- Date (DatePicker, default: today)

**Validation:**
- Amount must be > 0
- Category must be selected
- Date cannot be future date (for now, allow it)

**Actions:**
- Save button (top app bar)
- Cancel/back navigation

### 3.4 ViewModel

```kotlin
@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    
    val transactions: StateFlow<List<TransactionEntity>> = ...
    val categories: StateFlow<List<CategoryEntity>> = ...
    
    fun addTransaction(...) { ... }
    fun updateTransaction(...) { ... }
    fun deleteTransaction(...) { ... }
    fun filterByType(...) { ... }
    fun filterByDateRange(...) { ... }
}
```

### 3.5 Acceptance Criteria

- [ ] Transaction list displays correctly
- [ ] Add transaction works and saves to database
- [ ] Edit transaction pre-fills form correctly
- [ ] Delete transaction with swipe works
- [ ] Filter by type (income/expense) works
- [ ] Filter by date range works
- [ ] Category dropdown populated from database
- [ ] Form validation works
- [ ] Amount formatted with thousand separators

---

## M4: Category Module

**Estimasi:** 1-2 hari
**Goal:** Category management (CRUD)

### 4.1 Category List Screen

**Layout:**
- Top app bar with title "Categories"
- LazyColumn with category items
- FAB for adding new category

**Category Item:**
```
┌────────────────────────────────────────────┐
│ 🍔  Food & Drinks                    >    │
└────────────────────────────────────────────┘
```

### 4.2 Add/Edit Category Screen

**Form Fields:**
- Name (TextField, required)
- Icon (Icon picker grid)
- Color (Color picker)

**Icon Options (Material Icons):**
restaurant, directions_car, shopping_bag, movie, medical_services, school, payments, home, flight, fitness_center, pets, coffee, local_grocery_store, local_hospital, credit_card, build, phone_android, laptop, headphones, sports_esports

**Color Options:**
#FF9800, #2196F3, #E91E63, #9C27B0, #F44336, #3F51B5, #4CAF50, #607D8B, #00BCD4, #795548

### 4.3 Acceptance Criteria

- [ ] Category list displays correctly
- [ ] Add category works
- [ ] Edit category pre-fills correctly
- [ ] Delete category with confirmation
- [ ] Cannot delete category with existing transactions (show warning)
- [ ] Icon picker works
- [ ] Color picker works

---

## M5: Budget Module

**Estimasi:** 2-3 hari
**Goal:** Budget per category with progress tracking

### 5.1 Budget List Screen

**Layout:**
- Top app bar with title "Budget"
- Month/Year selector (previous/next arrows + label)
- LazyColumn with budget items
- FAB for adding new budget

**Budget Item:**
```
┌────────────────────────────────────────────┐
│ 🍔 Food & Drinks                           │
│ ████████████░░░░░░░░░░░░░░  67%         │
│ Rp 400,000 of Rp 600,000                  │
└────────────────────────────────────────────┘
```

**Progress Bar Colors:**
- 0-50%: Green (#4CAF50)
- 51-80%: Yellow (#FFC107)
- 81-100%: Orange (#FF9800)
- >100%: Red (#F44336)

### 5.2 Add/Edit Budget Screen

**Form Fields:**
- Category (Dropdown, only categories without budget for this month)
- Budget Amount (NumericTextField, required)

**Validation:**
- Cannot add budget for same category in same month
- Amount must be > 0

### 5.3 Budget Progress Calculation

```
spent = SUM(transactions where type=EXPENSE AND categoryId=X AND date in current month)
progress = (spent / budgetAmount) * 100
remaining = budgetAmount - spent
```

### 5.4 Acceptance Criteria

- [ ] Budget list displays correctly
- [ ] Month/year navigation works
- [ ] Progress bar shows correct percentage
- [ ] Progress bar color changes based on percentage
- [ ] Add budget works
- [ ] Edit budget works
- [ ] Delete budget works
- [ ] Budget warning shown at 80% and 100%

---

## M6: Saving Goals Module

**Estimasi:** 2-3 hari
**Goal:** Saving goals with progress tracking

### 6.1 Saving Goals List Screen

**Layout:**
- Top app bar with title "Savings"
- LazyColumn with saving goal items
- FAB for adding new goal

**Saving Goal Item:**
```
┌────────────────────────────────────────────┐
│ 🎒 Vacation                          75%   │
│ ████████████░░░░░░░░░░░░░░░░░            │
│ Rp 7,500,000 of Rp 10,000,000             │
└────────────────────────────────────────────┘
```

**Progress Ring (Circular):**
- Diameter: 48dp
- Stroke width: 4dp
- Background: Surface variant
- Progress: Primary color

### 6.2 Add/Edit Saving Goal Screen

**Form Fields:**
- Name (TextField, required)
- Target Amount (NumericTextField, required)
- Icon (Icon picker)

### 6.3 Saving Goal Detail Screen

**Layout:**
- Goal name and icon
- Large circular progress indicator
- Current amount / Target amount
- Add to savings button
- Edit/Delete actions

**Add Savings Flow:**
1. User taps "Add Savings"
2. Enter amount dialog appears
3. Amount added to currentAmount
4. If currentAmount >= targetAmount, mark as completed

### 6.4 Acceptance Criteria

- [ ] Saving goals list displays correctly
- [ ] Circular progress shows correct percentage
- [ ] Add saving goal works
- [ ] Edit saving goal works
- [ ] Delete saving goal with confirmation
- [ ] Add to savings updates progress
- [ ] Auto-complete when target reached
- [ ] Completed goals shown differently

---

## M7: Dashboard

**Estimasi:** 2-3 hari
**Goal:** Summary view with quick actions

### 7.1 Dashboard Screen

**Layout:**
- Top app bar with title "Budget & Save"
- Balance card (total balance this month)
- Income/Expense summary card
- Recent transactions (last 5)
- Quick add FAB

**Balance Card:**
```
┌────────────────────────────────────────────┐
│ Current Balance                            │
│ Rp 15,750,000                             │
│                                             │
│ Income: Rp 25,000,000  │  Expense: Rp 9,250,000 │
└────────────────────────────────────────────┘
```

**Quick Summary Card:**
```
┌────────────────────────────────────────────┐
│ This Month                                 │
│ + Rp 25,000,000         - Rp 9,250,000    │
│ (Income)               (Expense)           │
└────────────────────────────────────────────┘
```

### 7.2 Date Helpers

```kotlin
fun getStartOfMonth(year: Int, month: Int): Long
fun getEndOfMonth(year: Int, month: Int): Long
fun getCurrentMonthYear(): Pair<Int, Int>
fun formatCurrency(amount: Double): String
fun formatDate(timestamp: Long): String
```

### 7.3 Acceptance Criteria

- [ ] Balance card shows correct total
- [ ] Income/Expense summary correct
- [ ] Recent transactions show last 5
- [ ] Tap transaction → navigate to edit
- [ ] FAB opens add transaction
- [ ] Month navigation updates all data

---

## M8: v1.1 Search & Split

**Estimasi:** 2-3 hari
**Goal:** Search and split transaction functionality

### 8.1 Search Transactions (v1.1)

**Search Screen:**
- Search bar at top (auto-focus)
- Real-time search as user types
- Search in description and amount

**Implementation:**
```kotlin
@Query("""
    SELECT * FROM transactions 
    WHERE description LIKE '%' || :query || '%' 
    OR CAST(amount AS TEXT) LIKE '%' || :query || '%'
    ORDER BY date DESC
""")
fun searchTransactions(query: String): Flow<List<TransactionEntity>>
```

### 8.2 Split Transaction (v1.1)

**Split Entity:**
```kotlin
@Entity(tableName = "split_transactions")
data class SplitTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val transactionId: Long,
    val categoryId: Long,
    val amount: Double
)
```

**Split Transaction Screen:**
- Total amount field
- Add split button
- List of splits (category + amount)
- Validation: sum of splits = total amount

### 8.3 Acceptance Criteria

- [ ] Search finds transactions by description
- [ ] Search finds transactions by amount
- [ ] Real-time search results
- [ ] Split transaction saves correctly
- [ ] Split validation: total must match
- [ ] Split transactions show in list correctly

---

## M9: v1.1 Charts & Polish

**Estimasi:** 2-3 hari
**Goal:** Pie chart, gesture navigation, haptic feedback

### 9.1 Spending Pie Chart (v1.1)

**Implementation:**
- Use Compose Canvas or library (vico, MPAndroidChart compose)
- Show category breakdown
- Tap segment to see details

**Chart Data:**
```kotlin
data class ChartData(
    val category: String,
    val amount: Double,
    val color: Color,
    val percentage: Float
)
```

### 9.2 Gesture Navigation (v1.1)

**Swipe Actions:**
- Swipe left on transaction: Delete
- Swipe right on transaction: Edit (optional)

**Implementation:**
```kotlin
SwipeToDismissBox(
    state = dismissState,
    backgroundContent = { ... },
    content = { ... }
)
```

### 9.3 Haptic Feedback (v1.1)

**Feedback Points:**
- Transaction added: Short click
- Transaction deleted: Double click
- Budget warning: Warning pattern
- Goal completed: Success pattern

**Implementation:**
```kotlin
val haptic = LocalHaptics.current
haptic?.performHapticFeedback(HapticFeedbackType.LongPress)
```

### 9.4 Acceptance Criteria

- [ ] Pie chart renders correctly
- [ ] Pie chart tap shows category details
- [ ] Swipe to delete works
- [ ] Haptic feedback on key actions
- [ ] Smooth animations

---

## M10: v1.2 Accounts Module

**Estimasi:** 3-4 hari
**Goal:** Multiple accounts support

### 10.1 Account Entity

```kotlin
@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String, // "CASH" | "BANK" | "E_WALLET"
    val balance: Double = 0.0,
    val icon: String,
    val color: String,
    val isDefault: Boolean = false
)
```

### 10.2 Account Types

```kotlin
enum class AccountType {
    CASH,
    BANK,
    E_WALLET
}
```

### 10.3 Accounts List Screen

**Layout:**
- Top app bar with title "Accounts"
- Total balance summary
- LazyColumn with account items grouped by type
- FAB for adding new account

**Account Item:**
```
┌────────────────────────────────────────────┐
│ 💳  BCA Savings                     >      │
│ Rp 10,000,000                             │
│ Bank                                      │
└────────────────────────────────────────────┘
```

### 10.4 Add/Edit Account Screen

**Form Fields:**
- Name (TextField, required)
- Type (SegmentedButton: Cash / Bank / E-Wallet)
- Initial Balance (NumericTextField)
- Icon (Icon picker)
- Color (Color picker)
- Set as default (Checkbox)

### 10.5 Transaction-Account Integration

- Add accountId to TransactionEntity
- Transaction list shows account
- Filter by account option
- Account balance auto-updates on transaction

### 10.6 Acceptance Criteria

- [ ] Account CRUD works
- [ ] Transaction linked to account
- [ ] Account balance updates correctly
- [ ] Filter transactions by account
- [ ] Default account selection works
- [ ] Account types grouped correctly

---

## M11: v1.2 Transfer & Reports

**Estimasi:** 3-4 hari
**Goal:** Transfer between accounts and reporting

### 11.1 Transfer Between Accounts (v1.2)

**Transfer Screen:**
- From account dropdown
- To account dropdown
- Amount field
- Note field (optional)

**Implementation:**
1. Create EXPENSE transaction from source account
2. Create INCOME transaction to destination account
3. Type = "TRANSFER" (not counted in budget)

### 11.2 Overall Monthly Budget (v1.2)

**Entity:**
```kotlin
@Entity(tableName = "overall_budgets")
data class OverallBudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val month: Int,
    val year: Int
)
```

### 11.3 Budget Rollover (v1.2)

**Implementation:**
1. At end of month, calculate unused budget
2. Carry over option in Settings
3. Add rolloverAmount to next month budget

### 11.4 Monthly Report (v1.2)

**Report Screen:**
- Month/Year selector
- Total Income
- Total Expense
- Net Savings (Income - Expense)
- Category breakdown table
- Trend comparison with previous month

### 11.5 Export to PDF (v1.2)

**Implementation:**
- Use Android Print Framework or library (iText, PDFJet)
- Generate PDF with:
  - Header: App name, month/year
  - Summary section
  - Category breakdown table
  - Top transactions

### 11.6 Acceptance Criteria

- [ ] Transfer creates 2 transactions correctly
- [ ] Transfer not counted in budget
- [ ] Overall budget shows total monthly limit
- [ ] Rollover calculation correct
- [ ] Monthly report displays all data
- [ ] PDF export generates valid file
- [ ] Share PDF works

---

## M12: v1.3 Backup/Restore

**Estimasi:** 2-3 hari
**Goal:** Export and import database

### 12.1 Backup Flow

1. User taps "Export Backup" in Settings
2. Show file name suggestion: budget_save_YYYYMMDD.db
3. Use Storage Access Framework (SAF)
4. User selects destination folder
5. Copy database file to location
6. Show success message

**Implementation:**
```kotlin
val dbFile = database.openHelper.writableDatabase.path
val destFile = // from SAF
File(dbFile).copyTo(destFile, overwrite = true)
```

### 12.2 Restore Flow

1. User taps "Import Backup" in Settings
2. File picker opens
3. User selects .db file
4. Show warning: "This will replace all current data"
5. Confirm to proceed
6. Copy backup file to database location
7. Force close and reopen app

### 12.3 Settings Screen Updates

**Layout:**
- Export Backup button
- Import Backup button
- App version info
- About section

### 12.4 Acceptance Criteria

- [ ] Export creates valid .db file
- [ ] File can be saved to any location
- [ ] Import replaces database correctly
- [ ] Warning shown before restore
- [ ] App restarts after restore
- [ ] Error handling for invalid files

---

## M13: v1.4 Recurring Transactions

**Estimasi:** 3-4 hari
**Goal:** Automated recurring transactions

### 13.1 Recurring Entity

```kotlin
@Entity(tableName = "recurring_transactions")
data class RecurringTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val type: String,
    val accountId: Long,
    val categoryId: Long,
    val description: String,
    val frequency: String, // "DAILY" | "WEEKLY" | "MONTHLY"
    val nextDate: Long,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
```

### 13.2 Frequency Options

```kotlin
enum class RecurringFrequency {
    DAILY,
    WEEKLY,
    MONTHLY
}
```

### 13.3 Add Recurring Transaction

**UI:**
- Toggle: "Make this recurring"
- Frequency selector (when toggled)
- Next occurrence date

### 13.4 Background Processing

**WorkManager Implementation:**
- Periodic work every 24 hours
- Check for transactions where nextDate <= today
- Create transaction and update nextDate
- Handle missed days

### 13.5 Recurring Management Screen

**Layout:**
- List of active recurring transactions
- Toggle to activate/deactivate
- Edit recurring
- Delete recurring

### 13.6 Target Date for Goals (v1.2 carried)

**Saving Goal Update:**
- Add targetDate field (nullable Long)
- Show days remaining
- Warning if past target date

### 13.7 Acceptance Criteria

- [ ] Create recurring transaction works
- [ ] Frequency calculation correct
- [ ] Background worker creates transactions
- [ ] Manage recurring in settings
- [ ] Target date for goals works
- [ ] Warning for overdue goals

---

## M14: Final Testing & Polish

**Estimasi:** 2-3 hari
**Goal:** Bug fixes, performance, release prep

### 14.1 Testing Checklist

**Unit Tests:**
- Repository methods
- ViewModel logic
- Date/currency formatters

**Integration Tests:**
- Database operations
- Transaction flow

**UI Tests:**
- Navigation flows
- Form validation
- CRUD operations

### 14.2 Performance Checks

- App cold start < 2 seconds
- Screen transitions < 300ms
- Database queries < 100ms
- Smooth scrolling (60fps)

### 14.3 Edge Cases

- Empty states for all lists
- Large transaction lists (1000+)
- Currency overflow (very large amounts)
- Date edge cases (month boundaries)
- Database migration handling

### 14.4 Polish Items

- Loading states
- Error messages
- Empty state illustrations
- Animation smoothness
- Accessibility (content descriptions)

### 14.5 Release Checklist

- [ ] All acceptance criteria met
- [ ] No known critical bugs
- [ ] APK size optimized
- [ ] Proguard/R8 minification enabled
- [ ] Version bumped correctly
- [ ] Release notes written

---

## Appendix A: Default Categories

| Name | Icon | Color |
|------|------|-------|
| Food & Drinks | restaurant | #FF9800 |
| Transportation | directions_car | #2196F3 |
| Shopping | shopping_bag | #E91E63 |
| Entertainment | movie | #9C27B0 |
| Health | medical_services | #F44336 |
| Education | school | #3F51B5 |
| Salary | payments | #4CAF50 |
| Others | more_horiz | #607D8B |

---

## Appendix B: Icon List

Material Icons to support:
- restaurant, local_cafe, local_bar
- directions_car, directions_bus, flight
- shopping_bag, shopping_cart
- movie, music_note, sports_esports
- medical_services, fitness_center
- school, book
- payments, account_balance
- home, work, store
- more_horiz, add, remove
- savings, wallet, credit_card

---

## Appendix C: Color Palette

| Name | Hex | Usage |
|------|-----|-------|
| Orange | #FF9800 | Food |
| Blue | #2196F3 | Transport |
| Pink | #E91E63 | Shopping |
| Purple | #9C27B0 | Entertainment |
| Red | #F44336 | Health |
| Indigo | #3F51B5 | Education |
| Green | #4CAF50 | Income |
| Blue Grey | #607D8B | Other |
| Cyan | #00BCD4 | Info |
| Brown | #795548 | Utilities |

---

*Document Version: 1.0*
*Created: 2026-09-26*
*Status: Complete*
", "file_path": "D:\\CodeProject\\Android\\budget-me\\specs\\requirements.md"}