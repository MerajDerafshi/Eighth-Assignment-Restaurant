package ap.restaurant.restaurant.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private User user;
    private LocalDateTime createdAt;
    private double totalPrice;
    private List<OrderDetail> orderDetails;

    public Order(User user, List<OrderDetail> orderDetails) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.orderDetails = (orderDetails != null) ? orderDetails : new ArrayList<>();

        for (OrderDetail orderDetail : this.orderDetails) {
            totalPrice += orderDetail.getPrice() * orderDetail.getQuantity();
        }
    }


    public Order(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.totalPrice = 0;
        this.orderDetails = new ArrayList<>();
    }

    // Getters and setters

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void addToPrice(double price) { this.totalPrice += price; }
    public void deductFromPrice(double price) { this.totalPrice -= price; }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        addToPrice(orderDetail.getPrice() * orderDetail.getQuantity());
    }
    public void removeOrderDetail(OrderDetail orderDetail) {
        orderDetails.remove(orderDetail);
        deductFromPrice(orderDetail.getPrice() * orderDetail.getQuantity());
    }
    public void setOrderDetails(List<OrderDetail> orderDetails) {this.orderDetails = orderDetails;}
}
