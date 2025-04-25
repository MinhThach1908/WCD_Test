package org.example.wcd_assignment.repository;

import org.example.wcd_assignment.entity.Player;
import org.example.wcd_assignment.helper.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MySQLPlayerRepository implements PlayerRepository {

    private static final String TABLE_NAME = "players";

    @Override
    public Player findById(long playerId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE player_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, playerId); // Changed to long
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapPlayer(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error finding player by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Player> findAll(int limit, int page) {
        String query = "SELECT * FROM " + TABLE_NAME + " LIMIT ? OFFSET ?";
        List<Player> players = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, (page - 1) * limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                players.add(mapPlayer(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving players: " + e.getMessage());
        }

        return players.isEmpty() ? Collections.emptyList() : players;
    }

    @Override
    public List<Player> findAll(int limit, int page, String keyword, String sortColumn, String sortDirection) {
        String[] searchableColumns = {"name", "full_name", "age"};
        StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE 1=1");

        if (keyword != null && !keyword.isEmpty()) {
            query.append(" AND (");
            for (String column : searchableColumns) {
                query.append(column).append(" LIKE ? OR ");
            }
            query.setLength(query.length() - 4);
            query.append(")");
        }

        if (sortColumn != null && !sortColumn.isEmpty() && sortDirection != null && !sortDirection.isEmpty()) {
            query.append(" ORDER BY ").append(sortColumn).append(" ").append(sortDirection);
        } else {
            query.append(" ORDER BY player_id ASC");
        }

        query.append(" LIMIT ? OFFSET ?");
        List<Player> players = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query.toString())) {

            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                for (String column : searchableColumns) {
                    stmt.setString(paramIndex++, "%" + keyword + "%");
                }
            }

            stmt.setInt(paramIndex++, limit);
            stmt.setInt(paramIndex, (page - 1) * limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                players.add(mapPlayer(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving players with search: " + e.getMessage());
        }

        return players.isEmpty() ? Collections.emptyList() : players;
    }

    @Override
    public Player save(Player player) {
        String query = "INSERT INTO " + TABLE_NAME + " (name, full_name, age, index_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, player.getName());
            stmt.setString(2, player.getFullName());
            stmt.setString(3, player.getAge());
            stmt.setInt(4, player.getIndexId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        player.setPlayerId((int) generatedKeys.getLong(1)); // Adjusted to long
                        return player;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saving player: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Player update(long playerId, Player player) {
        String query = "UPDATE " + TABLE_NAME + " SET name = ?, full_name = ?, age = ?, index_id = ? WHERE player_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, player.getName());
            stmt.setString(2, player.getFullName());
            stmt.setString(3, player.getAge());
            stmt.setInt(4, player.getIndexId());
            stmt.setLong(5, playerId); // Adjusted to long

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                player.setPlayerId((int) playerId);
                return player;
            }
        } catch (SQLException e) {
            System.out.println("Error updating player: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean deleteById(long playerId) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE player_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, playerId); // Adjusted to long
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting player: " + e.getMessage());
        }

        return false;
    }

    @Override
    public int getTotalRecords(String keyword) {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        if (keyword != null && !keyword.isEmpty()) {
            query += " WHERE name LIKE ?";
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            if (keyword != null && !keyword.isEmpty()) {
                stmt.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting total records: " + e.getMessage());
        }

        return 0;
    }

    // Helper method to convert ResultSet into Player
    private Player mapPlayer(ResultSet rs) throws SQLException {
        Player player = new Player(
                (int) rs.getLong("player_id"), // Adjusted to long
                rs.getString("name"),
                rs.getString("full_name"),
                rs.getString("age"),
                rs.getInt("index_id")
        );
        return player;
    }
}
