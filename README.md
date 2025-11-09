# Personal Finance Tracker - Week 3 Implementation
**Student:** Haley Altaie  
**Course:** SDC330  
**Project Phase:** Class Implementation (Week 3)

## Project Overview
Personal Finance Tracker is a Java application for managing personal finances through tracking income, expenses, budgets, and savings goals.

## Files Included
1. **Reportable.java** - Interface for report generation
2. **Transaction.java** - Abstract base class for all transactions
3. **Income.java** - Income transaction class
4. **Expense.java** - Expense transaction class
5. **Category.java** - Transaction category class
6. **Budget.java** - Budget management class
7. **Goal.java** - Financial goal tracking class
8. **FinanceTrackerApp.java** - Main application with menu system

## OOP Concepts Demonstrated

### Interface
- `Reportable` interface implemented by `Budget` class
- Defines `generateReport()` method contract

### Abstract Class
- `Transaction` is abstract base class
- Contains abstract method `getTransactionType()`
- Cannot be instantiated directly

### Inheritance
- `Income` extends `Transaction`
- `Expense` extends `Transaction`
- Both inherit properties and methods from `Transaction`

### Polymorphism
- `ArrayList<Transaction>` can hold both `Income` and `Expense` objects
- Each calls its own overridden `getTransactionType()` and `toString()` methods
- Demonstrated in menu options

### Composition
- `Budget` HAS-A `Category` (composition relationship)
- Budget contains a Category object as a property

### Constructors
- Multiple constructors per class (overloading)
- Constructor chaining with `super()`
- Both with and without ID for database flexibility

### Access Specifiers
- `private` properties in all classes
- `public` getters and setters
- `protected` constructor in `Transaction` (only accessible to subclasses)

## How to Compile and Run

```bash
# Compile all files
javac *.java

# Run the application
java FinanceTrackerApp
```

## Application Features (Week 3)
- View all transactions (demonstrates polymorphism)
- Add new transactions (Income or Expense)
- View budgets with spending tracking
- View financial goals with progress
- Specific demonstrations of polymorphism and composition
- Test data pre-loaded for demonstration

## Note
Database functionality will be added in Week 4. Currently using ArrayLists for data storage.

## Requirements Met
✓ Interface implementation (Reportable)  
✓ Abstract class (Transaction)  
✓ Inheritance (Income/Expense extend Transaction)  
✓ Polymorphism (Transaction hierarchy)  
✓ Composition (Budget HAS-A Category)  
✓ Constructors (multiple per class)  
✓ Access Specifiers (public/private/protected mix)  
✓ Proper documentation and formatting  
✓ Working application with terminal I/O
