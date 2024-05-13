package crud;

import connection.ApplicationDataSource;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OriflameAgent {

    private String name;

    private String email;

    private String city;

    private String appointment;

    public OriflameAgent(String name, String email, String city, String appointment) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.appointment = appointment;
    }

    public OriflameAgent(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getAppointment() {
        return appointment;
    }

    public void createOriflameAgent() {
        try (Driver driver = ApplicationDataSource.getDriver()) {
            driver.executableQuery("CREATE (a:OriflameAgent {city: $city, name: $name, appointment: $appointment, email: $email}) RETURN a.name AS name")
                    .withParameters(Map.of("city", city, "name", name, "appointment", appointment, "email", email))
                    .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
                    .execute();
        }
    }

    public void deleteOriflameAgent() {
        try (Driver driver = ApplicationDataSource.getDriver()) {
            driver.executableQuery("MATCH (a:OriflameAgent {name: $name}) DETACH DELETE a")
                    .withParameters(Map.of("name", getName()))
                    .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
                    .execute();
        }
    }

    public void updateOriflameAgent(String newName, String newEmail, String newCity, String newAppointment) {
        try (Session session = ApplicationDataSource.getDriver().session()) {
            session.executeWriteWithoutResult(transactionContext -> transactionContext.run("MATCH (a:OriflameAgent) WHERE a.name = $name SET a.city = $newCity, a.name = $newName, a.email = $newEmail, a.appointment = $newAppointment",
                    Map.of("name", getName(), "newCity", newCity, "newName", newName, "newEmail", newEmail, "newAppointment", newAppointment)));
            setName(newName);
            setEmail(newEmail);
            setCity(newCity);
            setAppointment(newAppointment);
        }
    }

    public void createRelationOn(String onName) {
        try (Session session = ApplicationDataSource.getDriver().session()) {
            session.executeWriteWithoutResult(transactionContext -> transactionContext.run("" +
                            "MATCH (a:OriflameAgent {name: $relName}), (b:OriflameAgent {name: $myName}) " +
                            "CREATE (a)-[r:REFERRED]->(b)",
                    Map.of("relName", onName, "myName", getName())));
        }
    }

    public void deleteRelationFrom(String fromName) {
        try (Session session = ApplicationDataSource.getDriver().session()) {
            session.executeWriteWithoutResult(transactionContext -> transactionContext.run("" +
                            "MATCH (a:OriflameAgent)-[r:REFERRED]->(b:OriflameAgent) WHERE a.name = $relName AND b.name = $myName DELETE r",
                    Map.of("relName", fromName, "myName", getName())));
        }
    }

    public void hasRelationship() {
        try (Session session = ApplicationDataSource.getDriver().session()) {
            List<Record> records = new ArrayList<>();
            Result result = session.run("MATCH (a:OriflameAgent) WHERE a.name = $name RETURN a, [(a)-[r]->(m) | type(r)] as relationships",
                    Values.parameters("name", getName()));
            while (result.hasNext()) {
                Record record = result.next();
                records.add(record);
            }
            for (Record record : records) {
                List<Value> values = record.values();
                List<Node> nodes = new ArrayList<>();
                String relationType = "";
                for (Value value : values) {
                    for (Node node : value.asPath().nodes()) {
                        nodes.add(node);
                    }
                    for (Relationship relationship : value.asPath().relationships()) {
                        relationType = relationship.type();
                    }
                }
                System.out.print("________________________________________________");
                System.out.println("\n" + nodes.get(0).asMap().entrySet().stream()
                        .map(entry -> String.format("%-5s : %s", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("\n")) + "\n\nRelation : " + relationType + "\n\n" + nodes.get(1).asMap().entrySet().stream()
                        .map(entry -> String.format("%-5s : %s", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("\n")));
            }
            if (records.size() != 0) {
                System.out.println("________________________________________________");
            }
        }
    }

    @Override
    public String toString() {
        return "OriflameAgent{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", appointment='" + appointment + '\'' +
                '}';
    }
}
