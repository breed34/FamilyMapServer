package services;

import exceptions.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.RegisterResult;
import utilities.TestExtensions;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private RegisterService registerService;
    private RegisterRequest sampleRequest;

    @BeforeEach
    public void setUp() {
        registerService = new RegisterService();
        sampleRequest = new RegisterRequest("Bob123", "bobpass33", "bobby678@email.com",
                "Bob", "Smith", "m");
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.clearDataByUser(sampleRequest.getUsername());
    }

    @Test
    public void registerPass() {
        RegisterResult result = registerService.register(sampleRequest);

        assertTrue(result.isSuccess());
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getPersonID());
        assertEquals("Bob123", result.getUsername());
    }

    @Test
    public void registerFail() {
        registerService.register(sampleRequest);
        RegisterResult result = registerService.register(sampleRequest);

        assertFalse(result.isSuccess());
        assertEquals("Error: A user with that username already exists.", result.getMessage());
    }
}
