package ap.restaurant.restaurant.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;

public class LandingController {

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ap/restaurant/restaurant/fxml/Login.fxml")));
            Scene loginScene = new Scene(loginRoot);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            Parent signUpRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ap/restaurant/restaurant/fxml/Signup.fxml")));
            Scene signUpScene = new Scene(signUpRoot);
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setScene(signUpScene);
            stage.setTitle("Sign Up");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
