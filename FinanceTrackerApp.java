/*******************************************************************
* Name: Haley Altaie
* Date: 11/02/25
* Assignment: SDC330 Course Project - Week 4
*
* Main application class for Personal Finance Tracker with database support.
* Demonstrates: All OOP concepts integrated with database persistence
*/
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class FinanceTrackerApp {
    private Scanner scanner;
    private Connection conn;
    private static final String DB_NAME = "finance_tracker.db";

    public FinanceTrackerApp() {
        scanner = new Scanner(System.in);
        initializeDatabase();
    }

    // Initialize database and create tables
    private void initializeDatabase() {
        conn = SQLiteDatabase.connect(DB_NAME);
        
        if (conn != null) {
            System.out.println("Database connected successfully!");
            CategoryDB.createTable(conn);
            TransactionDB.createTable(conn);
            BudgetDB.createTable(conn);
            GoalDB.createTable(conn);
            System.out.println("All tables created/verified.\n");
            
            // Add default categories if database is empty
            if (CategoryDB.getAllCategories(conn).isEmpty()) {
                initializeDefaultCategories();
            }
        } else {
            System.out.println("Failed to connect to database!");
        }
    }

    private void initializeDefaultCategories() {
        System.out.println("Initializing default categories...");
        CategoryDB.addCategory(conn, new Category("Salary", "Income"));
        CategoryDB.addCategory(conn, new Category("Freelance", "Income"));
        CategoryDB.addCategory(conn, new Category("Groceries", "Expense"));
        CategoryDB.addCategory(conn, new Category("Utilities", "Expense"));
        CategoryDB.addCategory(conn, new Category("Entertainment", "Expense"));
        CategoryDB.addCategory(conn, new Category("Transportation", "Expense"));
        System.out.println("Default categories added!\n");
    }

    public void run() {
        System.out.println("===========================================");
        System.out.println("  Personal Finance Tracker - Haley Altaie");
        System.out.println("  SDC330 Course Project - Week 4");
        System.out.println("===========================================\n");

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    transactionMenu();
                    break;
                case 2:
                    budgetMenu();
                    break;
                case 3:
                    goalMenu();
                    break;
                case 4:
                    categoryMenu();
                    break;
                case 0:
                    System.out.println("\nThank you for using Personal Finance Tracker!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Transaction Management");
        System.out.println("2. Budget Management");
        System.out.println("3. Goal Management");
        System.out.println("4. Category Management");
        System.out.println("0. Exit");
        System.out.println("===============================");
    }

    // ========== TRANSACTION MENU ==========
    private void transactionMenu() {
        System.out.println("\n========== TRANSACTION MENU ==========");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View All Transactions");
        System.out.println("4. Update Transaction");
        System.out.println("5. Delete Transaction");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addIncome();
                break;
            case 2:
                addExpense();
                break;
            case 3:
                viewAllTransactions();
                break;
            case 4:
                updateTransaction();
                break;
            case 5:
                deleteTransaction();
                break;
        }
    }

    private void addIncome() {
        System.out.println("\n========== ADD INCOME ==========");
        
        System.out.print("Enter amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter source: ");
        String source = scanner.nextLine();
        
        displayCategories("Income");
        System.out.print("Enter category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        
        // Demonstrates polymorphism - creating Income object
        Income income = new Income(amount, date, description, categoryId, source);
        TransactionDB.addTransaction(conn, income);
        
        System.out.println("\nIncome added successfully!");
    }

    private void addExpense() {
        System.out.println("\n========== ADD EXPENSE ==========");
        
        System.out.print("Enter amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter payment method: ");
        String paymentMethod = scanner.nextLine();
        
        displayCategories("Expense");
        System.out.print("Enter category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        
        // Demonstrates polymorphism - creating Expense object
        Expense expense = new Expense(amount, date, description, categoryId, paymentMethod);
        TransactionDB.addTransaction(conn, expense);
        
        System.out.println("\nExpense added successfully!");
    }

    private void viewAllTransactions() {
        System.out.println("\n========== ALL TRANSACTIONS ==========");
        ArrayList<Transaction> transactions = TransactionDB.getAllTransactions(conn);
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                // Demonstrates polymorphism - calling overridden toString()
                System.out.println("ID: " + t.getId() + " | " + t.toString());
            }
        }
    }

    private void updateTransaction() {
        System.out.print("Enter transaction ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Transaction transaction = TransactionDB.getTransaction(conn, id);
        
        if (transaction == null) {
            System.out.println("Transaction not found!");
            return;
        }
        
        System.out.println("Current transaction: " + transaction.toString());
        System.out.print("Enter new amount (current: $" + transaction.getAmount() + "): $");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        transaction.setAmount(amount);
        TransactionDB.updateTransaction(conn, transaction);
        
        System.out.println("Transaction updated successfully!");
    }

    private void deleteTransaction() {
        System.out.print("Enter transaction ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        TransactionDB.deleteTransaction(conn, id);
        System.out.println("Transaction deleted successfully!");
    }

    // ========== BUDGET MENU ==========
    private void budgetMenu() {
        System.out.println("\n========== BUDGET MENU ==========");
        System.out.println("1. Add Budget");
        System.out.println("2. View All Budgets");
        System.out.println("3. Update Budget");
        System.out.println("4. Delete Budget");
        System.out.println("5. Generate Budget Report");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addBudget();
                break;
            case 2:
                viewAllBudgets();
                break;
            case 3:
                updateBudget();
                break;
            case 4:
                deleteBudget();
                break;
            case 5:
                generateBudgetReport();
                break;
        }
    }

    private void addBudget() {
        System.out.println("\n========== ADD BUDGET ==========");
        
        displayCategories("Expense");
        System.out.print("Enter category ID: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        
        Category category = CategoryDB.getCategory(conn, categoryId);
        
        System.out.print("Enter monthly limit: $");
        double limit = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Enter month (YYYY-MM): ");
        String month = scanner.nextLine();
        
        // Demonstrates composition - Budget HAS-A Category
        Budget budget = new Budget(category, limit, month);
        BudgetDB.addBudget(conn, budget);
        
        System.out.println("\nBudget added successfully!");
    }

    private void viewAllBudgets() {
        System.out.println("\n========== ALL BUDGETS ==========");
        ArrayList<Budget> budgets = BudgetDB.getAllBudgets(conn);
        
        if (budgets.isEmpty()) {
            System.out.println("No budgets found.");
        } else {
            for (Budget b : budgets) {
                System.out.println("ID: " + b.getId() + " | " + b.toString());
            }
        }
    }

    private void updateBudget() {
        System.out.print("Enter budget ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Budget budget = BudgetDB.getBudget(conn, id);
        
        if (budget == null) {
            System.out.println("Budget not found!");
            return;
        }
        
        System.out.println("Current budget: " + budget.toString());
        System.out.print("Enter new monthly limit (current: $" + budget.getMonthlyLimit() + "): $");
        double limit = scanner.nextDouble();
        scanner.nextLine();
        
        budget.setMonthlyLimit(limit);
        BudgetDB.updateBudget(conn, budget);
        
        System.out.println("Budget updated successfully!");
    }

    private void deleteBudget() {
        System.out.print("Enter budget ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        BudgetDB.deleteBudget(conn, id);
        System.out.println("Budget deleted successfully!");
    }

    private void generateBudgetReport() {
        System.out.print("Enter budget ID for report: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Budget budget = BudgetDB.getBudget(conn, id);
        
        if (budget == null) {
            System.out.println("Budget not found!");
            return;
        }
        
        // Demonstrates interface implementation
        System.out.println("\n" + budget.generateReport());
    }

    // ========== GOAL MENU ==========
    private void goalMenu() {
        System.out.println("\n========== GOAL MENU ==========");
        System.out.println("1. Add Goal");
        System.out.println("2. View All Goals");
        System.out.println("3. Update Goal");
        System.out.println("4. Delete Goal");
        System.out.println("5. Add Contribution to Goal");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addGoal();
                break;
            case 2:
                viewAllGoals();
                break;
            case 3:
                updateGoal();
                break;
            case 4:
                deleteGoal();
                break;
            case 5:
                addContributionToGoal();
                break;
        }
    }

    private void addGoal() {
        System.out.println("\n========== ADD GOAL ==========");
        
        System.out.print("Enter goal name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter target amount: $");
        double target = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Enter deadline (YYYY-MM-DD): ");
        String deadline = scanner.nextLine();
        
        Goal goal = new Goal(name, target, deadline);
        GoalDB.addGoal(conn, goal);
        
        System.out.println("\nGoal added successfully!");
    }

    private void viewAllGoals() {
        System.out.println("\n========== ALL GOALS ==========");
        ArrayList<Goal> goals = GoalDB.getAllGoals(conn);
        
        if (goals.isEmpty()) {
            System.out.println("No goals found.");
        } else {
            for (Goal g : goals) {
                System.out.println("ID: " + g.getId() + " | " + g.toString());
            }
        }
    }

    private void updateGoal() {
        System.out.print("Enter goal ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Goal goal = GoalDB.getGoal(conn, id);
        
        if (goal == null) {
            System.out.println("Goal not found!");
            return;
        }
        
        System.out.println("Current goal: " + goal.toString());
        System.out.print("Enter new target amount (current: $" + goal.getTargetAmount() + "): $");
        double target = scanner.nextDouble();
        scanner.nextLine();
        
        goal.setTargetAmount(target);
        GoalDB.updateGoal(conn, goal);
        
        System.out.println("Goal updated successfully!");
    }

    private void deleteGoal() {
        System.out.print("Enter goal ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        GoalDB.deleteGoal(conn, id);
        System.out.println("Goal deleted successfully!");
    }

    private void addContributionToGoal() {
        System.out.print("Enter goal ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Goal goal = GoalDB.getGoal(conn, id);
        
        if (goal == null) {
            System.out.println("Goal not found!");
            return;
        }
        
        System.out.print("Enter contribution amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        goal.addContribution(amount);
        GoalDB.updateGoal(conn, goal);
        
        System.out.println("Contribution added! New progress: " + goal.getProgressPercentage() + "%");
    }

    // ========== CATEGORY MENU ==========
    private void categoryMenu() {
        System.out.println("\n========== CATEGORY MENU ==========");
        System.out.println("1. Add Category");
        System.out.println("2. View All Categories");
        System.out.println("3. Delete Category");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                addCategory();
                break;
            case 2:
                viewAllCategories();
                break;
            case 3:
                deleteCategory();
                break;
        }
    }

    private void addCategory() {
        System.out.println("\n========== ADD CATEGORY ==========");
        
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter type (Income/Expense): ");
        String type = scanner.nextLine();
        
        Category category = new Category(name, type);
        CategoryDB.addCategory(conn, category);
        
        System.out.println("\nCategory added successfully!");
    }

    private void viewAllCategories() {
        System.out.println("\n========== ALL CATEGORIES ==========");
        ArrayList<Category> categories = CategoryDB.getAllCategories(conn);
        
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            for (Category c : categories) {
                System.out.println("ID: " + c.getId() + " | " + c.toString());
            }
        }
    }

    private void deleteCategory() {
        System.out.print("Enter category ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        CategoryDB.deleteCategory(conn, id);
        System.out.println("Category deleted successfully!");
    }

    // Helper method to display categories
    private void displayCategories(String type) {
        System.out.println("\nAvailable " + type + " Categories:");
        ArrayList<Category> categories = CategoryDB.getAllCategories(conn);
        for (Category c : categories) {
            if (c.getType().equals(type)) {
                System.out.println("ID: " + c.getId() + " - " + c.getName());
            }
        }
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    public static void main(String[] args) {
        FinanceTrackerApp app = new FinanceTrackerApp();
        app.run();
    }
}
