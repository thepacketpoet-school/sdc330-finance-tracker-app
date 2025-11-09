/*******************************************************************
* Name: Haley Altaie
* Date: 11/02/25
* Assignment: SDC330 Course Project - Week 4
*
* Handles CRUD operations for Budgets table.
* Demonstrates: Database CRUD operations, Composition with database
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BudgetDB {
    
    // Create the Budgets table
    public static boolean createTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Budgets (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " category_id INTEGER NOT NULL,\n"
                + " monthly_limit REAL NOT NULL,\n"
                + " current_spending REAL DEFAULT 0.00,\n"
                + " month VARCHAR(7) NOT NULL,\n"
                + " FOREIGN KEY (category_id) REFERENCES Categories(id));";
        
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    // CREATE - Add a budget
    public static void addBudget(Connection conn, Budget budget) {
        String sql = "INSERT INTO Budgets(category_id, monthly_limit, current_spending, month) " +
                    "VALUES(?,?,?,?)";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, budget.getCategory().getId());
            pst.setDouble(2, budget.getMonthlyLimit());
            pst.setDouble(3, budget.getCurrentSpending());
            pst.setString(4, budget.getMonth());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // READ - Get a single budget by ID
    public static Budget getBudget(Connection conn, int id) {
        Budget budget = null;
        String sql = "SELECT * FROM Budgets WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // Demonstrates composition - retrieve Category object
                Category category = CategoryDB.getCategory(conn, rs.getInt("category_id"));
                
                budget = new Budget(
                    rs.getInt("id"),
                    category,
                    rs.getDouble("monthly_limit"),
                    rs.getDouble("current_spending"),
                    rs.getString("month")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return budget;
    }
    
    // READ - Get all budgets
    public static ArrayList<Budget> getAllBudgets(Connection conn) {
        ArrayList<Budget> budgets = new ArrayList<>();
        String sql = "SELECT * FROM Budgets";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Category category = CategoryDB.getCategory(conn, rs.getInt("category_id"));
                
                Budget budget = new Budget(
                    rs.getInt("id"),
                    category,
                    rs.getDouble("monthly_limit"),
                    rs.getDouble("current_spending"),
                    rs.getString("month")
                );
                budgets.add(budget);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return budgets;
    }
    
    // UPDATE - Update a budget
    public static void updateBudget(Connection conn, Budget budget) {
        String sql = "UPDATE Budgets SET category_id=?, monthly_limit=?, " +
                    "current_spending=?, month=? WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, budget.getCategory().getId());
            pst.setDouble(2, budget.getMonthlyLimit());
            pst.setDouble(3, budget.getCurrentSpending());
            pst.setString(4, budget.getMonth());
            pst.setInt(5, budget.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // DELETE - Delete a budget
    public static void deleteBudget(Connection conn, int id) {
        String sql = "DELETE FROM Budgets WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
