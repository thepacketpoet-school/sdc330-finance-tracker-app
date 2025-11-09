/*******************************************************************
* Name: Haley Altaie
* Date: 10/26/25
* Assignment: SDC330 Course Project - Week 3
*
* Goal class represents financial savings goals.
* Demonstrates: Constructors, access specifiers
*/
public class Goal {
    private int id;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private String deadline;
    private String status;

    // Constructor without ID
    public Goal(String name, double targetAmount, String deadline) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
        this.deadline = deadline;
        this.status = "Active";
    }

    // Constructor with ID
    public Goal(int id, String name, double targetAmount, double currentAmount, String deadline, String status) {
        this.id = id;
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addContribution(double amount) {
        this.currentAmount += amount;
        if (this.currentAmount >= this.targetAmount) {
            this.status = "Completed";
        }
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getRemainingAmount() {
        return targetAmount - currentAmount;
    }

    public double getProgressPercentage() {
        return (currentAmount / targetAmount) * 100;
    }

    public boolean isCompleted() {
        return currentAmount >= targetAmount;
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f / $%.2f (%.1f%%) - %s",
            name, currentAmount, targetAmount, getProgressPercentage(), status);
    }
}
