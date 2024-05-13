package generator;

import generator.TableGenerator;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateTable {
    public static void createTable(ResultSet set) throws SQLException {
        TableGenerator tableGenerator = new TableGenerator();
        List<String> headersList = new ArrayList<>();
        List<List<String>> rowsList = new ArrayList<>();
        ResultSetMetaData data = set.getMetaData();
        int columnsNumber = data.getColumnCount();
        while (set.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnsNumber; i++) {
                headersList.add(data.getColumnName(i));
                row.add(set.getString(i));
            }
            rowsList.add(row);
            break;
        }
        while (set.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnsNumber; i++) {
                row.add(set.getString(i));
            }
            rowsList.add(row);
        }
        System.out.print(tableGenerator.generateTable(headersList, rowsList));
    }
}