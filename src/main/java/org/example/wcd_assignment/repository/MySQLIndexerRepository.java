package org.example.wcd_assignment.repository;

import org.example.wcd_assignment.entity.Indexer;

import org.example.wcd_assignment.helper.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MySQLIndexerRepository implements IndexerRepository {
    private static final String TABLE_NAME = "indexer";  // Table name in the database

    // Method to find an Indexer by its ID
    @Override
    public Indexer findById(int indexId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE index_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, indexId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapIndexer(rs);  // Convert ResultSet to Indexer object
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get a list of all Indexers
    @Override
    public List<Indexer> findAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        List<Indexer> indexers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                indexers.add(mapIndexer(rs));  // Convert each row to an Indexer object
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return indexers;
    }

    // Method to save a new Indexer
    @Override
    public Indexer save(Indexer indexer) {
        String query = "INSERT INTO " + TABLE_NAME + " (name, valueMin, valueMax) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, indexer.getName());
            stmt.setFloat(2, indexer.getValueMin());
            stmt.setFloat(3, indexer.getValueMax());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        indexer.setIndexId(generatedKeys.getInt(1));  // Set the generated indexId
                        return indexer;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update an existing Indexer
    @Override
    public Indexer update(int indexId, Indexer indexer) {
        String query = "UPDATE " + TABLE_NAME + " SET name = ?, valueMin = ?, valueMax = ? WHERE index_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, indexer.getName());
            stmt.setFloat(2, indexer.getValueMin());
            stmt.setFloat(3, indexer.getValueMax());
            stmt.setInt(4, indexId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                indexer.setIndexId(indexId);  // Ensure the correct indexId is set
                return indexer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to delete an Indexer by ID
    @Override
    public boolean deleteById(int indexId) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE index_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, indexId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper method to map a ResultSet row to an Indexer object
    private Indexer mapIndexer(ResultSet rs) throws SQLException {
        Indexer indexer = new Indexer();
        indexer.setIndexId(rs.getInt("index_id"));
        indexer.setName(rs.getString("name"));
        indexer.setValueMin(rs.getFloat("valueMin"));
        indexer.setValueMax(rs.getFloat("valueMax"));
        return indexer;
    }
}
