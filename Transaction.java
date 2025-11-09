/*******************************************************************
* Name: Haley Altaie
* Date: 10/26/25
* Assignment: SDC330 Course Project - Week 3
*
* Abstract base class for all transactions (income and expenses).
* Demonstrates: Abstract classes, inheritance, access specifiers
*/
public abstract class Transaction {
    // Private properties - demonstrates access specifiers
    private int id;
    private double amount;
    private String date;
    private String description;
    private int categoryId;

    // Protected constructor - only accessible to subclasses
    // Demonstrates: Access specifiers, constructors
    protected Transaction(double amount, String date, String description, int categoryId) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Public getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // Abstract method - must be implemented by subclasses
    // Demonstrates: Abstract methods, polymorphism
    public abstract String getTransactionType();

    @Override
    public String toString() {
        return String.format("%s - $%.2f on %s - %s",
            getTransactionType(), amount, date, description);
    }
}
