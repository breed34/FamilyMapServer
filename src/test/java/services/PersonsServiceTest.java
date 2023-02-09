package services;

import daos.Database;
import daos.PersonDao;
import exceptions.DataAccessException;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.AuthenticatedRequest;
import result.PersonsResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonsServiceTest {
    private PersonsService personsService;
    private AuthenticatedRequest passRequest;
    private AuthenticatedRequest failRequest;

    @BeforeEach
    public void setUp() throws DataAccessException {
        personsService = new PersonsService();
        passRequest = new AuthenticatedRequest("Gale");
        failRequest = new AuthenticatedRequest("Bob");

        addPersons();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        removePersons();
    }

    @Test
    public void getAllPersonsPass() {
        ArrayList<Person> expected = new ArrayList<>();
        expected.add(new Person("Bob123A", "Gale", "Bob",
                "Henderson", "m"));
        expected.add(new Person("Janet456A", "Gale", "Janet",
                "Smith", "f", "Bob123A", "Mary789A", "Jordan912A"));

        PersonsResult result = personsService.getAllPersons(passRequest);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getData());
    }

    @Test
    void getAllPersonsFailNoSuchEvent() {
        PersonsResult result = personsService.getAllPersons(failRequest);

        assertFalse(result.isSuccess());
        assertEquals("Error: No persons were found.", result.getMessage());
    }

    private void addPersons() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        Person person1 = new Person("Bob123A", "Gale", "Bob",
                "Henderson", "m");

        Person person2 = new Person("Janet456A", "Gale", "Janet",
                "Smith", "f", "Bob123A", "Mary789A", "Jordan912A");

        new PersonDao(db.getConnection()).insert(person1);
        new PersonDao(db.getConnection()).insert(person2);
        db.closeConnection(true);
    }

    private void removePersons() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        new PersonDao(db.getConnection()).clearByUser("Gale");
        db.closeConnection(true);
    }
}
