/*******************************************************************
* Name: Haley Altaie
* Date: 10/26/25
* Assignment: SDC330 Course Project - Week 3
*
* Income class represents income transactions.
* Demonstrates: Inheritance, polymorphism
*/
public class Income extends Transaction {
    private String source;

    // Constructor calls super class constructor
    // Demonstrates: Inheritance, constructors
    public Income(double amount, String date, String description, int categoryId, String source) {
        super(amount, date, description, categoryId);
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    // Override abstract method from Transaction
    // Demonstrates: Polymorphism
    @Override
    public String getTransactionType() {
        return "Income";
    }

    @Override
    public String toString() {
        return super.toString() + " - Source: " + source;
    }
}
