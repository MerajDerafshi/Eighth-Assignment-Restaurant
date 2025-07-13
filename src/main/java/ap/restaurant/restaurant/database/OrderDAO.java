package ap.restaurant.restaurant.database;

import ap.restaurant.restaurant.model.*;

import java.sql.*;
import java.util.*;

public class OrderDAO {
    private final UserDAO userDAO = new UserDAO();
    private static final MenuItemDAO menuItemDAO = new MenuItemDAO();

    public void save(Order order) {
        String insertOrderSQL = "INSERT INTO orders (id, user_id, created_at, total_price) VALUES (?, ?, ?, ?)";
        String insertDetailSQL = "INSERT INTO order_detail (id, order_id, menu_item_id, price, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false); // Transaction begins

            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL);
                 PreparedStatement detailStmt = conn.prepareStatement(insertDetailSQL)) {

                UUID orderId = UUID.randomUUID();
                order.setId(orderId);

                orderStmt.setObject(1, orderId);
                orderStmt.setObject(2, order.getUser().getId());
                orderStmt.setTimestamp(3, Timestamp.valueOf(order.getCreatedAt()));
                orderStmt.setDouble(4, order.getTotalPrice());
                orderStmt.executeUpdate();

                for (OrderDetail detail : order.getOrderDetails()) {
                    UUID detailId = UUID.randomUUID();
                    detailStmt.setObject(1, detailId);
                    detailStmt.setObject(2, orderId);
                    detailStmt.setObject(3, detail.getMenuItem().getId());
                    detailStmt.setDouble(4, detail.getPrice());
                    detailStmt.setInt(5, detail.getQuantity());
                    detailStmt.addBatch();
                }

                detailStmt.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UUID orderId = UUID.fromString(rs.getString("id"));
                UUID userId = UUID.fromString(rs.getString("user_id"));

                Order order = new Order(userDAO.findById(userId).orElse(null));
                order.setId(orderId);
                order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setOrderDetails(findOrderDetails(orderId));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static Optional<OrderDetail> findById(UUID id) {
        String sql = "SELECT * FROM order_detail WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UUID orderId = UUID.fromString(rs.getString("order_id"));
                UUID menuItemId = UUID.fromString(rs.getString("menu_item_id"));
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                Order order = OrderDAO.findById(orderId).orElse(null).getOrder();
                MenuItem menuItem = menuItemDAO.findById(menuItemId).orElse(null);

                if (order != null && menuItem != null) {
                    OrderDetail detail = new OrderDetail(order, menuItem, quantity);
                    detail.setId(id); // Set the ID from DB
                    return Optional.of(detail);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }


    public List<OrderDetail> findOrderDetails(UUID orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT * FROM order_detail WHERE order_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, orderId);
            ResultSet rs = stmt.executeQuery();

            Order order = OrderDAO.findById(orderId).orElse(null).getOrder();

            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                UUID menuItemId = UUID.fromString(rs.getString("menu_item_id"));
                MenuItem menuItem = menuItemDAO.findById(menuItemId).orElse(null);
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                OrderDetail detail = new OrderDetail(order, menuItem, quantity);
                detail.setId(id);
                details.add(detail);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }

    public void deleteById(UUID orderId) {
        String deleteOrderDetailsSql = "DELETE FROM order_detail WHERE order_id = ?";
        String deleteOrderSql = "DELETE FROM orders WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement detailStmt = conn.prepareStatement(deleteOrderDetailsSql);
                    PreparedStatement orderStmt = conn.prepareStatement(deleteOrderSql)
            ) {
                detailStmt.setObject(1, orderId);
                detailStmt.executeUpdate();

                orderStmt.setObject(1, orderId);
                orderStmt.executeUpdate();

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
