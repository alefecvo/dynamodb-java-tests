package steps;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.DynamoDBServices;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBStepDefinitionsJUnit {

    DynamoDbClient ddb;

    @BeforeEach
    public void testDynamoDBRestAssured01() {
        ddb = DynamoDBServices.createClientDataBase();
    }

    @Test
    public void testDynamoDBRestAssured02() {
        DynamoDBServices.createTableDataBase(ddb, "Carros");
        DynamoDBServices.insertItemTable(ddb, "Carros",
                "1", "Fusca");
        DynamoDBServices.deleteTableDataBase(ddb,"Carros");

    }

    @AfterEach
    public void testDynamoDBRestAssured03() {
        DynamoDBServices.closeDataBaseTable(ddb);
    }

}
