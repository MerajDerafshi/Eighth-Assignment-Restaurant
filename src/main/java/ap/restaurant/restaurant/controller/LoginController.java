package ap.restaurant.restaurant.controller;

import ap.restaurant.restaurant.database.UserDAO;
import ap.restaurant.restaurant.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        UserDAO userDAO = new UserDAO();
        Optional<User> userOpt = userDAO.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (BCrypt.checkpw(password, user.getPassword())) {
                messageLabel.setText("Login successful!");
                goToMenu(user);
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        } else {
            messageLabel.setText("Invalid username or password.");
        }
    }

    private void goToMenu(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ap/restaurant/restaurant/fxml/Menu.fxml"));
            Parent homeRoot = loader.load();

            MenuController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.setTitle("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
