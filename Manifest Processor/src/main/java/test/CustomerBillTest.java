package test;

import com.manifestprocessor.model.CustomerBill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileWriter;
import java.io.IOException;

class CustomerBillTest {

    private static final String TEST_FILE_PATH = "test_customer_bills.txt";
    private CustomerBill customerBill;

    @BeforeEach
    void setUp() throws IOException {
        // Create a test file with sample data
        try (FileWriter writer = new FileWriter(TEST_FILE_PATH)) {
            writer.write("7 | 123456789 | John Doe | 123 Main St | 2 | 150 | Door 1\n");
            writer.write("8 | 987654321| Jane Smith | 456 Elm St | 1 | 75 | Door 2\n");
        }
        customerBill = new CustomerBill(TEST_FILE_PATH);
    }

    @Test
    void testGetManifestForTrailer() {
        String expectedOutput = "Trailer Number: 7\n" +
                "Order Number: 123456789\n" +
                "Customer Name: John Doe\n" +
                "Customer Address: 123 Main St\n" +
                "Handling Units: 2\n" +
                "Weight: 150.5\n" +
                "Delivery Door Assigned: Door 1\n\n";

        String result = customerBill.getManifestForTrailer("7");
        assertEquals(expectedOutput, result, "Manifest for trailer 7 should match");
    }

    @Test
    void testGetManifestForTrailerNotFound() {
        String expectedOutput = "No bills found for trailer number: 9\n";
        String result = customerBill.getManifestForTrailer("9");
        assertEquals(expectedOutput, result, "Manifest for non-existent trailer should return not found message");
    }

    @Test
    void testReadDatasetWithInvalidFilePath() {
        String invalidFilePath = "non_existent_file.txt";
        assertThrows(IOException.class, () -> {
            new CustomerBill(invalidFilePath);
        }, "Creating CustomerBill with an invalid file path should throw IOException");
    }

    @AfterEach
    void tearDown() {
        // Clean up the test file after each test
        java.io.File file = new java.io.File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}