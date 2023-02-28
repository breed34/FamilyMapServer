package daos;

import exceptions.DataAccessException;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person samplePerson1;
    private Person samplePerson2;
    private PersonDao personDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        samplePerson1 = new Person("Bob123A", "Gale", "Bob",
                "Henderson", "m");
        samplePerson2 = new Person("Janet456A", "Gale", "Janet",
                "Smith", "f", "Bob123A", "Mary789A", "Jordan912A");

        Connection conn = db.getConnection();
        personDao = new PersonDao(conn);
        personDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        personDao.insert(samplePerson1);
        Person compareTest = personDao.find(samplePerson1.getPersonID(), samplePerson1.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(samplePerson1, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        personDao.insert(samplePerson1);
        assertThrows(DataAccessException.class, () -> personDao.insert(samplePerson1));
    }

    @Test
    public void findPass() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        Person compareTest = personDao.find(samplePerson2.getPersonID(), samplePerson2.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(samplePerson2, compareTest);
    }

    @Test
    public void findFailNoSuchUser() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        Person compareTest = personDao.find("DOES_NOT_EXIST", samplePerson1.getAssociatedUsername());
        assertNull(compareTest);
    }

    @Test
    public void findFailWrongUser() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        Person compareTest = personDao.find(samplePerson1.getPersonID(), "SomeUser");
        assertNull(compareTest);
    }

    @Test
    public void findByUserPass() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        List<Person> expected = new ArrayList<>();
        expected.add(samplePerson1);
        expected.add(samplePerson2);

        List<Person> compareTest = personDao.findByUser(samplePerson2.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(expected, compareTest);
    }

    @Test
    public void findByUserFail() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        List<Person> compareTest = personDao.findByUser("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void findAllPass() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        List<Person> expected = new ArrayList<>();
        expected.add(samplePerson1);
        expected.add(samplePerson2);

        List<Person> compareTest = personDao.findAll();
        assertNotNull(compareTest);
        assertEquals(expected, compareTest);
    }

    @Test
    public void findAllFail() throws DataAccessException {
        List<Person> compareTest = personDao.findAll();
        assertNull(compareTest);
    }

    @Test
    public void clearWithoutDataPass() {
        assertDoesNotThrow(() -> personDao.clear());
    }

    @Test
    public void clearWithDataPass() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        Person shouldFind1 = personDao.find(samplePerson1.getPersonID(), samplePerson1.getAssociatedUsername());
        Person shouldFind2 = personDao.find(samplePerson2.getPersonID(), samplePerson2.getAssociatedUsername());
        assertNotNull(shouldFind1);
        assertNotNull(shouldFind2);

        personDao.clear();
        Person shouldNotFind1 = personDao.find(samplePerson1.getPersonID(), samplePerson1.getAssociatedUsername());
        Person shouldNotFind2 = personDao.find(samplePerson2.getPersonID(),samplePerson2.getAssociatedUsername());
        assertNull(shouldNotFind1);
        assertNull(shouldNotFind2);
    }

    @Test
    public void clearByUserWithoutDataPass() {
        assertDoesNotThrow(() -> personDao.clearByUser("SomeUser"));
    }

    @Test
    public void clearByUserWithDataPass() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        List<Person> shouldFind1 = personDao.findByUser(samplePerson1.getAssociatedUsername());
        assertNotNull(shouldFind1);

        personDao.clearByUser(samplePerson1.getAssociatedUsername());
        List<Person> shouldNotFind1 = personDao.findByUser(samplePerson1.getAssociatedUsername());
        assertNull(shouldNotFind1);
    }
}