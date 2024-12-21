package com.travel.dao;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentsDAO {

    private String jdbcURL = "jdbc:mysql://localhost:30006/userdb";
    private String jdbcUserName = "root";
    private String jdbcPassword = ""; // Add your MySQL password here

    private static final String INSERT_USER_SQL = "INSERT INTO users (id, uname, email, country, passwd) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users;";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?;";
    // private static final String UPDATE_USERS_SQL = "UPDATE users SET uname=?, email=?, country=?, passwd=? WHERE id=?;";

    public PaymentsDAO() {
        super();
    }

    // Method to establish a database connection
    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Method to insert a new user
    public void insertUser(UserDAO user) {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getCountry());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a user by ID
    public UserDAO selectUser(int id) {
        UserDAO user = null;

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("uname");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                String password = resultSet.getString("passwd");

                user = new UserDAO(id, name, email, country, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Method to retrieve all users
    public List<Member> selectAllUsers() {
        List<Member> users = new ArrayList<>();

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("uname");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                String password = resultSet.getString("passwd");

                users.add((Member) new UserDAO());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // Method to delete a user by ID
    public boolean deleteUser(int id) {
        boolean status = false;

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);
            preparedStatement.setInt(1, id);
            status = preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
