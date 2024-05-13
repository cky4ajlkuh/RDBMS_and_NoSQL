import jdbc.Join;
import jdbc.connection.ApplicationDataSource;
import jdbc.dao.impl.DepartmentDAO;
import jdbc.dao.impl.EmployeeDAO;
import jdbc.dao.impl.MinistryDAO;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        MinistryDAO ministry = new MinistryDAO(ApplicationDataSource.getConnection());
        EmployeeDAO employee = new EmployeeDAO(ApplicationDataSource.getConnection());
        DepartmentDAO department = new DepartmentDAO(ApplicationDataSource.getConnection());
        employee.select();
        employee.with();
        employee.closeConnection();
/*

        ministry.insert("Министерство охотников за приведениями");
        ministry.select();
        department.insert("отдел против призраков", "7", "10");
        department.select();
        employee.insert("Крутой охотник", "14", "5000000");
        employee.select();

        ministry.update("Охотники за приведениями", "Министерство охотников за приведениями");
        ministry.select();
        department.update("Отдел призраков", "11", "отдел против призраков");
        department.select();
        employee.update("Очень крутой охотник", "1000000", "Крутой охотник");
        employee.select();

        ministry.delete("Охотники за приведениями");
        ministry.select();
        department.select();
        employee.select();


        ministry.groupByHaving();
        department.groupByHaving();
        employee.groupByHaving();

        ministry.with();
        department.with();
        employee.with();




        Join join = new Join(ApplicationDataSource.getConnection());
        //join.innerJoin();
        //join.leftJoin();
        //join.rightJoin();
        join.fullJoin();
        //join.crossJoin();

        join.closeConnection();
*/
    }
}
