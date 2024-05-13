import connection.ApplicationDataSource;
import connection.Cassandra;

public class Main {
    public static void main(String[] args) {
        ApplicationDataSource.connect();
        Cassandra cassandra = new Cassandra();
        cassandra.select();
        cassandra.insert(10, "Poly", "F", 20);
        cassandra.select();
        cassandra.update(10, "Polina", "F", 22);
        cassandra.select();
        cassandra.delete(10);
        cassandra.select();
        cassandra.selectCountOfMenOver(40);
        cassandra.selectPersonWithName("Barbi");
        ApplicationDataSource.close();
    }
}
