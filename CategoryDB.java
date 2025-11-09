/*******************************************************************
* Name: Haley Altaie
* Date: 11/02/25
* Assignment: SDC330 Course Project - Week 4
*
* Handles CRUD operations for Categories table.
* Demonstrates: Database CRUD operations (Create, Read, Update, Delete)
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDB {
    
    // Create the Categories table
    public static boolean createTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Categories (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name VARCHAR(50) NOT NULL,\n"
                + " type VARCHAR(10) NOT NULL);";
        
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    // CREATE - Add a category
    public static void addCategory(Connection conn, Category category) {
        String sql = "INSERT INTO Categories(name, type) VALUES(?,?)";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, category.getName());
            pst.setString(2, category.getType());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // READ - Get a single category by ID
    public static Category getCategory(Connection conn, int id) {
        Category category = new Category("", "");
        String sql = "SELECT * FROM Categories WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                category = new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return category;
    }
    
    // READ - Get all categories
    public static ArrayList<Category> getAllCategories(Connection conn) {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Category category = new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return categories;
    }
    
    // UPDATE - Update a category
    public static void updateCategory(Connection conn, Category category) {
        String sql = "UPDATE Categories SET name=?, type=? WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, category.getName());
            pst.setString(2, category.getType());
            pst.setInt(3, category.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // DELETE - Delete a category
    public static void deleteCategory(Connection conn, int id) {
        String sql = "DELETE FROM Categories WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
