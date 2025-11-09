/*******************************************************************
* Name: Haley Altaie
* Date: 11/02/25
* Assignment: SDC330 Course Project - Week 4
*
* Handles CRUD operations for Transactions table.
* Demonstrates: Database CRUD operations, Polymorphism with database
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionDB {
    
    // Create the Transactions table
    public static boolean createTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Transactions (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " amount REAL NOT NULL,\n"
                + " date VARCHAR(10) NOT NULL,\n"
                + " description VARCHAR(200),\n"
                + " category_id INTEGER NOT NULL,\n"
                + " transaction_type VARCHAR(10) NOT NULL,\n"
                + " source VARCHAR(100),\n"
                + " payment_method VARCHAR(50),\n"
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
    
    // CREATE - Add a transaction (demonstrates polymorphism)
    public static void addTransaction(Connection conn, Transaction transaction) {
        String sql = "INSERT INTO Transactions(amount, date, description, category_id, " +
                    "transaction_type, source, payment_method) VALUES(?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1, transaction.getAmount());
            pst.setString(2, transaction.getDate());
            pst.setString(3, transaction.getDescription());
            pst.setInt(4, transaction.getCategoryId());
            pst.setString(5, transaction.getTransactionType());
            
            // Demonstrates polymorphism - check type and set appropriate fields
            if (transaction instanceof Income) {
                Income income = (Income) transaction;
                pst.setString(6, income.getSource());
                pst.setString(7, null);
            } else if (transaction instanceof Expense) {
                Expense expense = (Expense) transaction;
                pst.setString(6, null);
                pst.setString(7, expense.getPaymentMethod());
            }
            
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // READ - Get a single transaction by ID (demonstrates polymorphism)
    public static Transaction getTransaction(Connection conn, int id) {
        Transaction transaction = null;
        String sql = "SELECT * FROM Transactions WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String type = rs.getString("transaction_type");
                
                // Demonstrates polymorphism - create appropriate subclass
                if (type.equals("Income")) {
                    transaction = new Income(
                        rs.getDouble("amount"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("source")
                    );
                } else {
                    transaction = new Expense(
                        rs.getDouble("amount"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("payment_method")
                    );
                }
                transaction.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return transaction;
    }
    
    // READ - Get all transactions
    public static ArrayList<Transaction> getAllTransactions(Connection conn) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions ORDER BY date DESC";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Transaction transaction;
                String type = rs.getString("transaction_type");
                
                if (type.equals("Income")) {
                    transaction = new Income(
                        rs.getDouble("amount"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("source")
                    );
                } else {
                    transaction = new Expense(
                        rs.getDouble("amount"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("payment_method")
                    );
                }
                transaction.setId(rs.getInt("id"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return transactions;
    }
    
    // UPDATE - Update a transaction
    public static void updateTransaction(Connection conn, Transaction transaction) {
        String sql = "UPDATE Transactions SET amount=?, date=?, description=?, " +
                    "category_id=?, source=?, payment_method=? WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1, transaction.getAmount());
            pst.setString(2, transaction.getDate());
            pst.setString(3, transaction.getDescription());
            pst.setInt(4, transaction.getCategoryId());
            
            if (transaction instanceof Income) {
                Income income = (Income) transaction;
                pst.setString(5, income.getSource());
                pst.setString(6, null);
            } else if (transaction instanceof Expense) {
                Expense expense = (Expense) transaction;
                pst.setString(5, null);
                pst.setString(6, expense.getPaymentMethod());
            }
            
            pst.setInt(7, transaction.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // DELETE - Delete a transaction
    public static void deleteTransaction(Connection conn, int id) {
        String sql = "DELETE FROM Transactions WHERE id=?";
        
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
