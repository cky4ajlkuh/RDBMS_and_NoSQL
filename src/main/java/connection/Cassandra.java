package connection;

import com.datastax.driver.core.*;

public class Cassandra {

    public Cassandra() {

    }

    public void createKeyspacePeople() {
        try {
            ResultSet set = ApplicationDataSource.getSession().execute("""
                    CREATE KEYSPACE IF NOT EXISTS people WITH REPLICATION =
                    { 'class' : 'SimpleStrategy',
                    'replication_factor' : '1'
                    };""");
            if (set.wasApplied()) {
                System.out.println("Keyspace people was applied");
            } else {
                System.out.println("Keyspace people wasn't applied");
                ApplicationDataSource.close();
            }
        } catch (RuntimeException e) {
            ApplicationDataSource.close();
            e.printStackTrace();
        }
    }

    public void createTablePerson() {
        try {
            ResultSet set = ApplicationDataSource.getSession().execute("""
                    CREATE TABLE IF NOT EXISTS people.person (
                        id int PRIMARY KEY,
                        name TEXT,
                        gender TEXT,
                        age int
                    );""");
            if (set.wasApplied()) {
                System.out.println("Table person was applied");
            } else {
                System.out.println("Table person wasn't applied");
                ApplicationDataSource.close();
            }
        } catch (RuntimeException e) {
            ApplicationDataSource.close();
            e.printStackTrace();
        }
    }

    public void insert(int id, String name, String gender, int age) {
        try {
            ResultSet set = ApplicationDataSource.getSession().execute("" +
                    "INSERT INTO people.person " +
                    "(id, name, gender, age) " +
                    "VALUES (" + id + " , '" + name + "', '" + gender + "', " + age + ");");
            if (set.wasApplied()) {
                System.out.println("Data in person was applied");
            } else {
                System.out.println("Data in person wasn't applied");
                ApplicationDataSource.close();
            }
        } catch (RuntimeException e) {
            ApplicationDataSource.close();
            e.printStackTrace();
        }
    }


    public void update(int id, String name, String gender, int age) {
        try {
            ResultSet set = ApplicationDataSource.getSession().execute("" +
                    " UPDATE people.person" +
                    " SET name = '" + name + "', gender = '" + gender + "', age = " + age +
                    " WHERE id =" + id);
            if (set.wasApplied()) {
                System.out.println("Data in person was updated");
            } else {
                System.out.println("Data in person wasn't updated");
                ApplicationDataSource.close();
            }
        } catch (RuntimeException e) {
            ApplicationDataSource.close();
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            ResultSet set = ApplicationDataSource.getSession().execute("DELETE FROM people.person WHERE id =" + id);
            if (set.wasApplied()) {
                System.out.println("Data in person was deleted!");
            } else {
                System.out.println("Data in person wasn't deleted");
                ApplicationDataSource.close();
            }
        } catch (RuntimeException e) {
            ApplicationDataSource.close();
            e.printStackTrace();
        }
    }

    public void select() {
        try {
            ResultSet set = ApplicationDataSource.getSession().execute("SELECT * FROM people.person;");
            System.out.println("Select all persons");
            System.out.println(set.getColumnDefinitions());
            set.forEach(System.out::println);
            System.out.println("_________________________________________________");
        } catch (RuntimeException e) {
            ApplicationDataSource.close();
            e.printStackTrace();
        }
    }

    public void selectCountOfMenOver(int age) {
        try {
            System.out.println("Select count of men who have age > " + age);
            ResultSet set = ApplicationDataSource.getSession().execute("SELECT COUNT(*) FROM people.person WHERE gender = 'M' and age > " + age + " ALLOW FILTERING;");
            System.out.println(set.one());
            System.out.println("_________________________________________________");
        } catch (RuntimeException e) {
            ApplicationDataSource.close();
            e.printStackTrace();
        }
    }

    public void selectPersonWithName(String name) {
        try {
            System.out.println("Select info about person with name " + name);
            ResultSet set = ApplicationDataSource.getSession().execute("SELECT * FROM people.person WHERE name = '" + name + "' ALLOW FILTERING;");
            System.out.println(set.getColumnDefinitions());
            set.forEach(System.out::println);
            System.out.println("_________________________________________________");
        } catch (RuntimeException e) {
            ApplicationDataSource.close();
            e.printStackTrace();
        }
    }
}
