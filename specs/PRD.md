# Budget & Save — Product Requirements Document

## 1. Overview

**Nama Aplikasi:** Budget & Save

**Tipe:** Aplikasi Android native

**Ringkasan:** Aplikasi pengeloa keuangan personal untuk mencatat transaksi, membuat anggaran, dan melacak tujuan tabungan.

**Target Pengguna:** Individu yang ingin mengelola keuangan pribadi dengan sederhana dan efektif.

---

## 2. Tech Stack

| Komponen | Teknologi |
|----------|-----------|
| Bahasa | Kotlin |
| Min SDK | Android 14 (API 34) |
| Target SDK | Android 14 (API 34) |
| UI | Jetpack Compose |
| Arsitektur | MVVM + Repository |
| DI | Manual (no DI framework) |
| Networking | Retrofit + OkHttp |
| JSON | Kotlin Serialization |
| Database | Room |
| Async | Kotlin Coroutines + Flow |
| Navigation | Navigation Compose |
| Build | Gradle Kotlin DSL + Version Catalog |
| Testing | JUnit + Compose UI Test |
| CI | GitHub Actions (later) |

---

## 3. User Stories

### 3.1 Transaksi

| ID | Cerita | Prioritas | Version |
|----|--------|-----------|---------|
| US-01 | Sebagai pengguna, saya ingin **menambahkan transaksi masuk** (pemasukan) agar saya bisa mencatat sumber dana saya | Must Have | v1.0 |
| US-02 | Sebagai pengguna, saya ingin **menambahkan transaksi keluar** (pengeluaran) agar saya bisa melacak spending saya | Must Have | v1.0 |
| US-03 | Sebagai pengguna, saya ingin **melihat daftar transaksi** berdasarkan tanggal agar saya bisa review riwayat | Must Have | v1.0 |
| US-04 | Sebagai pengguna, saya ingin **mengedit transaksi** jika ada kesalahan input | Should Have | v1.0 |
| US-05 | Sebagai pengguna, saya ingin **menghapus transaksi** jika tidak diperlukan | Should Have | v1.0 |
| US-06 | Sebagai pengguna, saya ingin **mencari transaksi** berdasarkan deskripsi atau jumlah agar mudah ditemukan | Should Have | v1.1 |
| US-07 | Sebagai pengguna, saya ingin **membagi transaksi** ke beberapa kategori sekaligus (split) agar mencatat pengeluaran yang satu tapi涉及 beberapa anggaran | Should Have | v1.1 |
| US-08 | Sebagai pengguna, saya ingin **membuat transaksi berulang otomatis** (harian/mingguan/bulanan) agar tidak perlu input manual berulang | Could Have | v1.4 |

### 3.2 Akun

| ID | Cerita | Prioritas | Version |
|----|--------|-----------|---------|
| US-50 | Sebagai pengguna, saya ingin **membuat beberapa akun** (cash, bank, e-wallet) agar bisa memisahkan sumber dana | Must Have | v1.2 |
| US-51 | Sebagai pengguna, saya ingin **melihat saldo masing-masing akun** agar tahu dana di setiap sumber | Must Have | v1.2 |
| US-52 | Sebagai pengguna, saya ingin **mentransfer dana antar akun** agar bisa memindahkan uang dari satu sumber ke sumber lain | Should Have | v1.2 |

### 3.3 Kategori

| ID | Cerita | Prioritas |
|----|--------|-----------|
| US-10 | Sebagai pengguna, saya ingin **membuat kategori** (contoh: Makanan, Transport, Gaji) agar transaksi terorganisir | Must Have |
| US-11 | Sebagai pengguna, saya ingin **menghapus kategori** yang tidak diperlukan | Should Have |
| US-12 | Sebagai pengguna, saya ingin **melihat total pengeluaran per kategori** | Must Have |

### 3.3 Anggaran (Budget)

| ID | Cerita | Prioritas | Version |
|----|--------|-----------|---------|
| US-20 | Sebagai pengguna, saya ingin **membuat anggaran bulanan** per kategori agar saya tidak overspend | Must Have | v1.0 |
| US-21 | Sebagai pengguna, saya ingin **melihat progress anggaran** (terpakai vs limit) | Must Have | v1.0 |
| US-22 | Sebagai pengguna, saya ingin **mendapat notifikasi** saat hampir mencapai limit anggaran | Should Have | v1.0 |
| US-23 | Sebagai pengguna, saya ingin **membuat anggaran bulanan keseluruhan** agar tahu total batas pengeluaran | Should Have | v1.2 |
| US-24 | Sebagai pengguna, saya ingin **sisa anggaran bulan lalu bisa di-carry over** ke bulan ini agar tidak hilang | Could Have | v1.2 |

### 3.4 Tabungan (Saving)

| ID | Cerita | Prioritas | Version |
|----|--------|-----------|---------|
| US-30 | Sebagai pengguna, saya ingin **membuat tujuan tabungan** (contoh: Liburan, Gadget baru) | Must Have | v1.0 |
| US-31 | Sebagai pengguna, saya ingin **menambahkan saldo ke tujuan tabungan** | Must Have | v1.0 |
| US-32 | Sebagai pengguna, saya ingin **melihat progress tabungan** (terkumpul vs target) | Must Have | v1.0 |
| US-33 | Sebagai pengguna, saya ingin **menandai tujuan tabungan selesai** saat sudah terpenuhi | Should Have | v1.0 |
| US-34 | Sebagai pengguna, saya ingin **menarik saldo dari tujuan tabungan** jika butuh dana mendesak | Should Have | v1.2 |
| US-35 | Sebagai pengguna, saya ingin **menetapkan target date** untuk tujuan tabungan agar ada tenggat waktu | Could Have | v1.2 |

### 3.5 Laporan (Reports)

| ID | Cerita | Prioritas | Version |
|----|--------|-----------|---------|
| US-40 | Sebagai pengguna, saya ingin **melihat ringkasan keuangan** (saldo, pemasukan, pengeluaran bulan ini) | Must Have | v1.0 |
| US-41 | Sebagai pengguna, saya ingin **melihat grafik spending** per bulan | Should Have | v1.0 |
| US-42 | Sebagai pengguna, saya ingin **melihat pie chart pengeluaran per kategori** agar tahu mana yang paling besar | Should Have | v1.1 |
| US-43 | Sebagai pengguna, saya ingin **melihat cash flow trend** (grafik income vs expense per bulan) | Could Have | v1.1 |
| US-44 | Sebagai pengguna, saya ingin **melihat monthly report** berupa rangkuman bulanan | Should Have | v1.2 |
| US-45 | Sebagai pengguna, saya ingin **export laporan ke PDF** agar bisa di-share atau dicetak | Could Have | v1.2 |

---

## 4. Data Model

### 4.1 Entity

```
Account
├── id: Long (PK, auto-generate)
├── name: String
├── type: AccountType (CASH / BANK / E_WALLET)
├── balance: Double
├── icon: String
├── color: String
└── isDefault: Boolean

Transaction
├── id: Long (PK, auto-generate)
├── amount: Double
├── type: TransactionType (INCOME / EXPENSE / TRANSFER)
├── accountId: Long (FK)
├── categoryId: Long (FK, nullable for transfers)
├── description: String
├── date: Long (timestamp)
├── isRecurring: Boolean
├── recurringId: Long (FK, nullable)
└── createdAt: Long

SplitTransaction
├── id: Long (PK, auto-generate)
├── transactionId: Long (FK)
├── categoryId: Long (FK)
└── amount: Double

RecurringTransaction
├── id: Long (PK, auto-generate)
├── amount: Double
├── type: TransactionType
├── accountId: Long (FK)
├── categoryId: Long (FK)
├── description: String
├── frequency: Frequency (DAILY / WEEKLY / MONTHLY)
├── nextDate: Long
├── isActive: Boolean
└── createdAt: Long

Category
├── id: Long (PK, auto-generate)
├── name: String
├── icon: String (emoji/icon name)
└── color: String (hex)

Budget
├── id: Long (PK, auto-generate)
├── categoryId: Long (FK)
├── amount: Double (limit)
├── month: Int
├── year: Int
└── rolloverAmount: Double

OverallBudget
├── id: Long (PK, auto-generate)
├── amount: Double (total monthly limit)
├── month: Int
├── year: Int
└── rolloverAmount: Double

SavingGoal
├── id: Long (PK, auto-generate)
├── name: String
├── targetAmount: Double
├── currentAmount: Double
├── icon: String
├── targetDate: Long (nullable)
└── isCompleted: Boolean
```

### 4.2 Relasi

- Transaction → Account (many-to-one)
- Transaction → Category (many-to-one, optional)
- SplitTransaction → Transaction (many-to-one)
- SplitTransaction → Category (many-to-one)
- RecurringTransaction → Account (many-to-one)
- RecurringTransaction → Category (many-to-one)
- Budget → Category (many-to-one)
- SavingGoal → tidak ada relasi (standalone)

---

## 5. Screen & Navigation

```
App
├── SplashScreen
├── BottomNavigation
│   ├── DashboardScreen (route: dashboard)
│   ├── TransactionsScreen (route: transactions)
│   │   ├── AddEditTransactionScreen (route: transactions/add, transactions/edit/:id)
│   │   └── TransactionDetailScreen (route: transactions/detail/:id)
│   ├── BudgetScreen (route: budget)
│   │   └── AddEditBudgetScreen (route: budget/add, budget/edit/:id)
│   └── SavingScreen (route: saving)
│       ├── SavingDetailScreen (route: saving/detail/:id)
│       └── AddEditSavingGoalScreen (route: saving/add, saving/edit/:id)
├── AccountsScreen (route: accounts)
│   ├── AddEditAccountScreen (route: accounts/add, accounts/edit/:id)
│   └── TransferScreen (route: transfer)
├── CategoriesScreen (route: categories)
├── ReportsScreen (route: reports)
│   └── MonthlyReportScreen (route: reports/monthly/:year/:month)
├── SettingsScreen (route: settings)
│   └── RecurringTransactionsScreen (route: settings/recurring)
└── SearchScreen (route: search)
```

### 5.1 Screen Detail

#### Dashboard
- Balance card per akun (total semua akun + breakdown)
- Summary card (pemasukan & pengeluaran bulan ini)
- Quick add FAB button
- Spending pie chart per kategori
- Recent transactions list (5 terakhir)
- Cash flow trend mini chart

#### Transactions
- Search bar di atas
- Filter by date range, account, category
- List transactions (grouped by date)
- Swipe actions (edit, delete)
- FAB to add new / split transaction

#### Budget
- Overall monthly budget card (jika ada)
- List budget per kategori dengan progress bar
- Rollover indicator jika ada sisa
- FAB to add new budget

#### Saving
- List saving goals dengan progress ring
- Target date indicator
- FAB to add new goal
- Tap goal → detail with withdraw option

#### Accounts
- List all accounts dengan saldo
- Total balance summary
- FAB to add new account
- Tap account → detail with transaction history
- Transfer button

#### Reports
- Monthly summary
- Category breakdown pie chart
- Cash flow trend chart
- Export to PDF button

#### Settings
- Recurring transactions management
- Backup & Restore (.db)
- App info

---

## 6. Functionality Spec

### 6.1 Transaction

**Add Transaction:**
1. User tap FAB atau button "Tambah"
2. Form: jumlah, tipe (pemasukan/pengeluaran), akun, kategori, deskripsi, tanggal
3. Date picker default today
4. Account selector (wajib)
5. Category dropdown dari list yang ada (nullable untuk transfer)
6. Simpan → ke database → back ke list

**Split Transaction (v1.1):**
1. User tap "Split" saat add transaction
2. Input total amount
3. Tambahkan list kategori + jumlah per kategori
4. Validasi: total split = total amount
5. Simpan → create parent transaction + split entries

**Search Transactions (v1.1):**
1. User tap search icon
2. Input keyword (description atau amount)
3. Real-time filtering dari database
4. Hasil tampil dengan highlight keyword

**Recurring Transaction (v1.4):**
1. User toggle "Recurring" saat add/edit transaction
2. Pilih frequency: Daily / Weekly / Monthly
3. Sistem otomatis create transaction di nextDate
4. Background worker check setiap hari untuk create transaksi yang jatuh tempo
5. User bisa manage recurring di Settings

### 6.2 Account (v1.2)

**Add Account:**
1. User tap FAB di Accounts screen
2. Form: nama, tipe (Cash/Bank/E-wallet), icon, color, initial balance
3. Initial balance = seed transaksi income untuk akun baru

**Transfer Between Accounts:**
1. User tap "Transfer" button
2. Pilih akun sumber & akun tujuan
3. Input jumlah
4. Sistem create 2 transactions: EXPENSE dari sumber, INCOME ke tujuan
5. Tipe = TRANSFER (tidak affect budget)

### 6.3 Budget

**Monthly Budget per Category:**
- Budget reset setiap awal bulan
- Progress dihitung dari total transaksi expense di kategori tersebut di bulan tersebut
- Warning jika sudah >80% (kuning) dan >100% (merah)

**Overall Monthly Budget (v1.2):**
- Input total batas pengeluaran bulanan
- Di-hitung dari semua transaksi expense (tidak per kategori)
- Progress bar keseluruhan di dashboard

**Budget Rollover (v1.2):**
- Di akhir bulan, sisa budget dihitung
- Opsi: carry over ke bulan berikutnya atau reset
- User pilih di Settings

### 6.4 Saving Goal

**Add Goal:**
1. Nama tujuan
2. Target jumlah
3. Target date (opsional, v1.2)
4. Icon (dari predefined list)

**Add Saving:**
1. User tap goal → detail screen
2. Input jumlah yang ingin ditambahkan
3. Update currentAmount
4. Jika currentAmount >= targetAmount → otomatis mark completed

**Withdraw from Savings (v1.2):**
1. User tap goal → detail screen
2. Tap "Withdraw"
3. Input jumlah yang ingin ditarik
4. Update currentAmount (dikurangi)
5. Note: withdraw tidak affect transaction (internal only)

### 6.5 Reports (v1.2)

**Monthly Report:**
1. User tap Reports di navigation
2. Pilih bulan/tahun
3. Tampilkan:
   - Total income
   - Total expense
   - Net savings
   - Category breakdown pie chart
   - Cash flow trend (6 bulan terakhir)

**Export to PDF:**
1. User tap "Export PDF" di report
2. Generate PDF dengan konten report
3. Share via system share sheet

### 6.6 Category

**Default Categories (seed data):**
- 🍔 Food & Drinks
- 🚌 Transportation
- 🛒 Shopping
- 🎬 Entertainment
- 💊 Health
- 📚 Education
- 💰 Salary
- 🎁 Others

---

## 7. UI/UX Spec

### 7.1 Theme

**Light Theme:**
- Primary: #2E7D32 (Green 800)
- Secondary: #1565C0 (Blue 800)
- Background: #FAFAFA
- Surface: #FFFFFF
- Error: #D32F2F

**Dark Theme:**
- Primary: #66BB6A (Green 400)
- Secondary: #42A5F5 (Blue 400)
- Background: #121212
- Surface: #1E1E1E
- Error: #EF5350

**Dark mode support:** ✅ Required (follows system setting)

### 7.2 Typography

- Heading 1: 28sp, Bold
- Heading 2: 24sp, SemiBold
- Heading 3: 20sp, Medium
- Body: 16sp, Regular
- Caption: 14sp, Regular
- Label: 12sp, Medium

### 7.3 Spacing

- xs: 4dp
- sm: 8dp
- md: 16dp
- lg: 24dp
- xl: 32dp

### 7.4 Component

**Transaction Item:**
```
┌─────────────────────────────────┐
│ 🍔 Makanan        -Rp 50.000   │
│ Warung Sebelah    25 Sep 2026   │
└─────────────────────────────────┘
```

**Budget Item:**
```
┌─────────────────────────────────┐
│ 🍔 Makanan                      │
│ ████████████░░░░░░░  Rp 400k   │
│      67% terpakai dari Rp 600k  │
└─────────────────────────────────┘
```

**Saving Goal Item:**
```
┌─────────────────────────────────┐
│ 🎒 Liburan             75%      │
│ ████████░░░░░░░░░░░             │
│ Rp 7.500.000 / Rp 10.000.000   │
└─────────────────────────────────┘
```

---

## 8. Technical Spec

### 8.1 Package Structure

```
com.budgetsave
├── data
│   ├── local
│   │   ├── db
│   │   │   ├── AppDatabase
│   │   │   ├── dao
│   │   │   └── entity
│   │   └── repository
│   └── model
├── di
├── domain
│   ├── model
│   └── usecase
├── ui
│   ├── components
│   ├── navigation
│   ├── theme
│   ├── screens
│   │   ├── dashboard
│   │   ├── transactions
│   │   ├── budget
│   │   ├── saving
│   │   ├── categories
│   │   └── settings
│   └── viewmodel
└── util
```

### 8.2 Database Schema (Room)

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

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val type: String, // "INCOME" | "EXPENSE" | "TRANSFER"
    val accountId: Long,
    val categoryId: Long?, // nullable untuk transfer
    val description: String,
    val date: Long,
    val isSplit: Boolean = false,
    val recurringId: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "split_transactions")
data class SplitTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val transactionId: Long,
    val categoryId: Long,
    val amount: Double
)

@Entity(tableName = "recurring_transactions")
data class RecurringTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val type: String, // "INCOME" | "EXPENSE"
    val accountId: Long,
    val categoryId: Long,
    val description: String,
    val frequency: String, // "DAILY" | "WEEKLY" | "MONTHLY"
    val nextDate: Long,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val icon: String,
    val color: String
)

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long,
    val amount: Double,
    val month: Int,
    val year: Int,
    val rolloverAmount: Double = 0.0
)

@Entity(tableName = "overall_budgets")
data class OverallBudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val month: Int,
    val year: Int,
    val rolloverAmount: Double = 0.0
)

@Entity(tableName = "saving_goals")
data class SavingGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val icon: String,
    val targetDate: Long? = null,
    val isCompleted: Boolean = false
)
```

### 8.3 Navigation

```kotlin
sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Transactions : Screen("transactions")
    object AddTransaction : Screen("transactions/add")
    object EditTransaction : Screen("transactions/edit/{id}") {
        fun createRoute(id: Long) = "transactions/edit/$id"
    }
    object Budget : Screen("budget")
    object AddBudget : Screen("budget/add")
    object EditBudget : Screen("budget/edit/{id}") {
        fun createRoute(id: Long) = "budget/edit/$id"
    }
    object Saving : Screen("saving")
    object AddSavingGoal : Screen("saving/add")
    object EditSavingGoal : Screen("saving/edit/{id}") {
        fun createRoute(id: Long) = "saving/edit/$id"
    }
    object Categories : Screen("categories")
    object Settings : Screen("settings")
}
```

---

## 9. Non-Functional Requirements

### 9.1 Performance
- App launch < 2 detik
- Screen transition < 300ms
- Database query < 100ms

### 9.2 Offline-First
- Semua data tersimpan lokal (Room)
- Tidak perlu koneksi internet untuk penggunaan dasar

### 9.3 Data Safety
- Semua data tersimpan lokal (Room)
- Tidak ada sinkronisasi cloud
- User bisa export/import .db file untuk backup manual
- Backup bisa di-share ke device lain

### 9.4 Backup & Restore
- Export: Copy .db file ke user-selected location via SAF atau share sheet
- Import: Replace database dengan backup file
- User warnings sebelum overwrite data
- App restart setelah restore

---

## 10. Out of Scope

- Multi-currency
- Cloud sync (all data stays local)
- Sharing dengan user lain
- Investment tracking
- Bill reminder
- Export to Excel/CSV
- Auto-save dari transaksi ke savings
- Encrypted backup
- Widget
- Apple Watch / Wear OS
- Receipt photo attachment
- Gesture-based transaction entry
- AI-powered insights

**Note:** Fitur-fitur di atas bisa ditambahkan di versi mendatang jika user meminta.

---

## 11. Milestone

| Phase | Fitur | Estimasi | Deliverable |
|-------|-------|----------|-------------|
| **M1** | Project Setup & Foundation | 2-3 hari | APK |
| **M2** | Core Data Layer | 2-3 hari | - |
| **M3** | Transaction Module | 3-4 hari | APK |
| **M4** | Category Module | 1-2 hari | - |
| **M5** | Budget Module | 2-3 hari | APK |
| **M6** | Saving Goals Module | 2-3 hari | APK |
| **M7** | Dashboard | 2-3 hari | APK |
| **M8** | v1.1: Search & Split | 2-3 hari | v1.1 APK |
| **M9** | v1.1: Charts & Polish | 2-3 hari | - |
| **M10** | v1.2: Accounts Module | 3-4 hari | - |
| **M11** | v1.2: Transfer & Reports | 3-4 hari | v1.2 APK |
| **M12** | v1.3: Backup/Restore | 2-3 hari | v1.3 APK |
| **M13** | v1.4: Recurring Transactions | 3-4 hari | v1.4 APK |
| **M14** | Final Testing & Polish | 2-3 hari | Release APK |

**Total estimasi: ~28-38 hari**

### 11.1 Version Timeline

| Version | Fase | Fitur Utama |
|---------|------|------------|
| v1.0 | M1-M7 | Transaction, Category, Budget, Saving, Dashboard |
| v1.1 | M8-M9 | Search, Split Transaction, Pie Chart, Gesture Nav, Haptic |
| v1.2 | M10-M11 | Accounts, Transfer, Overall Budget, Rollover, Reports, PDF |
| v1.3 | M12 | Export/Import .db |
| v1.4 | M13 | Recurring Transactions |

---

## 12. Open Questions

- [x] ~~Recurring transaction~~ → **Out of Scope v1.0**
- [x] Batas transaksi per kategori per bulan? → **Tidak perlu**
- [x] Bahasa UI → **English**
- [x] Dark mode → **Needed (Light + Dark theme)**
- [x] Target Android version minimum? → **Android 14 (API 34)**

---

## 12.1 Recurring Transaction Explanation

**What is it?**
Recurring transaction = transaksi otomatis yang berulang secara periodik (harian/mingguan/bulanan). Contoh: langganan Netflix, gaji bulanan, cicilan.

**When to use?**
- Untuk transaksi固定 (fixed expense/income) yang sama setiap periode
- Membantu tracking subscription & bills

**Is it critical?**
- **NO** untuk v1.0
- Bisa ditambahkan di v1.1+ jika user meminta
- Untuk awal, user bisa input manual setiap kali

---

## 13. Competitive Analysis

### 13.1 Feature Comparison Matrix

| # | Fitur | Budget & Save (Kamu) | Wallet Tracker |
|---|-------|---------------------|----------------|
| **Core Transaction** | | | |
| 1 | Add income | ✅ | ✅ |
| 2 | Add expense | ✅ | ✅ |
| 3 | Edit transaction | ✅ | ✅ |
| 4 | Delete transaction | ✅ | ✅ |
| 5 | View transaction list | ✅ | ✅ |
| 6 | Filter by date range | ✅ | ✅ |
| 7 | Search transactions | ✅ (v1.1) | ✅ |
| 8 | Attach receipt photo | ❌ | ✅ |
| 9 | Split transaction (1x bayar bagi beberapa kategori) | ✅ (v1.1) | ✅ |
| 10 | Recurring transaction | ✅ (v1.4) | ✅ |
| **Category** | | | |
| 11 | Create custom category | ✅ | ✅ |
| 12 | Edit category | ✅ | ✅ |
| 13 | Delete category | ✅ | ✅ |
| 14 | Category icon picker | ✅ | ✅ |
| 15 | Category color picker | ✅ | ✅ |
| 16 | Category with budget limit | ✅ | ✅ |
| **Budget** | | | |
| 17 | Monthly budget per category | ✅ | ✅ |
| 18 | Overall monthly budget | ✅ (v1.2) | ✅ |
| 19 | Budget progress visualization | ✅ | ✅ |
| 20 | Budget warning (80%, 100%) | ✅ | ✅ |
| 21 | Budget rollover (sisa bulan lalu) | ✅ (v1.2) | ✅ |
| **Saving Goals** | | | |
| 22 | Create savings goal | ✅ | ✅ |
| 23 | Add to savings | ✅ | ✅ |
| 24 | Withdraw from savings | ✅ (v1.2) | ✅ |
| 25 | Savings progress ring | ✅ | ✅ |
| 26 | Target date for goal | ✅ (v1.2) | ✅ |
| 27 | Auto-save from transactions | ❌ | ✅ |
| **Dashboard** | | | |
| 28 | Current balance | ✅ | ✅ |
| 29 | Income vs Expense summary | ✅ | ✅ |
| 30 | Monthly spending chart | ✅ | ✅ |
| 31 | Spending by category pie chart | ✅ (v1.1) | ✅ |
| 32 | Cash flow trend | ✅ (v1.1) | ✅ |
| 33 | Quick add FAB | ✅ | ✅ |
| **Reports** | | | |
| 34 | Monthly report | ✅ (v1.2) | ✅ |
| 35 | Category breakdown | ✅ | ✅ |
| 36 | Export to PDF | ✅ (v1.2) | ✅ |
| 37 | Export to Excel/CSV | ❌ | ✅ |
| **Accounts** | | | |
| 38 | Multiple accounts (cash, bank, e-wallet) | ✅ (v1.2) | ✅ |
| 39 | Transfer between accounts | ✅ (v1.2) | ✅ |
| 40 | Account balance tracking | ✅ (v1.2) | ✅ |
| **Sync & Backup** | | | |
| 41 | Cloud sync | ❌ | ✅ |
| 42 | Local .db backup | ✅ (v1.3) | ❌ |
| 43 | Auto backup | ❌ | ✅ |
| **UI/UX** | | | |
| 44 | Dark mode | ✅ | ✅ |
| 45 | Material Design 3 | ✅ | ✅ |
| 46 | Bottom navigation | ✅ | ✅ |
| 47 | Gesture navigation | ✅ (v1.1) | ✅ |
| 48 | Haptic feedback | ✅ (v1.1) | ✅ |

### 13.2 Summary

| Metric | Budget & Save | Competitor |
|--------|---------------|------------|
| Core features | 48/48 (100%) | 48/48 (100%) |
| Complexity | Simple → Full | Complex |
| Data storage | Local only | Cloud + Local |
| Multi-account | ✅ (v1.2) | ✅ |

### 13.3 Strategic Position

**Budget & Save:** Fokus ke simplicity
- ✅ Clean, tidak overwhelming
- ✅ Easy to use untuk pemula
- ✅ Focus ke esensi: catat, budget, tabung
- ❌ Fitur terbatas dibanding kompetitor

**Competitor:** Full-featured all-in-one
- ✅ Fitur lengkap untuk power user
- ✅ Cloud sync = access anywhere
- ❌ Complex, overwhelming untuk newbie
- ❌ Need account setup

### 13.4 Strategic Roadmap

**Vision:** Budget & Save = kompetitor penuh untuk Wallet Budget Expense Tracker, tapi dengan:
- ✅ Simple, clean UI
- ✅ No cloud, full local control
- ✅ No account required

**Perilisan bertahap:**

| Version | Focus | Fitur Utama |
|---------|-------|------------|
| v1.0 | **Core MVP** | Transaction, Category, Budget, Saving, Dashboard |
| v1.1 | **UX Enhancement** | Search, Split, Charts, Polish |
| v1.2 | **Full Feature** | Accounts, Transfer, Reports, PDF |
| v1.3 | **Backup** | Export/Import .db |
| v1.4 | **Automation** | Recurring transactions |

---

*Document version: 2.0*
*Updated: 2026-09-26*

### 14.1 Philosophy

**No cloud, full local control.** User punya ownership penuh atas data mereka.

Backup = copy file `.db` biasa yang bisa di-share ke device lain.

### 14.2 Implementation

**Backup Flow:**
```
Settings → "Export Backup" → User selects location → Save as "budget_save_YYYYMMDD.db"
```

**Restore Flow:**
```
Settings → "Import Backup" → Select .db file → Confirm overwrite → App restart
```

### 14.3 Technical Approach

```kotlin
// Export: copy Room database file to user-selected location
// Database file: /data/data/com.budgetsave/databases/budget_save.db

// Using SAF (Storage Access Framework):
// 1. User picks destination folder
// 2. Copy .db file to that location

// Using Share Sheet:
// 1. Export → Share as file → User picks app (email, Drive, etc.)
```

### 14.4 Restore Process

```kotlin
// 1. Copy backup file to app's database directory
// 2. Close database connection
// 3. Replace current .db with backup
// 4. Reopen database
// 5. Show success message + restart prompt
```

### 14.5 User Warnings

- ⚠️ "This will replace ALL current data"
- ⚠️ "Backup and app must be same version"
- ⚠️ After restore: app auto-restarts

### 14.6 Out of Scope (v1.0)

- ❌ Cloud sync (Firebase, Supabase, etc.)
- ❌ Encrypted backup
- ❌ Selective backup (transactions only, categories only)
- ❌ Auto-backup schedule
- ❌ Backup to specific cloud folder manually

---

*Document version: 2.0*
*Updated: 2026-09-26*
*Status: Full feature set aligned with competitor*
