package services;

import daos.Database;
import daos.PersonDao;
import exceptions.DataAccessException;
import models.Authtoken;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.PersonRequest;
import result.PersonResult;
import utilities.TestExtensions;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    private PersonService personService;
    private PersonRequest passRequest;
    private PersonRequest failRequestPersonNotFound;
    private PersonRequest failRequestWrongUsername;
    private PersonRequest failRequestInvalidAuthtoken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        personService = new PersonService();

        Authtoken validAuthtoken = TestExtensions.addAuthtoken();
        passRequest = new PersonRequest(validAuthtoken, "Bob123");
        failRequestPersonNotFound = new PersonRequest(validAuthtoken, "SomeOtherPerson");
        failRequestWrongUsername = new PersonRequest(validAuthtoken, "Janet456A");

        Authtoken invalidAuthtoken = new Authtoken("Wrong", "SomeUser");
        failRequestInvalidAuthtoken = new PersonRequest(invalidAuthtoken, "Bob123");

        addPersons();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.removeAuthtoken();
        removePersons();
    }

    @Test
    public void getPersonPass() {
        Person expected = new Person("Bob123", "Gale", "Bob",
                "Henderson", "m");

        PersonResult result = personService.getPerson(passRequest);
        Person actual = extractPersonFromResult(result);

        assertTrue(result.isSuccess());
        assertEquals(expected, actual);
    }

    @Test
    public void getPersonFailInvalidAuthtoken() {
        PersonResult result = personService.getPerson(failRequestInvalidAuthtoken);

        assertFalse(result.isSuccess());
        assertEquals("Error: The authtoken provided was not valid.", result.getMessage());
    }

    @Test
    public void getPersonFailPersonNotFound() {
        PersonResult result = personService.getPerson(failRequestPersonNotFound);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired person was not found.", result.getMessage());
    }

    @Test
    public void getPersonFailWrongUsername() {
        PersonResult result = personService.getPerson(failRequestWrongUsername);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired person was not found.", result.getMessage());
    }

    private void addPersons() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        Person person1 = new Person("Bob123", "Gale", "Bob",
                "Henderson", "m");
        Person person2 = new Person("Janet456A", "James12", "Janet",
                "Smith", "f", "Bob123A", "Mary789A", "Jordan912A");
        new PersonDao(db.getConnection()).insert(person1);
        new PersonDao(db.getConnection()).insert(person2);
        db.closeConnection(true);
    }

    private void removePersons() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        new PersonDao(db.getConnection()).clearByUser("Gale");
        new PersonDao(db.getConnection()).clearByUser("James12");
        db.closeConnection(true);
    }

    private Person extractPersonFromResult(PersonResult result) {
        return new Person(result.getPersonId(),
                result.getAssociatedUsername(),
                result.getFirstName(),
                result.getLastName(),
                result.getGender(),
                result.getFatherId(),
                result.getMotherId(),
                result.getSpouseId());
    }
}
