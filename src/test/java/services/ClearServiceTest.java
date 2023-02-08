package services;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.ClearResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService clearService;

    @BeforeEach
    public void setUp() {
        clearService = new ClearService();
    }

    @Test
    public void clearWithoutDataPass() {
        ClearResult result = clearService.clear();
        assertTrue(result.isSuccess());
        assertEquals("Clear succeeded.", result.getMessage());
    }

    @Test
    public void clearWithDataPass() {
        RegisterRequest registerRequest = new RegisterRequest("Bob123", "bobpass33",
                "bobby678@email.com", "Bob", "Smith", "m");
        RegisterResult registerResult = new RegisterService().register(registerRequest);
        assertTrue(registerResult.isSuccess());

        ClearResult result = clearService.clear();
        assertTrue(result.isSuccess());
        assertEquals("Clear succeeded.", result.getMessage());
    }
}
