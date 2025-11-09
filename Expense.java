/*******************************************************************
* Name: Haley Altaie
* Date: 10/26/25
* Assignment: SDC330 Course Project - Week 3
*
* Expense class represents expense transactions.
* Demonstrates: Inheritance, polymorphism
*/
public class Expense extends Transaction {
    private String paymentMethod;

    // Constructor calls super class constructor
    // Demonstrates: Inheritance, constructors
    public Expense(double amount, String date, String description, int categoryId, String paymentMethod) {
        super(amount, date, description, categoryId);
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Override abstract method from Transaction
    // Demonstrates: Polymorphism
    @Override
    public String getTransactionType() {
        return "Expense";
    }

    @Override
    public String toString() {
        return super.toString() + " - Payment: " + paymentMethod;
    }
}
