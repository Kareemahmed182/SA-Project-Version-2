package test;

import data_handlers.DataHandler;

import java.util.List;

public class TestWriteData {
    public static void main(String[] args) {
        testWriteData();
    }

    public static void testWriteData() {
        // Create a simple list of test data
        List<String> testList = List.of("User1", "User2", "User3");

        // Use DataHandler to write the data to a new file
        DataHandler<String> testHandler = new DataHandler<>("test.json");

        // Call writeData to write the data to the file
        testHandler.writeData(testList);

        // Debug message to confirm the data was written
        System.out.println("Data has been written to test.json.");
    }
}
