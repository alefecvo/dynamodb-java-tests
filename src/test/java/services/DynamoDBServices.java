package services;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class DynamoDBServices {

    public static DynamoDbClient createClientDataBase(){
        // Create DynamoDB client
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.US_EAST_1) // Change the region as needed
                .endpointOverride(URI.create("http://localhost:4566"))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        return ddb;
    }

    public static void createTableDataBase(DynamoDbClient ddb, String tableName){
        // Create table request
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName(tableName)
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName("Id")
                        .attributeType(ScalarAttributeType.N)
                        .build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName("Id")
                        .keyType(KeyType.HASH)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(5L)
                        .writeCapacityUnits(5L)
                        .build())
                .build();

        // Create the table
        CreateTableResponse createTableResponse = ddb.createTable(createTableRequest);
        System.out.println("Table created: " + createTableResponse.tableDescription().tableName());

    }
    public static void insertItemTable(DynamoDbClient ddb, String tableName, String key, String value){
        // Insert data into the table
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(tableName)
                .item(Map.of(
                        "Id", AttributeValue.builder().n(key).build(),
                        "Name", AttributeValue.builder().s(value).build()
                        // Add more attributes as needed
                ))
                .build();

        ddb.putItem(putItemRequest);
        System.out.println("Data inserted into the table." + tableName);
    }

    public static void deleteTableDataBase(DynamoDbClient ddb,String tableName){
        // Delete table request
        DeleteTableRequest tableRequest = DeleteTableRequest.builder().tableName(tableName).build();

        ddb.deleteTable(tableRequest);

        System.out.println("Table deleted: " + tableRequest.tableName().toString());

    }

    public static void consultItemTableDataBase(DynamoDbClient ddb,String tableName){
        // Create a scan request
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build();

        // Execute the scan request
        ScanResponse scanResponse = ddb.scan(scanRequest);

        // Process the result
        for (Map<String, AttributeValue> item : scanResponse.items()) {
            // Process each item in the result
            System.out.println("Item add with sucesso: " + item);
        }
    }

    public static void validatedItemTableDataBase(DynamoDbClient ddb,String tableName, String key, String value){
        // Create a scan request
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build();

        // Execute the scan request
        ScanResponse scanResponse = ddb.scan(scanRequest);

        for (Map<String, AttributeValue> item : scanResponse.items()) {
            // Create the expected item
            Map<String, AttributeValue> expectedItem = new HashMap<>();
            expectedItem.put("Id", AttributeValue.builder().n(key).build());
            expectedItem.put("Name", AttributeValue.builder().s(value).build());

            // Use assertEquals to compare the expected and actual values
            assertEquals("Items are equal", expectedItem, item);
        }
    }

    public static void closeDataBaseTable(DynamoDbClient ddb){
        // Close the DynamoDB client
        ddb.close();
    }
}