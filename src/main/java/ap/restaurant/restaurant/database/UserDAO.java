package ap.restaurant.restaurant.database;

import ap.restaurant.restaurant.model.User;
import ap.restaurant.restaurant.database.DatabaseConnector;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class UserDAO {

    public boolean createUser(User user) {
        String sql = "INSERT INTO users (id, username, password, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());  // Hash this!
            stmt.setString(4, user.getEmail());

            int affectedRows = stmt.executeUpdate();
            return affectedRows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<User> findById(UUID id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
                user.setId(UUID.fromString(rs.getString("id")));
                return Optional.of(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String password = rs.getString("password");
                String email = rs.getString("email");

                return Optional.of(new User(id, username, password, email));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
