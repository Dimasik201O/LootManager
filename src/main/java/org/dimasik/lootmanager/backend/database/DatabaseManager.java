package org.dimasik.lootmanager.backend.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;
    private final ExecutorService executorService;

    public DatabaseManager(String host, String user, String password, String database) {
        this.DB_URL = "jdbc:mysql://" + host + "/" + database + "?autoReconnect=true&useSSL=false";
        this.DB_USER = user;
        this.DB_PASSWORD = password;
        this.executorService = Executors.newCachedThreadPool();
        initializeDatabase();
    }

    private void initializeDatabase() {
        CompletableFuture.runAsync(() -> {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS lootmanager_configs (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255) NOT NULL UNIQUE, " +
                        "config_data TEXT NOT NULL, " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)";

                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableQuery);
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<Boolean> saveConfig(String name, String configData) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "INSERT INTO lootmanager_configs (name, config_data) VALUES (?, ?) ON DUPLICATE KEY UPDATE config_data = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    stmt.setString(2, configData);
                    stmt.setString(3, configData);
                    stmt.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при сохранении конфигурации: " + e.getMessage());
                return false;
            }
        }, executorService);
    }

    public CompletableFuture<String> getConfig(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT config_data FROM lootmanager_configs WHERE name = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return rs.getString("config_data");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при получении конфигурации: " + e.getMessage());
            }
            return null;
        }, executorService);
    }

    public CompletableFuture<Boolean> deleteConfig(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "DELETE FROM lootmanager_configs WHERE name = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, name);
                    int affectedRows = stmt.executeUpdate();
                    return affectedRows > 0;
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при удалении конфигурации: " + e.getMessage());
                return false;
            }
        }, executorService);
    }

    public CompletableFuture<List<String>> getConfigNames() {
        return CompletableFuture.supplyAsync(() -> {
            List<String> configNames = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT name FROM lootmanager_configs ORDER BY name";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        configNames.add(rs.getString("name"));
                    }
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при получении списка конфигураций: " + e.getMessage());
            }
            return configNames;
        }, executorService);
    }

    public CompletableFuture<Connection> getConnectionAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при получении соединения: " + e.getMessage(), e);
            }
        }, executorService);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}