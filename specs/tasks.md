# Budget & Save - Development Tasks

Dokumen ini berisi checklist tugas development berdasarkan requirements.md dan design.md.

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

### 1.1 Project Structure

- [x] Create project with Gradle wrapper
- [x] Setup build.gradle.kts with Version Catalog
- [x] Configure compileSdk: 34, minSdk: 34, targetSdk: 34
- [x] Create package structure (data, domain, ui, util)
- [x] Setup AndroidManifest.xml

### 1.2 Dependencies

- [x] Add Compose BOM and libraries
- [x] Add Room dependencies
- [x] Add Navigation Compose
- [x] Add Kotlin Coroutines
- [x] Add Kotlin Serialization
- [x] Add KSP for Room

### 1.3 Theme Setup

- [x] Create Theme.kt with ColorScheme
- [x] Implement Light Theme colors
- [x] Implement Dark Theme colors
- [x] Create Typography.kt with type scale
- [x] Create Shape.kt with corner radius tokens
- [x] Configure dynamic color (Material You)

### 1.4 Navigation Setup

- [x] Create NavigationRoutes.kt
- [x] Setup Bottom Navigation with 4 items
- [x] Implement NavHost with all routes
- [x] Create placeholder screens for navigation

### 1.5 Build Verification

- [x] Verify ./gradlew assembleDebug succeeds
- [ ] Verify Light theme renders
- [ ] Verify Dark theme renders
- [ ] Verify Bottom navigation works

---

## M2: Core Data Layer

**Estimasi:** 2-3 hari
**Goal:** Room database, DAOs, repositories

### 2.1 Entity Classes

- [x] Create CategoryEntity
- [x] Create TransactionEntity
- [x] Create BudgetEntity
- [x] Create SavingGoalEntity
- [x] Add Room annotations (@Entity, @PrimaryKey)

### 2.2 DAO Interfaces

- [x] Create CategoryDao with CRUD operations
- [x] Create TransactionDao with CRUD operations
- [x] Create BudgetDao with CRUD operations
- [x] Create SavingGoalDao with CRUD operations
- [x] Add Flow return types for reactive queries
- [x] Add date range query methods

### 2.3 Repository Classes

- [x] Create CategoryRepository
- [x] Create TransactionRepository
- [x] Create BudgetRepository
- [x] Create SavingGoalRepository
- [x] Wrap DAO operations in Repository layer

### 2.4 Database Class

- [x] Create AppDatabase.kt
- [x] Register all DAOs
- [x] Implement database callback for seed data
- [ ] Handle migrations

### 2.5 Seed Data

- [x] Create default categories on first launch
- [x] Add Food & Drinks category
- [x] Add Transportation category
- [x] Add Shopping category
- [x] Add Entertainment category
- [x] Add Health category
- [x] Add Education category
- [x] Add Salary category
- [x] Add Others category

### 2.6 Unit Tests

- [x] Test CategoryRepository
- [x] Test TransactionRepository
- [x] Test BudgetRepository
- [x] Test SavingGoalRepository

---

## M3: Transaction Module

**Estimasi:** 3-4 hari
**Goal:** Transaction list, add, edit, delete functionality

### 3.1 Transaction List Screen

- [x] Create TransactionListScreen composable
- [x] Implement LazyColumn with transaction items
- [x] Group transactions by date
- [x] Display transaction type icon
- [x] Display category icon and name
- [x] Display amount with formatting
- [x] Display description and date

### 3.2 Transaction Item

- [x] Create TransactionItem composable
- [x] Apply card styling (min 72dp height, 16dp padding)
- [x] Apply medium corner radius (8dp)
- [x] Add Level 1 elevation
- [x] Implement ripple effect on tap
- [x] Style income amounts (green)
- [x] Style expense amounts (red)

### 3.3 Filter Components

- [x] Create FilterChip components
- [x] Implement All filter
- [x] Implement Income filter
- [x] Implement Expense filter
- [x] Add date range picker button

### 3.4 Add Transaction Screen

- [x] Create AddTransactionScreen composable
- [x] Create AmountTextField with Rp prefix
- [x] Implement numeric keyboard
- [x] Add thousand separator formatting
- [x] Create TransactionType selector (Income/Expense)
- [x] Create Category dropdown
- [x] Create Description text field
- [x] Create Date picker
- [x] Add Save button in app bar

### 3.5 Edit Transaction Screen

- [x] Create EditTransactionScreen composable
- [x] Pre-fill form with existing data
- [x] Load transaction by ID from route
- [x] Handle save update

### 3.6 Form Validation

- [x] Validate amount > 0
- [x] Validate category selected
- [x] Show error states on TextField
- [x] Disable save button until valid

### 3.7 Transaction ViewModel

- [x] Create TransactionViewModel
- [x] Implement addTransaction (via AddTransactionViewModel)
- [x] Implement updateTransaction (via AddTransactionViewModel)
- [x] Implement deleteTransaction
- [x] Implement filterByType
- [x] Implement filterByDateRange
- [x] Use StateFlow for UI state

### 3.8 Delete Functionality

- [x] Implement swipe to delete
- [x] Show delete confirmation dialog
- [x] Add haptic feedback on delete

### 3.9 Acceptance Criteria

- [ ] Transaction list displays correctly
- [ ] Add transaction works
- [ ] Edit transaction works
- [ ] Delete transaction works
- [ ] Filter by type works
- [ ] Filter by date range works

---

## M4: Category Module

**Estimasi:** 1-2 hari
**Goal:** Category management (CRUD)

### 4.1 Category List Screen

- [ ] Create CategoryListScreen composable
- [ ] Implement LazyColumn with category items
- [ ] Display category icon
- [ ] Display category name
- [ ] Display category color indicator
- [ ] Add FAB for adding new category

### 4.2 Category Item

- [ ] Create CategoryItem composable
- [ ] Display leading icon
- [ ] Display category name
- [ ] Add chevron for navigation
- [ ] Apply card styling

### 4.3 Add/Edit Category Screen

- [ ] Create AddEditCategoryScreen composable
- [ ] Create Name text field
- [ ] Create Icon picker grid
- [ ] Create Color picker
- [ ] Add Save button

### 4.4 Icon Picker

- [ ] Create IconPickerGrid composable
- [ ] Display predefined icons (restaurant, directions_car, etc.)
- [ ] Allow selection with highlight
- [ ] Use Material Icons

### 4.5 Color Picker

- [ ] Create ColorPicker composable
- [ ] Display predefined colors
- [ ] Allow selection with checkmark
- [ ] Use hex colors from design.md

### 4.6 Delete Category

- [ ] Show confirmation dialog
- [ ] Check for existing transactions
- [ ] Show warning if category has transactions
- [ ] Prevent delete if transactions exist

### 4.7 Category ViewModel

- [ ] Create CategoryViewModel
- [ ] Implement addCategory
- [ ] Implement updateCategory
- [ ] Implement deleteCategory

---

## M5: Budget Module

**Estimasi:** 2-3 hari
**Goal:** Budget per category with progress tracking

### 5.1 Budget List Screen

- [ ] Create BudgetListScreen composable
- [ ] Create MonthYearNavigator
- [ ] Implement previous month button
- [ ] Implement next month button
- [ ] Display current month/year label
- [ ] Implement LazyColumn with budget items

### 5.2 Budget Item

- [ ] Create BudgetItem composable
- [ ] Display category icon and name
- [ ] Display progress bar
- [ ] Display spent/total amounts
- [ ] Calculate progress percentage
- [ ] Apply green color (0-50%)
- [ ] Apply yellow color (51-80%)
- [ ] Apply orange color (81-99%)
- [ ] Apply red color (100%+)
- [ ] Add warning icon at 80%
- [ ] Add danger icon at 100%

### 5.3 Progress Bar Component

- [ ] Create LinearProgressIndicator
- [ ] Set height: 8dp
- [ ] Set full corner radius (4dp)
- [ ] Set SurfaceVariant background
- [ ] Animate progress changes (300ms)

### 5.4 Add Budget Screen

- [ ] Create AddBudgetScreen composable
- [ ] Create Category dropdown
- [ ] Filter categories without budget for current month
- [ ] Create BudgetAmount text field
- [ ] Validate amount > 0
- [ ] Prevent duplicate category budget

### 5.5 Edit Budget Screen

- [ ] Create EditBudgetScreen composable
- [ ] Pre-fill form with existing data
- [ ] Handle save update

### 5.6 Budget Calculation

- [ ] Calculate spent from transactions
- [ ] Filter by category and date range
- [ ] Calculate remaining amount
- [ ] Handle rollover (future)

### 5.7 Budget ViewModel

- [ ] Create BudgetViewModel
- [ ] Implement addBudget
- [ ] Implement updateBudget
- [ ] Implement deleteBudget
- [ ] Implement loadBudgetsByMonthYear
- [ ] Calculate progress for each budget

### 5.8 Warning Notifications

- [ ] Show visual warning at 80%
- [ ] Show danger indicator at 100%
- [ ] Add haptic feedback at thresholds

---

## M6: Saving Goals Module

**Estimasi:** 2-3 hari
**Goal:** Saving goals with progress tracking

### 6.1 Saving Goals List Screen

- [ ] Create SavingGoalsListScreen composable
- [ ] Implement Active Goals section
- [ ] Implement Completed Goals section
- [ ] Add FAB for adding new goal

### 6.2 Saving Goal Item

- [ ] Create SavingGoalItem composable
- [ ] Display circular progress ring
- [ ] Display goal name
- [ ] Display current/target amounts
- [ ] Calculate and display percentage
- [ ] Apply completed styling for done goals
- [ ] Add checkmark for completed goals

### 6.3 Circular Progress Ring

- [ ] Create CircularProgressRing composable
- [ ] Set diameter: 48dp (small) / 120dp (large)
- [ ] Set stroke: 4dp (small) / 8dp (large)
- [ ] Set SurfaceVariant background
- [ ] Set Primary progress color
- [ ] Animate progress changes (500ms)

### 6.4 Add Goal Screen

- [ ] Create AddSavingGoalScreen composable
- [ ] Create GoalName text field
- [ ] Create TargetAmount text field
- [ ] Create Icon picker
- [ ] Validate name not empty
- [ ] Validate amount > 0

### 6.5 Edit Goal Screen

- [ ] Create EditSavingGoalScreen composable
- [ ] Pre-fill form with existing data
- [ ] Handle save update

### 6.6 Goal Detail Screen

- [ ] Create SavingGoalDetailScreen composable
- [ ] Display large circular progress ring
- [ ] Display goal name and icon
- [ ] Display current amount / target amount
- [ ] Add "Add to Savings" button
- [ ] Add Withdraw button (v1.2)
- [ ] Add Edit button
- [ ] Add Delete button

### 6.7 Add to Savings

- [ ] Create AddSavingsDialog
- [ ] Create Amount text field
- [ ] Validate amount > 0
- [ ] Update currentAmount
- [ ] Auto-complete if current >= target
- [ ] Add haptic feedback on complete

### 6.8 Saving Goal ViewModel

- [ ] Create SavingGoalViewModel
- [ ] Implement addSavingGoal
- [ ] Implement updateSavingGoal
- [ ] Implement deleteSavingGoal
- [ ] Implement addToSavings
- [ ] Implement markAsCompleted

---

## M7: Dashboard

**Estimasi:** 2-3 hari
**Goal:** Summary view with quick actions

### 7.1 Dashboard Screen

- [ ] Create DashboardScreen composable
- [ ] Create BalanceCard
- [ ] Create SummaryCard
- [ ] Create RecentTransactions section
- [ ] Add quick add FAB

### 7.2 Balance Card

- [ ] Create BalanceCard composable
- [ ] Display "Current Balance" label
- [ ] Display total balance (Headline Large, Bold)
- [ ] Display Income summary
- [ ] Display Expense summary
- [ ] Apply card styling with elevation

### 7.3 Summary Card

- [ ] Create SummaryCard composable
- [ ] Display "This Month" label
- [ ] Display total income (green)
- [ ] Display total expense (red)
- [ ] Calculate net savings

### 7.4 Recent Transactions

- [ ] Create RecentTransactionsSection
- [ ] Display last 5 transactions
- [ ] Group by date
- [ ] Show "See All" link to Transactions

### 7.5 Quick Add FAB

- [ ] Position FAB bottom right
- [ ] Set Primary background color
- [ ] Set 56dp size
- [ ] Set full corner radius (28dp)
- [ ] Set Level 3 elevation
- [ ] Navigate to AddTransaction on tap

### 7.6 Dashboard ViewModel

- [ ] Create DashboardViewModel
- [ ] Load total balance
- [ ] Load monthly income/expense
- [ ] Load recent transactions
- [ ] Refresh data on screen focus

### 7.7 Date Helpers

- [ ] Create getStartOfMonth
- [ ] Create getEndOfMonth
- [ ] Create getCurrentMonthYear
- [ ] Create formatCurrency
- [ ] Create formatDate

---

## M8: v1.1 Search & Split

**Estimasi:** 2-3 hari
**Goal:** Search and split transaction functionality

### 8.1 Search Screen

- [ ] Create SearchScreen composable
- [ ] Create SearchBar at top
- [ ] Auto-focus search bar on enter
- [ ] Implement real-time search
- [ ] Display search results

### 8.2 Search Implementation

- [ ] Add searchTransactions to TransactionDao
- [ ] Search in description
- [ ] Search in amount
- [ ] Use LIKE query
- [ ] Return Flow for reactive updates

### 8.3 Search Results

- [ ] Create SearchResultsList composable
- [ ] Display matching transactions
- [ ] Highlight search term
- [ ] Show "No results" empty state

### 8.4 Split Transaction Entity

- [ ] Create SplitTransactionEntity
- [ ] Add transactionId foreign key
- [ ] Add categoryId
- [ ] Add amount

### 8.5 Split Transaction Screen

- [ ] Create SplitTransactionSection
- [ ] Display total amount field
- [ ] Create AddSplitButton
- [ ] Create SplitList
- [ ] Add category dropdown per split
- [ ] Add amount field per split

### 8.6 Split Validation

- [ ] Calculate sum of splits
- [ ] Compare with total amount
- [ ] Show validation error if mismatch
- [ ] Disable save until valid

### 8.7 Split Save Logic

- [ ] Create parent transaction
- [ ] Create split entries
- [ ] Mark transaction as split
- [ ] Save to database

### 8.8 Display Split Transactions

- [ ] Load split entries for transaction
- [ ] Display category breakdown
- [ ] Show split amounts

---

## M9: v1.1 Charts & Polish

**Estimasi:** 2-3 hari
**Goal:** Pie chart, gesture navigation, haptic feedback

### 9.1 Pie Chart Component

- [ ] Create PieChart composable
- [ ] Set diameter: 200dp
- [ ] Draw segments with category colors
- [ ] Animate segments (500ms staggered)
- [ ] Display legend below chart

### 9.2 Pie Chart Data

- [ ] Create ChartData model
- [ ] Calculate category totals
- [ ] Calculate percentages
- [ ] Map to colors from design.md

### 9.3 Pie Chart Legend

- [ ] Create PieChartLegend composable
- [ ] Display color indicator
- [ ] Display category name
- [ ] Display percentage

### 9.4 Swipe Actions

- [ ] Implement SwipeToDismiss
- [ ] Swipe left to delete
- [ ] Show delete background
- [ ] Show confirmation dialog
- [ ] Add haptic feedback

### 9.5 Haptic Feedback Implementation

- [ ] Setup HapticFeedback
- [ ] Add on button tap (short click)
- [ ] Add on transaction saved (double click)
- [ ] Add on transaction deleted (warning)
- [ ] Add on budget warning (triple click)
- [ ] Add on goal completed (celebration)

### 9.6 Animation Polish

- [ ] Animate card appear (200ms)
- [ ] Animate page transitions (300ms)
- [ ] Animate list item stagger (50ms)
- [ ] Use FastOutSlowIn easing
- [ ] Use SlowOutFastIn for exit

### 9.7 FAB Animation

- [ ] Animate FAB press (scale 0.95)
- [ ] Use 100ms duration
- [ ] Add ripple effect

---

## M10: v1.2 Accounts Module

**Estimasi:** 3-4 hari
**Goal:** Multiple accounts support

### 10.1 Account Entity

- [ ] Create AccountEntity
- [ ] Add name, type, balance
- [ ] Add icon, color
- [ ] Add isDefault flag

### 10.2 Account Types

- [ ] Create AccountType enum
- [ ] Add CASH type
- [ ] Add BANK type
- [ ] Add E_WALLET type

### 10.3 Account DAO

- [ ] Create AccountDao
- [ ] Add CRUD operations
- [ ] Add getDefaultAccount
- [ ] Add getAccountsByType

### 10.4 Account Repository

- [ ] Create AccountRepository
- [ ] Wrap AccountDao operations
- [ ] Handle balance updates

### 10.5 Accounts List Screen

- [ ] Create AccountsListScreen composable
- [ ] Display total balance
- [ ] Group accounts by type
- [ ] Create Bank Accounts section
- [ ] Create E-Wallets section
- [ ] Create Cash section

### 10.6 Account Item

- [ ] Create AccountItem composable
- [ ] Display account icon
- [ ] Display account name
- [ ] Display balance
- [ ] Display account type label

### 10.7 Add/Edit Account Screen

- [ ] Create AddEditAccountScreen composable
- [ ] Create Name text field
- [ ] Create Type selector (Cash/Bank/E-Wallet)
- [ ] Create InitialBalance text field
- [ ] Create Icon picker
- [ ] Create Color picker
- [ ] Create isDefault checkbox
- [ ] Validate required fields

### 10.8 Account Detail Screen

- [ ] Create AccountDetailScreen composable
- [ ] Display account info
- [ ] Display balance
- [ ] Display transaction history
- [ ] Add Edit button
- [ ] Add Delete button

### 10.9 Transaction-Account Integration

- [ ] Add accountId to TransactionEntity
- [ ] Update Transaction form to select account
- [ ] Update Transaction list to show account
- [ ] Update account balance on transaction
- [ ] Add filter by account option

### 10.10 Default Account

- [ ] Pre-select default account in form
- [ ] Allow change in Settings

---

## M11: v1.2 Transfer & Reports

**Estimasi:** 3-4 hari
**Goal:** Transfer between accounts and reporting

### 11.1 Transfer Screen

- [ ] Create TransferScreen composable
- [ ] Create FromAccount dropdown
- [ ] Create ToAccount dropdown
- [ ] Create Amount text field
- [ ] Create Note text field
- [ ] Validate accounts different
- [ ] Validate sufficient balance

### 11.2 Transfer Implementation

- [ ] Create EXPENSE transaction from source
- [ ] Create INCOME transaction to destination
- [ ] Set type to TRANSFER
- [ ] Update both account balances
- [ ] Add to transaction history

### 11.3 Overall Budget Entity

- [ ] Create OverallBudgetEntity
- [ ] Add amount, month, year
- [ ] Add rolloverAmount

### 11.4 Overall Budget Screen

- [ ] Create OverallBudgetCard
- [ ] Display total monthly limit
- [ ] Display spent amount
- [ ] Display progress

### 11.5 Budget Rollover

- [ ] Calculate unused budget at month end
- [ ] Store rollover amount
- [ ] Add to next month budget
- [ ] Add toggle in Settings

### 11.6 Reports Screen

- [ ] Create ReportsScreen composable
- [ ] Create MonthYearNavigator
- [ ] Display SummaryCard
- [ ] Display CategoryBreakdown table
- [ ] Add ViewChart button
- [ ] Add ExportPDF button

### 11.7 Monthly Summary

- [ ] Calculate total income
- [ ] Calculate total expense
- [ ] Calculate net savings
- [ ] Compare with previous month

### 11.8 Category Breakdown Table

- [ ] Create CategoryBreakdownTable
- [ ] Display category name
- [ ] Display total amount
- [ ] Display percentage
- [ ] Sort by amount descending

### 11.9 PDF Export

- [ ] Create PDF generation logic
- [ ] Add header with app name and date
- [ ] Add summary section
- [ ] Add category breakdown table
- [ ] Add top transactions
- [ ] Implement share via system sheet

### 11.10 Cash Flow Trend

- [ ] Create CashFlowTrendChart
- [ ] Display 6 months data
- [ ] Show income vs expense bars
- [ ] Use category colors

---

## M12: v1.3 Backup/Restore

**Estimasi:** 2-3 hari
**Goal:** Export and import database

### 12.1 Export Backup

- [ ] Create ExportBackup function
- [ ] Get database file path
- [ ] Generate filename: budget_save_YYYYMMDD.db
- [ ] Use Storage Access Framework
- [ ] Copy database to selected location
- [ ] Show success message

### 12.2 Import Backup

- [ ] Create ImportBackup function
- [ ] Open file picker
- [ ] Validate .db file
- [ ] Show warning dialog
- [ ] Copy backup to database location
- [ ] Force close and reopen database

### 12.3 Settings Integration

- [ ] Add Export Backup button in Settings
- [ ] Add Import Backup button in Settings
- [ ] Show loading indicator during backup
- [ ] Handle errors gracefully

### 12.4 User Warnings

- [ ] Show "This will replace all data" dialog
- [ ] Require confirmation before import
- [ ] Show version compatibility warning

### 12.5 Error Handling

- [ ] Handle file not found
- [ ] Handle invalid file format
- [ ] Handle permission denied
- [ ] Show appropriate error messages

---

## M13: v1.4 Recurring Transactions

**Estimasi:** 3-4 hari
**Goal:** Automated recurring transactions

### 13.1 Recurring Entity

- [ ] Create RecurringTransactionEntity
- [ ] Add amount, type, accountId
- [ ] Add categoryId, description
- [ ] Add frequency (DAILY/WEEKLY/MONTHLY)
- [ ] Add nextDate, isActive

### 13.2 Frequency Enum

- [ ] Create RecurringFrequency enum
- [ ] Add DAILY value
- [ ] Add WEEKLY value
- [ ] Add MONTHLY value

### 13.3 Recurring DAO

- [ ] Create RecurringTransactionDao
- [ ] Add CRUD operations
- [ ] Add getActiveRecurring
- [ ] Add getDueRecurring

### 13.4 Recurring Repository

- [ ] Create RecurringTransactionRepository
- [ ] Wrap DAO operations
- [ ] Handle next date calculation

### 13.5 Add Recurring Toggle

- [ ] Add "Make this recurring" toggle
- [ ] Show frequency selector when enabled
- [ ] Show next date picker

### 13.6 Recurring Management Screen

- [ ] Create RecurringListScreen composable
- [ ] Display active recurring transactions
- [ ] Create toggle to activate/deactivate
- [ ] Add Edit button
- [ ] Add Delete button

### 13.7 Background Processing

- [ ] Setup WorkManager
- [ ] Create daily worker
- [ ] Check for due transactions
- [ ] Create transaction when due
- [ ] Update nextDate

### 13.8 Target Date for Goals

- [ ] Add targetDate field to SavingGoalEntity
- [ ] Update AddGoalScreen with date picker
- [ ] Display days remaining
- [ ] Show warning if past due
- [ ] Add overdue styling

---

## M14: Final Testing & Polish

**Estimasi:** 2-3 hari
**Goal:** Bug fixes, performance, release prep

### 14.1 Unit Tests

- [ ] Test CategoryRepository methods
- [ ] Test TransactionRepository methods
- [ ] Test BudgetRepository methods
- [ ] Test SavingGoalRepository methods
- [ ] Test AccountRepository methods
- [ ] Test date helper functions

### 14.2 Integration Tests

- [ ] Test database operations
- [ ] Test transaction flow
- [ ] Test budget calculation
- [ ] Test saving goal completion

### 14.3 UI Tests

- [ ] Test navigation flows
- [ ] Test form validation
- [ ] Test CRUD operations
- [ ] Test filter functionality

### 14.4 Performance Checks

- [ ] Measure app cold start
- [ ] Measure screen transitions
- [ ] Measure database queries
- [ ] Test smooth scrolling

### 14.5 Edge Cases

- [ ] Test empty states for all lists
- [ ] Test large transaction lists (1000+)
- [ ] Test currency overflow
- [ ] Test date edge cases
- [ ] Test database migration

### 14.6 Loading States

- [ ] Implement shimmer effect
- [ ] Implement skeleton cards
- [ ] Implement progress indicators

### 14.7 Error States

- [ ] Implement error messages
- [ ] Implement retry buttons
- [ ] Implement empty state illustrations

### 14.8 Accessibility

- [ ] Add content descriptions
- [ ] Test with TalkBack
- [ ] Verify touch targets (48dp min)
- [ ] Verify color contrast

### 14.9 Release Prep

- [ ] Enable Proguard/R8 minification
- [ ] Optimize APK size
- [ ] Bump version code
- [ ] Bump version name
- [ ] Write release notes

---

## Task Summary

| Milestone | Total Tasks | Status |
|-----------|-------------|--------|
| M1: Project Setup | 18 | [ ] |
| M2: Core Data Layer | 23 | [ ] |
| M3: Transaction Module | 27 | [ ] |
| M4: Category Module | 17 | [ ] |
| M5: Budget Module | 19 | [ ] |
| M6: Saving Goals | 22 | [ ] |
| M7: Dashboard | 17 | [ ] |
| M8: Search & Split | 17 | [ ] |
| M9: Charts & Polish | 15 | [ ] |
| M10: Accounts Module | 22 | [ ] |
| M11: Transfer & Reports | 21 | [ ] |
| M12: Backup/Restore | 12 | [ ] |
| M13: Recurring | 17 | [ ] |
| M14: Final Testing | 20 | [ ] |
| **Total** | **287** | [ ] |

---

*Document Version: 1.0*
*Created: 2026-09-26*
*Status: In Progress*