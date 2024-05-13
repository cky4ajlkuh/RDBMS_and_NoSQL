package jdbc.dao;

import java.sql.SQLException;

public abstract class DataAccessObject {

    public abstract void update(String... value);

    public abstract void delete(String name);

    public abstract void select();

    public void insert(String... value) {
    }

    public abstract void groupByHaving();

    public abstract void with();
    public abstract void closeConnection() throws SQLException;
}
