package table;

import connection.ApplicationDataSource;
import generator.CreateTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PedometerColapsingMergeTree {

    public void insert(int id, int numberOfSteps, int mileage, int sign) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("INSERT INTO pedometer VALUES(?, ?, ?, ?);")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, numberOfSteps);
            preparedStatement.setInt(3, mileage);
            preparedStatement.setInt(4, sign);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Pedometer table successfully inserted!");
            } else {
                throw new RuntimeException("The data in the Pedometer table could not be inserted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, int newNumberOfSteps, int newMileage) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("ALTER TABLE pedometer UPDATE number_of_steps = ?, mileage = ? WHERE id = ?;")) {
            preparedStatement.setInt(1, newNumberOfSteps);
            preparedStatement.setInt(2, newMileage);
            preparedStatement.setInt(3, id);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Pedometer table successfully updated!");
            } else {
                throw new RuntimeException("The data in the Pedometer table could not be updated!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("ALTER TABLE pedometer DELETE WHERE id = ?;")) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Pedometer table successfully deleted!");
            } else {
                throw new RuntimeException("The data in the Pedometer table could not be deleted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void select() {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement("SELECT * FROM pedometer;");
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData().getColumnCount() != 0) {
                CreateTable.createTable(set);
            } else {
                throw new RuntimeException("table Pedometer is empty!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void selectSum() {
        try (PreparedStatement preparedStatement = ApplicationDataSource.getConnection().prepareStatement(
                "SELECT id, sum(number_of_steps * Sign) AS number_of_steps, " +
                        "sum(mileage * Sign) AS mileage " +
                        "FROM pedometer " +
                        "GROUP BY id " +
                        "HAVING sum(Sign) > 0");
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData().getColumnCount() != 0) {
                CreateTable.createTable(set);
            } else {
                throw new RuntimeException("table Pedometer is empty!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
