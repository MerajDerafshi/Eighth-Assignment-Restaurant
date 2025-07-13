package ap.restaurant.restaurant.controller;

import ap.restaurant.restaurant.database.MenuItemDAO;
import ap.restaurant.restaurant.model.MenuItem;
import ap.restaurant.restaurant.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    public Button checkoutButton;

    @FXML
    private VBox menuBox;

    @FXML
    private VBox cartBox;

    private User user;

    private final List<MenuItem> cartItems = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MenuItemDAO dao = new MenuItemDAO();
        List<MenuItem> items = dao.findAll();
        for (MenuItem item : items) {
            menuBox.getChildren().add(createMenuItemBox(item));
        }
    }


    private HBox createMenuItemBox(MenuItem item) {
        VBox infoBox = new VBox(
                new Label(item.getName()),
                new Label(item.getDescription()),
                new Label(String.format("$%.2f", item.getPrice()))
        );
        infoBox.setPrefWidth(250);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            cartItems.add(item);
            cartBox.getChildren().add(createCartItemBox(item));
        });

        HBox box = new HBox(10, infoBox, addButton);
        box.setStyle("-fx-border-color: #e1952d; -fx-padding: 10;");
        return box;
    }

    private HBox createCartItemBox(MenuItem item) {
        Label label = new Label(item.getName() + " - $" + item.getPrice());
        label.setPrefWidth(200);

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            cartItems.remove(item);
            cartBox.getChildren().removeIf(node -> {
                Label l = (Label) ((HBox) node).getChildren().get(0);
                return l.getText().startsWith(item.getName());
            });
        });

        HBox box = new HBox(10, label, removeButton);
        box.setStyle("-fx-border-color: #dcb05d; -fx-padding: 10;");
        return box;
    }

    @FXML
    private void handleCheckout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ap/restaurant/restaurant/fxml/Checkout.fxml"));
            Parent root = loader.load();

             CheckoutController controller = loader.getController();
             controller.setCartItems(cartItems);
             controller.setUser(user);

            Stage stage = (Stage) menuBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Checkout");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
