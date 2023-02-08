package services;

import daos.Database;
import daos.EventDao;
import daos.PersonDao;
import exceptions.DataAccessException;
import models.Authtoken;
import models.Event;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventsRequest;
import request.PersonsRequest;
import result.EventsResult;
import result.PersonsResult;
import utilities.TestExtensions;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonsServiceTest {
    private PersonsService personsService;
    private PersonsRequest sampleRequest;
    private PersonsRequest failRequestInvalidAuthtoken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        personsService = new PersonsService();

        Authtoken validAuthtoken = TestExtensions.addAuthtoken();
        sampleRequest = new PersonsRequest(validAuthtoken);

        Authtoken invalidAuthtoken = new Authtoken("Wrong", "SomeUser");
        failRequestInvalidAuthtoken = new PersonsRequest(invalidAuthtoken);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.removeAuthtoken();
    }

    @Test
    public void getEventsPass() throws DataAccessException {
        addPersons();

        ArrayList<Person> expected = new ArrayList<>();
        expected.add(new Person("Bob123A", "Gale", "Bob",
                "Henderson", "m"));
        expected.add(new Person("Janet456A", "Gale", "Janet",
                "Smith", "f", "Bob123A", "Mary789A", "Jordan912A"));

        PersonsResult result =  personsService.getPersons(sampleRequest);
        assertTrue(result.isSuccess());
        assertEquals(expected, result.getData());

        removePersons();
    }

    @Test
    public void getEventsFailInvalidAuthtoken() {
        PersonsResult result = personsService.getPersons(failRequestInvalidAuthtoken);

        assertFalse(result.isSuccess());
        assertEquals("Error: The authtoken provided was not valid.", result.getMessage());
    }

    @Test
    public void getEventsFailPersonNotFound() {
        PersonsResult result = personsService.getPersons(sampleRequest);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired persons were not found.", result.getMessage());
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
