/*******************************************************************
* Name: Haley Altaie
* Date: 11/02/25
* Assignment: SDC330 Course Project - Week 4
*
* Handles CRUD operations for Goals table.
* Demonstrates: Database CRUD operations
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GoalDB {
    
    // Create the Goals table
    public static boolean createTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Goals (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name VARCHAR(100) NOT NULL,\n"
                + " target_amount REAL NOT NULL,\n"
                + " current_amount REAL DEFAULT 0.00,\n"
                + " deadline VARCHAR(10) NOT NULL,\n"
                + " status VARCHAR(20) DEFAULT 'Active');";
        
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    // CREATE - Add a goal
    public static void addGoal(Connection conn, Goal goal) {
        String sql = "INSERT INTO Goals(name, target_amount, current_amount, deadline, status) " +
                    "VALUES(?,?,?,?,?)";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, goal.getName());
            pst.setDouble(2, goal.getTargetAmount());
            pst.setDouble(3, goal.getCurrentAmount());
            pst.setString(4, goal.getDeadline());
            pst.setString(5, goal.getStatus());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // READ - Get a single goal by ID
    public static Goal getGoal(Connection conn, int id) {
        Goal goal = null;
        String sql = "SELECT * FROM Goals WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                goal = new Goal(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("target_amount"),
                    rs.getDouble("current_amount"),
                    rs.getString("deadline"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return goal;
    }
    
    // READ - Get all goals
    public static ArrayList<Goal> getAllGoals(Connection conn) {
        ArrayList<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM Goals";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Goal goal = new Goal(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("target_amount"),
                    rs.getDouble("current_amount"),
                    rs.getString("deadline"),
                    rs.getString("status")
                );
                goals.add(goal);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return goals;
    }
    
    // UPDATE - Update a goal
    public static void updateGoal(Connection conn, Goal goal) {
        String sql = "UPDATE Goals SET name=?, target_amount=?, current_amount=?, " +
                    "deadline=?, status=? WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, goal.getName());
            pst.setDouble(2, goal.getTargetAmount());
            pst.setDouble(3, goal.getCurrentAmount());
            pst.setString(4, goal.getDeadline());
            pst.setString(5, goal.getStatus());
            pst.setInt(6, goal.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // DELETE - Delete a goal
    public static void deleteGoal(Connection conn, int id) {
        String sql = "DELETE FROM Goals WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
