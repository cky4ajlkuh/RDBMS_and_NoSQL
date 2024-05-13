package jdbc.dao.impl;

import jdbc.dao.DataAccessObject;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static jdbc.CreateTable.createTable;

public class MinistryDAO extends DataAccessObject {

    // объединяем всех сотрудников со всеми отделами и всеми министерствами и выводим только те, где в министерстве есть Самара
    private final static String SQL_WITH = "WITH t AS (\n" +
            "\tSELECT public.\"Ministry\".name as \"Ministry\", public.\"Department\".name as \"Department\", public.\"Employee\".name as \"FIO\"\n" +
            "\tFROM public.\"Ministry\", public.\"Department\", public.\"Employee\" \n" +
            "\twhere public.\"Department\".id = public.\"Employee\".department_id and public.\"Ministry\".id = public.\"Department\".ministry_id\n" +
            ")\n" +
            "SELECT \"Ministry\", \"Department\", \"FIO\" FROM t\n" +
            "WHERE \"Ministry\" LIKE '%Samara%';";  // рекурсия
    // Министерства, отделы и размер отдела, где максимальный размер отдела не превышает 100 человек
    private final static String SQL_REQUEST = "SELECT public.\"Ministry\".name as \"Ministry\", public.\"Department\".name as \"Department\", public.\"Department\".amount\n" +
            "FROM public.\"Ministry\" INNER JOIN public.\"Department\" ON public.\"Ministry\".id = public.\"Department\".ministry_id \n" +
            "GROUP BY \"Ministry\", \"Department\", amount HAVING MAX(public.\"Department\".amount) < 100;";
    private final static String SQL_UPDATE = "UPDATE public.\"Ministry\" SET NAME = ? WHERE name = ?;";
    private final static String SQL_DELETE = "DELETE FROM public.\"Ministry\" WHERE name = ?;";
    private final static String SQL_INSERT = "INSERT INTO public.\"Ministry\" (name) VALUES(?);";
    private final static String SQL_SELECT = "SELECT * FROM public.\"Ministry\";";

    private final Connection connection;

    public MinistryDAO(Connection connection) {
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
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Ministry table successfully inserted!");
            } else {
                throw new RuntimeException("The data in the Ministry table could not be inserted!");
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
            preparedStatement.setString(2, value[1]);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Data in the Ministry table successfully updated!");
            } else {
                throw new RuntimeException("The data in the Ministry table could not be updated!");
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
                System.out.println("Data in the Ministry table successfully deleted!");
            } else {
                throw new RuntimeException("The data in the Ministry table could not be deleted!");
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
                throw new RuntimeException("table Ministry is empty!");
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
