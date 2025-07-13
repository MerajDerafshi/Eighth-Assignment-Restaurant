package ap.restaurant.restaurant.controller;

import ap.restaurant.restaurant.database.UserDAO;
import ap.restaurant.restaurant.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class SignUpController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField emailField;

    @FXML
    private void handleSignUp(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();
        String email = emailField.getText();

        if (!password.equals(confirm)) {
            messageLabel.setText("Passwords do not match.");
            return;
        }

        UserDAO userDAO = new UserDAO();
        Optional<User> existingUser = userDAO.findByUsername(username);

        if (existingUser.isPresent()) {
            messageLabel.setText("Username already taken.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User(UUID.randomUUID(), username, hashedPassword, email);

        boolean success = userDAO.createUser(newUser);
        if (success) {
            messageLabel.setText("Sign up successful!");
            goToMenu(newUser);
        } else {
            messageLabel.setText("Sign up failed. Try again.");
        }
    }

    private void goToMenu(User user) {
        try {
            Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ap/restaurant/restaurant/fxml/Menu.fxml")));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            MenuController controller = new MenuController();
            controller.setUser(user);
            stage.setScene(new Scene(homeRoot));
            stage.setTitle("Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
