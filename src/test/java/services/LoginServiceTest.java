package services;

import exceptions.DataAccessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import utilities.TestExtensions;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private LoginService loginService;
    private LoginRequest passRequest = new LoginRequest("Bob123", "bobpass33");
    private LoginRequest failRequestWrongUsername = new LoginRequest("JimmyBob432", "bobpass33");
    private LoginRequest failRequestWrongPassword = new LoginRequest("Bob123", "bObPaSs33");

    @BeforeEach
    public void setUp() {
        loginService = new LoginService();

        RegisterRequest registerRequest = new RegisterRequest("Bob123", "bobpass33",
                "bobby678@email.com", "Bob", "Smith", "m");
        new RegisterService().register(registerRequest);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.clearDataByUser(passRequest.getUsername());
    }

    @Test
    public void loginPass() {
        LoginResult result = loginService.login(passRequest);

        assertTrue(result.isSuccess());
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getPersonID());
        assertEquals(passRequest.getUsername(), result.getUsername());
    }

    @Test
    public void loginFailWrongUsername() {
        LoginResult result = loginService.login(failRequestWrongUsername);

        assertFalse(result.isSuccess());
        assertEquals("Error: The username or password for the user was incorrect.", result.getMessage());
    }

    @Test
    public void loginFailWrongPassword() {
        LoginResult result = loginService.login(failRequestWrongPassword);

        assertFalse(result.isSuccess());
        assertEquals("Error: The username or password for the user was incorrect.", result.getMessage());
    }
}
