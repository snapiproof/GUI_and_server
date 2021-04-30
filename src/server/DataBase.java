package server;

import database.SpaceMarine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBase {
    private Connection connection;
    private String login;
    private String password;

    public DataBase(Connection connection, String login, String password){
        this.connection = connection;
        this.login = login;
        this.password = password;
    }
    public void insert(SpaceMarine spaceMarine) {
        try {
            String request = "CREATE TABLE SpaceMarineCollection(id INT, age INT)";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
