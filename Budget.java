/*******************************************************************
* Name: Haley Altaie
* Date: 10/26/25
* Assignment: SDC330 Course Project - Week 3
*
* Budget class represents spending budgets for categories.
* Demonstrates: Composition (HAS-A Category), interface implementation
*/
public class Budget implements Reportable {
    private int id;
    // Composition - Budget HAS-A Category
    // Demonstrates: Composition
    private Category category;
    private double monthlyLimit;
    private double currentSpending;
    private String month;

    // Constructor without ID
    public Budget(Category category, double monthlyLimit, String month) {
        this.category = category;
        this.monthlyLimit = monthlyLimit;
        this.currentSpending = 0.0;
        this.month = month;
    }

    // Constructor with ID
    public Budget(int id, Category category, double monthlyLimit, double currentSpending, String month) {
        this.id = id;
        this.category = category;
        this.monthlyLimit = monthlyLimit;
        this.currentSpending = currentSpending;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public void updateSpending(double amount) {
        this.currentSpending += amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getRemainingBudget() {
        return monthlyLimit - currentSpending;
    }

    public double getPercentageUsed() {
        return (currentSpending / monthlyLimit) * 100;
    }

    public boolean isOverBudget() {
        return currentSpending > monthlyLimit;
    }

    // Implement interface method
    // Demonstrates: Interface implementation
    @Override
    public String generateReport() {
        String status = isOverBudget() ? "OVER BUDGET" : "Within Budget";
        return String.format("Budget Report for %s (%s):%n" +
                           "  Category: %s%n" +
                           "  Monthly Limit: $%.2f%n" +
                           "  Current Spending: $%.2f%n" +
                           "  Remaining: $%.2f%n" +
                           "  Percentage Used: %.1f%%%n" +
                           "  Status: %s",
            category.getName(), month, category.getName(),
            monthlyLimit, currentSpending, getRemainingBudget(),
            getPercentageUsed(), status);
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f / $%.2f (%.1f%% used)",
            category.getName(), currentSpending, monthlyLimit, getPercentageUsed());
    }
}
