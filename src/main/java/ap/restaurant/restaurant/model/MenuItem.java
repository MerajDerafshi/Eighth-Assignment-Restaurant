package ap.restaurant.restaurant.model;

import java.util.UUID;

public class MenuItem {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String category;

    public MenuItem(String name, String description, double price, String category) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }


    // Getters and setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return name + " - " + price;
    }
}
