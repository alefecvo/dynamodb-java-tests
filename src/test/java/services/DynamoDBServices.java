package services;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.List;

public class DynamoDBServices {
    private final AmazonDynamoDB client;
    private final DynamoDB dynamoDB;

    public DynamoDBServices() {
        client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "http://localhost:4566", "us-west-2"))
                .build();

        System.out.println("Connection success...");

        dynamoDB = new DynamoDB(client);
    }

    public boolean verifyTable(String tableName) {
        try {
            Table table = dynamoDB.getTable(tableName);
            table.describe();
            System.out.println("Table " + tableName + " already exists.");
            return true;
        } catch (ResourceNotFoundException e) {
            System.out.println("Table " + tableName + " does not exist.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createTable(String tableName) {
        if (!verifyTable(tableName)) {
            try {
                Table table = dynamoDB.createTable(tableName,
                        List.of(new KeySchemaElement("ID", KeyType.HASH)),
                        List.of(new AttributeDefinition("ID", ScalarAttributeType.S)),
                        new ProvisionedThroughput(10L, 10L));
                table.waitForActive();
                System.out.println("Table " + tableName + " created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertItem(String tableName, String id, String title, String description) {
        Table table = dynamoDB.getTable(tableName);
        Item item = new Item()
                .withPrimaryKey("ID", id)
                .withString("Title", title)
                .withString("Description", description);
        table.putItem(item);
    }

    public Item getItem(String tableName, String id) {
        Table table = dynamoDB.getTable(tableName);
        return table.getItem("ID", id);
    }

    public void deleteItem(String tableName, String id) {
        Table table = dynamoDB.getTable(tableName);
        table.deleteItem("ID", id);
    }

    public void close() {
        client.shutdown();
    }
}