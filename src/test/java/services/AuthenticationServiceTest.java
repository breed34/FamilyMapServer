package services;

import daos.AuthtokenDao;
import daos.Database;
import exceptions.DataAccessException;
import models.Authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.AuthenticationRequest;
import results.AuthenticationResult;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {
    private AuthenticationService authService;
    private AuthenticationRequest passRequest;
    private AuthenticationRequest failRequest;

    @BeforeEach
    public void setUp() throws DataAccessException {
        authService = new AuthenticationService();
        passRequest = new AuthenticationRequest("123");
        failRequest = new AuthenticationRequest("SomeOtherToken");

        addAuthtoken();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        removeAuthtoken();
    }

    @Test
    public void authenticatePass() {
        AuthenticationResult result = authService.authenticate(passRequest);

        assertTrue(result.isSuccess());
        assertEquals("Billy", result.getUsername());
    }

    @Test
    public void authenticateFail() {
        AuthenticationResult result = authService.authenticate(failRequest);

        assertFalse(result.isSuccess());
        assertEquals("Error: The provided authtoken was invalid.", result.getMessage());
    }

    private void addAuthtoken() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        Authtoken authtoken = new Authtoken("123", "Billy");
        new AuthtokenDao(db.getConnection()).insert(authtoken);

        db.closeConnection(true);
    }

    private void removeAuthtoken() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        new AuthtokenDao(db.getConnection()).clearByUser("Billy");

        db.closeConnection(true);
    }
}
