package jdbc.dao.impl;

import jdbc.dao.DataAccessObject;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static jdbc.CreateTable.createTable;

public class EmployeeDAO extends DataAccessObject {
    private final static String SQL_WITH =
            "WITH RECURSIVE Rec (id, pid, name, level) AS (\n" +
            "    SELECT e.id, e.pid, e.name, 1\n" +
            "    FROM public.\"Employee\" e\n" +
            "    WHERE pid IS NULL\n" +
            "UNION ALL\n" +
            "    SELECT employee.id, employee.pid, employee.name, r.level + 1\n" +
            "    FROM public.\"Employee\" employee\n" +
            "    JOIN Rec r ON employee.pid = r.id\n" +
            ")\n" +
            "SELECT * FROM Rec;";
    private final static String SQL_REQUEST = "SELECT public.\"Department\".name as \"Department\", public.\"Employee\".name as \"FIO\", public.\"Employee\".salary\n" +
            "FROM public.\"Department\" RIGHT JOIN public.\"Employee\" ON public.\"Department\".id = public.\"Employee\".department_id \n" +
            "WHERE \"Department\".name LIKE '%Samara%'\n" +
            "GROUP BY \"Department\", \"FIO\", salary HAVING AVG(salary) > 50000;";
    private final static String SQL_UPDATE = "UPDATE public.\"Employee\" SET NAME = ?, salary = ? WHERE name = ?;";
    private final static String SQL_DELETE = "DELETE FROM public.\"Employee\" WHERE name = ?;";
    private final static String SQL_INSERT = "INSERT INTO public.\"Employee\" (name, department_id, salary, level_mayor) VALUES(?, ?, ?, ?);";
    private final static String SQL_SELECT = "SELECT * FROM public.\"Employee\";";

    private final Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    @SneakyThrows
    public void with() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_WITH);
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                createTable(set);
            } else {
                throw new RuntimeException("Oops..!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void insert(String... value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, value[0]);
            preparedStatement.setInt(2, Integer.parseInt(value[1]));
            preparedStatement.setInt(3, Integer.parseInt(value[2]));
            preparedStatement.setInt(4, Integer.parseInt(value[3]));
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Employee table successfully inserted!");
            } else {
                throw new RuntimeException("The data in the Employee table could not be inserted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public void update(String... value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, value[0]);
            preparedStatement.setInt(2, Integer.parseInt(value[1]));
            preparedStatement.setString(3, value[2]);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Employee table successfully updated!");
            } else {
                throw new RuntimeException("The data in the Employee table could not be updated!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public void delete(String name) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE)) {
            preparedStatement.setString(1, name);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Employee table successfully deleted!");
            } else {
                throw new RuntimeException("The data in the Employee table could not be deleted!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SneakyThrows
    public void select() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT);
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                createTable(set);
            } else {
                throw new RuntimeException("table Employee is empty!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    @SneakyThrows
    public void groupByHaving() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REQUEST);
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                createTable(set);
            } else {
                throw new RuntimeException("Oops!..");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
