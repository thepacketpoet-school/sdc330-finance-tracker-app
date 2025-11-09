/*******************************************************************
* Name: Haley Altaie
* Date: 10/26/25
* Assignment: SDC330 Course Project - Week 3
*
* Main application class for Personal Finance Tracker.
* Demonstrates: All OOP concepts integrated
*/
import java.util.ArrayList;
import java.util.Scanner;

public class FinanceTrackerApp {
    private Scanner scanner;
    private ArrayList<Transaction> transactions;
    private ArrayList<Category> categories;
    private ArrayList<Budget> budgets;
    private ArrayList<Goal> goals;

    public FinanceTrackerApp() {
        scanner = new Scanner(System.in);
        transactions = new ArrayList<>();
        categories = new ArrayList<>();
        budgets = new ArrayList<>();
        goals = new ArrayList<>();
        initializeTestData();
    }

    // Initialize with some test data to demonstrate functionality
    private void initializeTestData() {
        // Create some categories
        categories.add(new Category(1, "Salary", "Income"));
        categories.add(new Category(2, "Groceries", "Expense"));
        categories.add(new Category(3, "Utilities", "Expense"));
        categories.add(new Category(4, "Entertainment", "Expense"));

        // Create some sample transactions
        // Demonstrates: Polymorphism - ArrayList of Transaction holding Income and Expense
        transactions.add(new Income(3500.00, "2025-11-01", "Monthly Salary", 1, "Employer"));
        transactions.add(new Expense(125.50, "2025-11-03", "Weekly Groceries", 2, "Credit Card"));
        transactions.add(new Expense(85.00, "2025-11-05", "Electric Bill", 3, "Auto-Pay"));
        transactions.add(new Expense(45.00, "2025-11-07", "Movie Night", 4, "Cash"));

        // Create sample budgets
        // Demonstrates: Composition - Budget contains Category objects
        budgets.add(new Budget(1, categories.get(1), 500.00, 125.50, "2025-11"));
        budgets.add(new Budget(2, categories.get(2), 200.00, 85.00, "2025-11"));

        // Create sample goals
        goals.add(new Goal(1, "Emergency Fund", 10000.00, 2500.00, "2026-12-31", "Active"));
        goals.add(new Goal(2, "Vacation Savings", 3000.00, 1200.00, "2026-06-30", "Active"));
    }

    public void run() {
        System.out.println("===========================================");
        System.out.println("  Personal Finance Tracker - Haley Altaie");
        System.out.println("  SDC330 Course Project - Week 3");
        System.out.println("===========================================\n");

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewTransactions();
                    break;
                case 2:
                    addTransactionDemo();
                    break;
                case 3:
                    viewBudgets();
                    break;
                case 4:
                    viewGoals();
                    break;
                case 5:
                    demonstratePolymorphism();
                    break;
                case 6:
                    demonstrateComposition();
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
        System.out.println("1. View Transactions");
        System.out.println("2. Add Transaction (Demo)");
        System.out.println("3. View Budgets");
        System.out.println("4. View Goals");
        System.out.println("5. Demonstrate Polymorphism");
        System.out.println("6. Demonstrate Composition");
        System.out.println("0. Exit");
        System.out.println("===============================");
    }

    private void viewTransactions() {
        System.out.println("\n========== ALL TRANSACTIONS ==========");
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                // Demonstrates: Polymorphism - calling overridden toString()
                System.out.println(t.toString());
            }
        }
    }

    private void addTransactionDemo() {
        System.out.println("\n========== ADD TRANSACTION ==========");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        int choice = getIntInput("Enter choice: ");

        System.out.print("Enter amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter income source: ");
            String source = scanner.nextLine();
            // Demonstrates: Polymorphism - adding Income to Transaction ArrayList
            transactions.add(new Income(amount, date, description, 1, source));
            System.out.println("\nIncome transaction added successfully!");
        } else if (choice == 2) {
            System.out.print("Enter payment method: ");
            String method = scanner.nextLine();
            // Demonstrates: Polymorphism - adding Expense to Transaction ArrayList
            transactions.add(new Expense(amount, date, description, 2, method));
            System.out.println("\nExpense transaction added successfully!");
        }
    }

    private void viewBudgets() {
        System.out.println("\n========== BUDGETS ==========");
        if (budgets.isEmpty()) {
            System.out.println("No budgets found.");
        } else {
            for (Budget b : budgets) {
                System.out.println(b.toString());
                System.out.println();
            }
        }
    }

    private void viewGoals() {
        System.out.println("\n========== FINANCIAL GOALS ==========");
        if (goals.isEmpty()) {
            System.out.println("No goals found.");
        } else {
            for (Goal g : goals) {
                System.out.println(g.toString());
            }
        }
    }

    private void demonstratePolymorphism() {
        System.out.println("\n========== POLYMORPHISM DEMONSTRATION ==========");
        System.out.println("This demonstrates polymorphism with the Transaction hierarchy:");
        System.out.println("- Transaction is an abstract class");
        System.out.println("- Income and Expense both extend Transaction");
        System.out.println("- We can store both in an ArrayList<Transaction>");
        System.out.println("- Each calls its own overridden methods\n");

        // Demonstrates: Polymorphism
        ArrayList<Transaction> mixed = new ArrayList<>();
        mixed.add(new Income(1000.00, "2025-11-09", "Bonus", 1, "Employer"));
        mixed.add(new Expense(50.00, "2025-11-09", "Dinner", 4, "Credit Card"));

        for (Transaction t : mixed) {
            System.out.println("Type: " + t.getTransactionType());
            System.out.println(t.toString());
            System.out.println();
        }
    }

    private void demonstrateComposition() {
        System.out.println("\n========== COMPOSITION DEMONSTRATION ==========");
        System.out.println("This demonstrates composition with Budget and Category:");
        System.out.println("- Budget HAS-A Category (composition relationship)");
        System.out.println("- Budget contains a Category object as a property\n");

        // Demonstrates: Composition
        Category testCategory = new Category(5, "Dining Out", "Expense");
        Budget testBudget = new Budget(testCategory, 300.00, "2025-11");
        testBudget.updateSpending(75.50);

        System.out.println("Created Budget with composed Category:");
        System.out.println(testBudget.toString());
        System.out.println("\nBudget Report:");
        // Demonstrates: Interface implementation
        System.out.println(testBudget.generateReport());
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
