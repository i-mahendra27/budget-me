# Budget & Save - Design Document

Dokumen ini berisi spesifikasi desain untuk aplikasi Budget & Save.

**Target:** Android 14 (API 34)
**Versi Dokumen:** 1.0
**Tanggal:** 2026-09-26

---

## 1. Design Principles

### 1.1 Core Principles

1. **Simple & Focused** - Clean interface, no clutter
2. **Glanceable** - Key info visible at a glance
3. **Intuitive** - No learning curve needed
4. **Fast** - Quick access to common actions
5. **Delightful** - Smooth animations, haptic feedback

### 1.2 Design Language

- **Style:** Material Design 3 (Material You)
- **Shape:** Rounded corners (medium radius)
- **Elevation:** Subtle shadows, surface tint
- **Motion:** Purposeful, physics-based

---

## 2. Visual Design System

### 2.1 Brand Identity

**App Name:** Budget & Save
**Tagline:** Track, Budget, Save

### 2.2 App Icon

- Background: Green gradient (#2E7D32 to #4CAF50)
- Icon: Wallet with upward arrow (savings)
- Shape: Rounded square (Android adaptive icon)
- Size: 108x108dp (plus safe zone)

---

## 3. Color System

### 3.1 Light Theme

| Token | Hex | Usage |
|-------|-----|-------|
| Primary | #2E7D32 | Main actions, FAB |
| OnPrimary | #FFFFFF | Text on primary |
| PrimaryContainer | #A5D6A7 | Primary backgrounds |
| Secondary | #1565C0 | Secondary actions |
| Background | #FAFAFA | Screen backgrounds |
| Surface | #FFFFFF | Cards, sheets |
| SurfaceVariant | #E7E0EC | Subtle backgrounds |
| Outline | #79747E | Borders, dividers |
| Error | #D32F2F | Error states |
| Income | #4CAF50 | Income amounts (green) |
| Expense | #F44336 | Expense amounts (red) |

### 3.2 Dark Theme

| Token | Hex | Usage |
|-------|-----|-------|
| Primary | #66BB6A | Main actions |
| OnPrimary | #003300 | Text on primary |
| PrimaryContainer | #1B5E20 | Primary backgrounds |
| Secondary | #42A5F5 | Secondary actions |
| Background | #121212 | Screen backgrounds |
| Surface | #1E1E1E | Cards, sheets |
| SurfaceVariant | #49454F | Subtle backgrounds |
| Outline | #938F99 | Borders, dividers |
| Error | #EF5350 | Error states |
| Income | #81C784 | Income amounts (green) |
| Expense | #EF9A9A | Expense amounts (red) |

### 3.3 Chart Colors

| Category | Light | Dark |
|----------|-------|------|
| Food and Drinks | #FF9800 | #FFB74D |
| Transportation | #2196F3 | #64B5F6 |
| Shopping | #E91E63 | #F06292 |
| Entertainment | #9C27B0 | #BA68C8 |
| Health | #F44336 | #EF5350 |
| Education | #3F51B5 | #7986CB |
| Salary | #4CAF50 | #81C784 |
| Others | #607D8B | #90A4AE |

### 3.4 Budget Progress Colors

| Percentage | Light | Dark | Label |
|------------|-------|------|-------|
| 0-50% | #4CAF50 | #81C784 | Safe |
| 51-80% | #FFC107 | #FFD54F | Caution |
| 81-99% | #FF9800 | #FFB74D | Warning |
| 100%+ | #F44336 | #EF5350 | Over budget |

---

## 4. Typography

### 4.1 Type Scale

| Style | Size | Weight | Usage |
|-------|------|--------|-------|
| Headline Large | 32sp | 400 | Screen titles |
| Headline Medium | 28sp | 400 | Section headers |
| Headline Small | 24sp | 400 | Card titles |
| Title Large | 22sp | 400 | List item titles |
| Title Medium | 16sp | 500 | Subtitles |
| Body Large | 16sp | 400 | Primary text |
| Body Medium | 14sp | 400 | Secondary text |
| Body Small | 12sp | 400 | Captions |
| Label Large | 14sp | 500 | Buttons |
| Label Medium | 12sp | 500 | Chips, tabs |

### 4.2 Amount Display

| Style | Size | Weight | Format |
|-------|------|--------|--------|
| Balance Large | 36sp | 700 | Rp 15,750,000 |
| Balance Medium | 28sp | 600 | Rp 1,500,000 |
| Amount Large | 24sp | 500 | +Rp 500,000 |
| Amount Medium | 18sp | 500 | Rp 50,000 |
| Amount Small | 14sp | 400 | Rp 10,000 |

---

## 5. Spacing and Layout

### 5.1 Spacing Scale

| Token | Value | Usage |
|-------|-------|-------|
| xs | 4dp | Icon padding |
| sm | 8dp | Tight spacing |
| md | 16dp | Default padding |
| lg | 24dp | Section spacing |
| xl | 32dp | Large gaps |
| xxl | 48dp | Screen margins |

### 5.2 Corner Radius

| Token | Value | Usage |
|-------|-------|-------|
| small | 4dp | Chips, small buttons |
| medium | 8dp | Cards, inputs |
| large | 12dp | Bottom sheets |
| extraLarge | 16dp | Modal dialogs |
| full | 50% | FAB, avatars |

### 5.3 Elevation

| Level | Usage |
|-------|-------|
| Level 0 | Flat surfaces |
| Level 1 | Cards at rest |
| Level 2 | Cards on hover/focus |
| Level 3 | FAB, floating elements |
| Level 4 | Dialogs, modals |

---

## 6. Components

### 6.1 Buttons

**Filled Button (Primary)**
- Background: Primary
- Text: OnPrimary
- Corner: medium (8dp)
- Height: 40dp
- Usage: Main actions (Save, Add)

**Outlined Button**
- Border: Outline (1dp)
- Text: Primary
- Corner: medium (8dp)
- Height: 40dp
- Usage: Secondary actions (Cancel)

**Text Button**
- Text: Primary
- No background
- Usage: Tertiary actions

**FAB (Floating Action Button)**
- Background: Primary
- Size: 56dp
- Corner: full (28dp)
- Elevation: Level 3
- Usage: Add new items

### 6.2 Input Fields

**Text Field**
- Background: Surface
- Border: Outline (unfocused) / Primary (focused)
- Corner: medium (8dp)
- Height: 56dp
- States: Default, Focused, Error, Disabled

**Amount Input**
- Same as Text Field
- Prefix: Rp
- Keyboard: Number
- Format: Thousand separators

### 6.3 Cards

**Transaction Card**
- Min Height: 72dp
- Padding: 16dp
- Corner: medium (8dp)
- Elevation: Level 1

**Budget Card**
- Min Height: 100dp
- Padding: 16dp
- Corner: medium (8dp)
- Elevation: Level 1

**Saving Goal Card**
- Min Height: 120dp
- Padding: 16dp
- Corner: medium (8dp)
- Elevation: Level 1

### 6.4 Progress Indicators

**Linear Progress Bar**
- Height: 8dp
- Corner: full (4dp)
- Background: SurfaceVariant
- Animation: 300ms ease-out

**Circular Progress Ring**
- Diameter: 48dp (small), 120dp (large)
- Stroke: 4dp (small), 8dp (large)
- Background: SurfaceVariant

**Spending Pie Chart**
- Diameter: 200dp
- Segments: Category colors
- Animation: 500ms staggered
- Legend: Below chart

### 6.5 Navigation

**Bottom Navigation Bar**
- Height: 80dp (with labels)
- Background: Surface
- Indicator: PrimaryContainer
- Items: Dashboard, Transactions, Budget, Saving

**Top App Bar**
- Height: 56dp
- Background: Surface
- Title: Headline Small
- Actions: Icons (24dp)

### 6.6 Chips

**Filter Chip**
- Height: 32dp
- Corner: small (4dp)
- Background: SurfaceVariant (unselected) / PrimaryContainer (selected)

---

## 7. Screen Designs

### 7.1 Dashboard Screen

Layout:
- App Bar with title and menu
- Balance Card (Current Balance, Income/Expense summary)
- Spending Pie Chart with legend
- Recent Transactions (last 5)
- FAB for adding new transaction
- Bottom Navigation (4 items)

### 7.2 Transactions Screen

Layout:
- App Bar with search icon
- Filter Chips (All, Income, Expense)
- Date Range picker
- Transaction List grouped by date
- FAB for adding new transaction
- Bottom Navigation

### 7.3 Add/Edit Transaction Screen

Layout:
- App Bar with back and save button
- Amount Input (Rp prefix)
- Type Selector (Income/Expense)
- Category Dropdown
- Description Input
- Date Picker
- Split Transaction option

### 7.4 Budget Screen

Layout:
- Month/Year Navigator
- Overall Budget Card (if set)
- Budget List with progress bars
- Progress Color: Green (0-50%), Yellow (51-80%), Orange (81-99%), Red (100%+)
- Warning indicators at 80% and 100%
- FAB for adding new budget

### 7.5 Saving Goals Screen

Layout:
- App Bar with title
- Active Goals section
- Completed Goals section
- Goal Card with Progress Ring
- FAB for adding new goal

### 7.6 Accounts Screen (v1.2)

Layout:
- App Bar with title
- Total Balance summary
- Grouped by Account Type (Bank, E-Wallet, Cash)
- Account Card with balance
- Add Account and Transfer buttons

### 7.7 Reports Screen (v1.2)

Layout:
- App Bar with back
- Month/Year Navigator
- Summary Card (Income, Expense, Net)
- Category Breakdown Table
- View Chart and Export PDF buttons

### 7.8 Settings Screen

Layout:
- App Bar with back
- Account section (Default Account)
- Budget section (Budget Rollover toggle)
- Automation section (Recurring Transactions)
- Data section (Export/import Backup)
- About section (Version, Rate App, Privacy Policy)

---

## 8. Navigation Design

### 8.1 Navigation Structure

- App
  - Bottom Navigation
    - Dashboard (startDestination)
    - Transactions
    - Budget
    - Saving Goals
  - Top Level
    - Categories
    - Accounts (v1.2)
    - Reports (v1.2)
    - Settings
    - Search
  - Detail Screens
    - Add/Edit Transaction
    - Add/Edit Budget
    - Add/Edit Saving Goal
    - Saving Goal Detail
    - Add/Edit Account (v1.2)
    - Transfer (v1.2)
  - Modals
    - Date Picker
    - Category Picker
    - Confirm Delete Dialog
    - Add Savings Dialog

### 8.2 Navigation Transitions

| Transition | Type | Duration |
|------------|------|----------|
| Screen to screen | Slide horizontal | 300ms |
| Modal open | Slide up | 250ms |
| Modal close | Slide down | 200ms |
| Tab switch | Fade | 150ms |
| FAB to screen | Fade + scale | 250ms |

---

## 9. Interaction and Animation

### 9.1 Gestures

| Gesture | Screen | Action |
|---------|--------|--------|
| Swipe left | Transaction item | Delete with confirmation |
| Long press | Any item | Show context menu |

### 9.2 Animations

| Animation | Duration | Easing |
|-----------|----------|--------|
| Button press | 100ms | FastOutSlowIn |
| Card appear | 200ms | FastOutSlowIn |
| Card dismiss | 150ms | SlowOutFastIn |
| Progress update | 500ms | FastOutSlowIn |
| List item stagger | 50ms delay | - |
| Modal enter | 250ms | FastOutSlowIn |
| Modal exit | 200ms | SlowOutFastIn |
| Page transition | 300ms | FastOutSlowIn |

### 9.3 Haptic Feedback

| Action | Feedback Type |
|--------|---------------|
| Button tap | Short click |
| Transaction saved | Success (double click) |
| Transaction deleted | Warning |
| Budget warning | Triple click |
| Goal completed | Celebration pattern |

---

## 10. States and Empty States

### 10.1 Loading State

- Shimmer effect for lists
- Skeleton cards matching content shape

### 10.2 Empty States

**No Transactions**
- Receipt icon
- Title: No transactions yet
- Subtitle: Start tracking your finances
- CTA: Add Transaction button

**No Budgets**
- Wallet icon
- Title: No budgets set
- Subtitle: Create budgets to track spending limits
- CTA: Add Budget button

**No Saving Goals**
- Savings icon
- Title: No saving goals yet
- Subtitle: Start saving for your dreams
- CTA: Add Goal button

### 10.3 Error States

- Error icon
- Title: Something went wrong
- CTA: Try Again button

---

## 11. Accessibility

### 11.1 Content Descriptions

| Element | Description |
|---------|-------------|
| App icon | Budget and Save app logo |
| Amount positive | Income amount |
| Amount negative | Expense amount |
| Progress bar | Percentage complete |
| FAB | Add new transaction |

### 11.2 Touch Targets

- Minimum size: 48x48dp
- Recommended: 56x56dp
- List items: min 72dp height

### 11.3 Color Contrast

- Text on background: 4.5:1 minimum
- Large text: 3:1 minimum
- UI components: 3:1 minimum

---

## 12. Dark Mode

### 12.1 Implementation

- Follows system setting via uiThemeMode
- Automatic switching based on system preference
- No manual toggle (system controlled)

### 12.2 Dark Mode Considerations

- Avoid pure black (#000000) for backgrounds
- Use surface colors for elevation
- Reduce brightness, not saturation
- Maintain contrast ratios

---

## Appendix A: Iconography

### Navigation Icons
- Dashboard: home
- Transactions: receipt_long
- Budget: account_balance_wallet
- Saving: savings
- Settings: settings
- Search: search
- Back: arrow_back
- Add: add
- Edit: edit
- Delete: delete

### Category Icons
- Food: restaurant, local_cafe
- Transport: directions_car, flight
- Shopping: shopping_bag, shopping_cart
- Entertainment: movie, music_note
- Health: medical_services, fitness_center
- Education: school, book
- Salary: payments
- Other: more_horiz

### Account Icons
- Cash: payments
- Bank: account_balance
- E-Wallet: wallet

---

*Document Version: 1.0*
*Created: 2026-09-26*
*Status: Complete