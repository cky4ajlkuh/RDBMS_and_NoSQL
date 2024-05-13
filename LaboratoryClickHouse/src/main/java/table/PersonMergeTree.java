package table;

import connection.ApplicationDataSource;
import generator.CreateTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMergeTree {

    public void insert(int id, String name, int age, String gender) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("INSERT INTO person VALUES(?, ?, ?, ?);")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, age);
            preparedStatement.setString(4, gender);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Person table successfully inserted!");
            } else {
                throw new RuntimeException("The data in the Department table could not be inserted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, String newName, int newAge, String newGender) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("ALTER TABLE person UPDATE name = ?, age = ?, gender = ? WHERE id = ?;")) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, newAge);
            preparedStatement.setString(3, newGender);
            preparedStatement.setInt(4, id);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Department table successfully updated!");
            } else {
                throw new RuntimeException("The data in the Department table could not be updated!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("ALTER TABLE person DELETE WHERE id = ?;")) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Department table successfully deleted!");
            } else {
                throw new RuntimeException("The data in the Department table could not be deleted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void select() {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("SELECT * FROM person;");
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                CreateTable.createTable(set);
            } else {
                throw new RuntimeException("table Department is empty!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
