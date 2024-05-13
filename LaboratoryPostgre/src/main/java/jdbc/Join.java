package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static jdbc.CreateTable.createTable;

public class Join {
    private static final String SQL_CROSS_JOIN = "SELECT public.\"Ministry\".name as \"Ministry\", public.\"Department\".name as \"Department\"\n" +
            "FROM public.\"Ministry\" CROSS JOIN public.\"Department\";";
    private static final String SQL_LEFT_JOIN = "SELECT public.\"Employee\".name as \"FIO\", public.\"Department\".name as \"Department name\"\n" +
            "FROM public.\"Employee\" LEFT JOIN public.\"Department\" \n" +
            "ON public.\"Employee\".department_id = public.\"Department\".id;";
    private static final String SQL_RIGHT_JOIN = "SELECT public.\"Employee\".name as \"FIO\", public.\"Department\".name as \"Department name\"\n" +
            "FROM public.\"Employee\" RIGHT JOIN public.\"Department\" \n" +
            "ON public.\"Employee\".department_id = public.\"Department\".id;";
    private static final String SQL_FULL_JOIN = "SELECT public.\"Employee\".name as \"FIO\", public.\"Department\".name as \"Department name\"\n" +
            "FROM public.\"Department\" FULL JOIN public.\"Employee\" \n" +
            "ON public.\"Employee\".department_id = public.\"Department\".id;";
    private static final String SQL_INNER_JOIN = "SELECT public.\"Employee\".name as \"FIO\", public.\"Department\".name as \"Department name\" " +
            "FROM public.\"Department\" INNER JOIN public.\"Employee\" " +
            "ON public.\"Employee\".department_id = public.\"Department\".id;";
    private final Connection connection;

    public Join(Connection connection) {
        this.connection = connection;
    }

    public void innerJoin() {
        // все сотрудники всех отделов
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INNER_JOIN);
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                createTable(set);
            } else {
                throw new RuntimeException("The SQL_INNER_JOIN request failed!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void leftJoin() {
        // все отделы и их сотрудники
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LEFT_JOIN);
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                createTable(set);
            } else {
                throw new RuntimeException("The SQL_LEFT_JOIN request failed!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void rightJoin() {
        // все сотрудники и их отделы
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_RIGHT_JOIN);
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                createTable(set);
            } else {
                throw new RuntimeException("The SQL_RIGHT_JOIN request failed!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void fullJoin() {
        // все сотрудники и все отделы
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FULL_JOIN);
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                createTable(set);
            } else {
                throw new RuntimeException("The SQL_FULL_OUTER_JOIN request failed!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void crossJoin() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CROSS_JOIN);
             ResultSet set = preparedStatement.executeQuery()) {
            if (set.getMetaData() != null) {
                createTable(set);
            } else {
                throw new RuntimeException("The SQL_CROSS_JOIN request failed!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
