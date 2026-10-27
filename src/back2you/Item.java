package back2you;

import java.time.LocalDate;

public abstract class Item {
    private int id;
    private String name;
    private String description;
    private Category category;
    private String location;
    private LocalDate date;

    public Item(int id, String name, String description, Category category,
                String location, LocalDate date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.location = location;
        this.date = date;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(Category category) { this.category = category; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(LocalDate date) { this.date = date; }

    // Every child class must define its own type
    public abstract String getType();

    public void displayDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
        System.out.println("Location: " + location);
        System.out.println("Date: " + date);
    }
}