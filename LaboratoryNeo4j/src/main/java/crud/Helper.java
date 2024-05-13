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

public class Helper {
    public static void selectNods() {
        try (Driver driver = ApplicationDataSource.getDriver()) {
            EagerResult result = driver.executableQuery("MATCH (a:OriflameAgent) RETURN a")
                    .withConfig(QueryConfig.builder()
                            .withDatabase("neo4j").build())
                    .execute();
            System.out.println("Agents of oriflame:");
            List<Record> records = result.records();
            for (Record record : records) {
                System.out.println("________________________________________________");
                System.out.println(record.get("a").asNode().asMap().entrySet().stream()
                        .map(entry -> String.format("%-5s : %s", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("\n")));
            }
            if (records.size() != 0) {
                System.out.println("________________________________________________");
            }
        }
    }

    public static void selectRelations() {
        try (Driver driver = ApplicationDataSource.getDriver()) {
            EagerResult result = driver.executableQuery("MATCH a=()-[:REFERRED]->() RETURN a LIMIT 55;")
                    .withConfig(QueryConfig.builder()
                            .withDatabase("neo4j").build())
                    .execute();
            System.out.println("Agents of oriflame relation:");
            List<Record> records = result.records();
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

    public static void selectRoot() {
        try (Driver driver = ApplicationDataSource.getDriver()) {
            EagerResult result = driver.executableQuery("MATCH (a:OriflameAgent) WHERE NOT (a)-->(:Node) RETURN a LIMIT 1")
                    .withConfig(QueryConfig.builder()
                            .withDatabase("neo4j").build())
                    .execute();
            List<Record> records = result.records();
            System.out.println("Root element is:");
            System.out.println("________________________________________________");
            System.out.println(records.get(0).get("a").asMap().entrySet().stream()
                    .map(entry -> String.format("%-5s : %s", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("\n")));
            System.out.println("________________________________________________");
        }
    }

    public static void selectByCity(String city) {
        try (Driver driver = ApplicationDataSource.getDriver()) {
            EagerResult result = driver.executableQuery("MATCH (a:OriflameAgent) WHERE a.city = $city RETURN a")
                    .withParameters(Map.of("city", city))
                    .withConfig(QueryConfig.builder()
                            .withDatabase("neo4j").build())
                    .execute();
            List<Record> records = result.records();
            System.out.println("Agents of oriflame in " + city);
            for (Record record : records) {
                System.out.println("________________________________________________");
                System.out.println(record.get("a").asNode().asMap().entrySet().stream()
                        .map(entry -> String.format("%-5s : %s", entry.getKey(), entry.getValue()))
                        .collect(Collectors.joining("\n")));
            }
            if (records.size() != 0) {
                System.out.println("________________________________________________");
            }
        }
    }
}
