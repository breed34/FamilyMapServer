package services;

import daos.Database;
import daos.PersonDao;
import exceptions.DataAccessException;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.PersonRequest;
import results.PersonResult;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    private PersonService personService;
    private PersonRequest passRequest;
    private PersonRequest failRequestWrongUser;
    private PersonRequest failRequestNoSuchPerson;

    @BeforeEach
    public void setUp() throws DataAccessException {
        personService = new PersonService();
        passRequest = new PersonRequest("Bob123", "Gale");
        failRequestWrongUser = new PersonRequest("Bob123", "Bob");
        failRequestNoSuchPerson = new PersonRequest("SomeOtherPerson", "Gale");

        addPerson();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        removePerson();
    }

    @Test
    public void getPersonPass() {
        Person expected = new Person("Bob123", "Gale", "Bob",
                "Henderson", "m");

        PersonResult result = personService.getPerson(passRequest);
        Person actual = createPersonFromResult(result);

        assertTrue(result.isSuccess());
        assertEquals(expected, actual);
    }

    @Test
    void getPersonFailWrongUser() {
        PersonResult result = personService.getPerson(failRequestWrongUser);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired person was not found.", result.getMessage());
    }

    @Test
    void getPersonFailNoSuchPerson() {
        PersonResult result = personService.getPerson(failRequestNoSuchPerson);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired person was not found.", result.getMessage());
    }

    private void addPerson() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        Person person = new Person("Bob123", "Gale", "Bob",
                "Henderson", "m");
        new PersonDao(db.getConnection()).insert(person);
        db.closeConnection(true);
    }

    private void removePerson() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        new PersonDao(db.getConnection()).clearByUser("Gale");
        db.closeConnection(true);
    }

    private Person createPersonFromResult(PersonResult result) {
        return new Person(result.getPersonID(),
                result.getAssociatedUsername(),
                result.getFirstName(),
                result.getLastName(),
                result.getGender(),
                result.getFatherID(),
                result.getMotherID(),
                result.getSpouseID());
    }
}
