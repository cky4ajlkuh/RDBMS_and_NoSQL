package jdbc.dao.impl;

import jdbc.dao.DataAccessObject;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static jdbc.CreateTable.createTable;

public class DepartmentDAO extends DataAccessObject {

     // все сотрудники и их отделы, где у сотрудников зарплата до 100к
    private final static String SQL_WITH = "WITH t AS (\n" +
            "\tSELECT public.\"Department\".name as \"Department\", public.\"Employee\".name as \"FIO\", public.\"Employee\".salary\n" +
            "\tFROM public.\"Department\", public.\"Employee\" where public.\"Department\".id = public.\"Employee\".department_id\n" +
            ")\n" +
            "SELECT * FROM t where salary < 100000;";
    // отдел, все сотрудники и его зп, сначала фильтруем чтоб у каждого сотрудника зп от 30к, а затем чтоб средняя ЗП по отделу больше 50к
    private final static String SQL_REQUEST = "SELECT public.\"Department\".name as \"Department\", public.\"Employee\".name as \"FIO\", public.\"Employee\".salary\n" +
            "FROM public.\"Department\" RIGHT JOIN public.\"Employee\" ON public.\"Department\".id = public.\"Employee\".department_id \n" +
            "WHERE salary > 30000\n" +
            "GROUP BY \"Department\", \"FIO\", salary \n" +
            "HAVING AVG(salary) > 50000;";
    private final static String SQL_UPDATE = "UPDATE public.\"Department\" SET name = ?, amount = ? WHERE name = ?;";
    private final static String SQL_DELETE = "DELETE FROM public.\"Department\" WHERE name = ?;";
    private final static String SQL_INSERT = "INSERT INTO public.\"Department\" (name, ministry_id, amount) VALUES(?, ?, ?);";
    private final static String SQL_SELECT = "SELECT * FROM public.\"Department\";";
    private final Connection connection;

    public DepartmentDAO(Connection connection) {
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
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Department table successfully inserted!");
            } else {
                throw new RuntimeException("The data in the Department table could not be inserted!");
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
                System.out.println("Data in the Department table successfully updated!");
            } else {
                throw new RuntimeException("The data in the Department table could not be updated!");
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
                System.out.println("Data in the Department table successfully deleted!");
            } else {
                throw new RuntimeException("The data in the Department table could not be deleted!");
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
                throw new RuntimeException("table Department is empty!");
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
