package org.example;

import java.sql.*;

public class DatabaseStorage implements Storage {
    private Connection connection;
    private int nextId = 0;

    public DatabaseStorage() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:storage.db");
            createTableIfNotExists();
            initNextId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String data) {
        String insertSQL = "INSERT INTO storage (id, data) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, nextId++);
            pstmt.setString(2, data);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String retrieve(int id) {
        String selectSQL = "SELECT data FROM storage WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String data = rs.getString("data");
                rs.close();
                return data;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initNextId() throws SQLException {
        String maxIdSQL = "SELECT MAX(id) FROM storage";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(maxIdSQL);
        if (rs.next()) {
            nextId = rs.getInt(1) + 1;
        }
        rs.close();
        stmt.close();
    }

    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS storage (id INTEGER PRIMARY KEY, data TEXT)";
        Statement stmt = connection.createStatement();
        stmt.execute(createTableSQL);
        stmt.close();
    }
}
