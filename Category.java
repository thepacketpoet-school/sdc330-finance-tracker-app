/*******************************************************************
* Name: Haley Altaie
* Date: 10/26/25
* Assignment: SDC330 Course Project - Week 3
*
* Category class represents transaction categories.
* Demonstrates: Constructor overloading, access specifiers
*/
public class Category {
    private int id;
    private String name;
    private String type;

    // Constructor without ID (for new categories)
    // Demonstrates: Constructor overloading
    public Category(String name, String type) {
        this.name = name;
        this.type = type;
    }

    // Constructor with ID (for existing categories from database)
    // Demonstrates: Constructor overloading
    public Category(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}
