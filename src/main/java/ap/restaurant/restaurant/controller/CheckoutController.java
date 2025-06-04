package ap.restaurant.restaurant.controller;

import ap.restaurant.restaurant.database.OrderDAO;
import ap.restaurant.restaurant.model.MenuItem;
import ap.restaurant.restaurant.model.Order;
import ap.restaurant.restaurant.model.OrderDetail;
import ap.restaurant.restaurant.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutController {

    @FXML
    private VBox checkoutItemsBox;

    @FXML
    private Label totalLabel;

    private List<MenuItem> cartItems;

    private User user;

    private Order order;

    private List<OrderDetail> convertToOrderDetails() {
        List<OrderDetail> details = new ArrayList<>();
        Map<String, OrderDetail> detailMap = new HashMap<>();

        for (MenuItem item : cartItems) {
            String itemId = String.valueOf(item.getId());

            if (detailMap.containsKey(itemId)) {
                OrderDetail existingDetail = detailMap.get(itemId);
                existingDetail.setQuantity(existingDetail.getQuantity() + 1);
            } else {
                OrderDetail newDetail = new OrderDetail(order, item, 1);
                detailMap.put(itemId, newDetail);
            }
        }

        for (OrderDetail detail : detailMap.values()) {
            order.addOrderDetail(detail);
            details.add(detail);
        }

        return details;
    }


    private void tryCreateOrder() {
        if (user != null && cartItems != null && order == null) {
            order = new Order(user, new ArrayList<>());
            convertToOrderDetails();
        }
    }

    public void setCartItems(List<MenuItem> cartItems) {
        this.cartItems = cartItems;
        tryCreateOrder();

        if (checkoutItemsBox != null) {
            showCartItems();
        }
    }
    private void showCartItems() {
        double total = 0;

        for (MenuItem item : cartItems) {
            Label label = new Label(item.getName() + " - $" + String.format("%.2f", item.getPrice()));
            checkoutItemsBox.getChildren().add(label);
            total += item.getPrice();
        }

        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ap/restaurant/restaurant/fxml/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) totalLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirm() {

        OrderDAO orderDAO = new OrderDAO();
        orderDAO.save(order);

        System.out.println("Order confirmed!");
        totalLabel.setText("Order placed! Thank you!");
        checkoutItemsBox.getChildren().clear();
    }

    public void setUser(User user) {
        this.user = user;
        tryCreateOrder();
    }
}
