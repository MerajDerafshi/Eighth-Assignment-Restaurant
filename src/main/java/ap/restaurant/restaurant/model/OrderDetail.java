package ap.restaurant.restaurant.model;

import java.util.UUID;

public class OrderDetail {
    private UUID id;
    private Order order;
    private MenuItem menuItem;
    private int quantity;
    private double price;

    public OrderDetail(Order order, MenuItem menuItem, int quantity) {
        this.id = UUID.randomUUID();
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = menuItem.getPrice();
    }

    public OrderDetail(MenuItem menuItem, double price, int quantity) {
        this.id = UUID.randomUUID();
        this.order = null;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderDetail(Order order, MenuItem menuItem, double price, int quantity) {
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
        this.id = null;
    }


    // Getters and setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public MenuItem getMenuItem() { return menuItem; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
