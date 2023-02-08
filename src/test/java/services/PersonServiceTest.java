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
    private Authtoken validAuthtoken;
    private Authtoken invalidAuthtoken;
    private PersonRequest passRequest;
    private PersonRequest failRequest;

    @BeforeEach
    public void setUp() throws DataAccessException {
        personService = new PersonService();

        validAuthtoken = TestExtensions.addAuthtoken();
        passRequest = new PersonRequest(validAuthtoken, "Bob123");
        failRequest = new PersonRequest(validAuthtoken, "SomeOtherPerson");
        invalidAuthtoken = new Authtoken("Wrong", "SomeUser");

        addPerson();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.removeAuthtoken();
        removePerson();
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
    void getPersonFailInvalidAuthtoken() {
        PersonResult result = personService.getPerson(passRequest);

        assertFalse(result.isSuccess());
        assertEquals("Error: The authtoken provided was not valid.", result.getMessage());
    }

    @Test
    void getPersonFailPersonNotFound() {
        PersonResult result = personService.getPerson(failRequest);

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
