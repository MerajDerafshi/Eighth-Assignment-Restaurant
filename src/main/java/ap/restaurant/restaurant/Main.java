package ap.restaurant.restaurant;

import ap.restaurant.restaurant.database.MenuItemDAO;
import ap.restaurant.restaurant.model.MenuItem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ap/restaurant/restaurant/fxml/LandingPage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/ap/restaurant/restaurant/style/Styles.css").toExternalForm());

        primaryStage.setTitle("CS Restaurant");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
