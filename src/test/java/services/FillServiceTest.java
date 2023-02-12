package services;

import exceptions.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.FillRequest;
import requests.RegisterRequest;
import results.FillResult;
import utilities.TestExtensions;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private FillService fillService;
    private FillRequest passRequestDefaultGenerations;
    private FillRequest passRequestFiveGenerations;
    private FillRequest failRequest;

    @BeforeEach
    public void setUp() {
        fillService = new FillService();
        passRequestDefaultGenerations = new FillRequest("Bob123");
        passRequestFiveGenerations = new FillRequest("Bob123", 5);
        failRequest = new FillRequest("SomeOtherUser");

        RegisterRequest registerRequest = new RegisterRequest("Bob123", "bobpass33",
                "bobby678@email.com", "Bob", "Smith", "m");
        new RegisterService().register(registerRequest);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.clearDataByUser(passRequestDefaultGenerations.getUsername());
    }

    @Test
    public void fillWithDefaultGenerationsPass() {
        FillResult result = fillService.fill(passRequestDefaultGenerations);
        assertTrue(result.isSuccess());
        assertEquals("Successfully added 31 persons and 91 events to the database.", result.getMessage());
    }

    @Test
    public void fillWithFiveGenerationsPass() {
        FillResult result = fillService.fill(passRequestFiveGenerations);
        assertTrue(result.isSuccess());
        assertEquals("Successfully added 63 persons and 187 events to the database.", result.getMessage());
    }

    @Test
    public void fillFail() {
        FillResult result = fillService.fill(failRequest);
        assertFalse(result.isSuccess());
        assertEquals("Error: No user with that username could be found.", result.getMessage());
    }
}
