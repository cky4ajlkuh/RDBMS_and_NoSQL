package table;

import connection.ApplicationDataSource;
import generator.CreateTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductReplacingMergeTree {

    public void insert(int key, String name, int version) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("INSERT INTO product VALUES(?, ?, ?);")) {
            preparedStatement.setInt(1, key);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, version);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Product table successfully inserted!");
            } else {
                throw new RuntimeException("The data in the Product table could not be inserted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int key, String newName) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("ALTER TABLE product UPDATE name = ? WHERE key = ?;")) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, key);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Product table successfully updated!");
            } else {
                throw new RuntimeException("The data in the Product table could not be updated!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int key) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("ALTER TABLE product DELETE WHERE key = ?;")) {
            preparedStatement.setInt(1, key);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Product table successfully deleted!");
            } else {
                throw new RuntimeException("The data in the Product table could not be deleted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void select() {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("SELECT * FROM product;");
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData().getColumnCount() != 0) {
                CreateTable.createTable(set);
            } else {
                throw new RuntimeException("table Product is empty!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void selectLimitBy(int limit) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement(
                "SELECT * " +
                        "FROM product " +
                        "ORDER BY version DESC " +
                        "LIMIT " + limit + " BY key")) {
            ResultSet set = preparedStatement.executeQuery();
            if (set.getMetaData().getColumnCount() != 0) {
                CreateTable.createTable(set);
            } else {
                throw new RuntimeException("table Product is empty!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
