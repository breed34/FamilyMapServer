package services;

import exceptions.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.FillRequest;
import request.RegisterRequest;
import result.FillResult;
import utilities.TestExtensions;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private FillService fillService;
    private FillRequest passRequest1;
    private FillRequest passRequest2;
    private FillRequest failRequest;

    @BeforeEach
    public void setUp() {
        fillService = new FillService();
        passRequest1 = new FillRequest("Bob123");
        passRequest2 = new FillRequest("Bob123", 5);
        failRequest = new FillRequest("SomeOtherUser");

        RegisterRequest registerRequest = new RegisterRequest("Bob123", "bobpass33",
                "bobby678@email.com", "Bob", "Smith", "m");
        new RegisterService().register(registerRequest);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.clearDataByUser(passRequest1.getUsername());
    }

    @Test
    public void fillWithDefaultGenerationsPass() {
        FillResult result = fillService.fill(passRequest1);
        assertTrue(result.isSuccess());
        assertEquals("Successfully added 31 persons and 91 events to the database.", result.getMessage());
    }

    @Test
    public void fillWithFiveGenerationsPass() {
        FillResult result = fillService.fill(passRequest2);
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
