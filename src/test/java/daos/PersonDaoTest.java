package daos;

import exceptions.DataAccessException;
import models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

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
        Person compareTest = personDao.find(samplePerson1.getPersonId());
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
        Person compareTest = personDao.find(samplePerson2.getPersonId());
        assertNotNull(compareTest);
        assertEquals(samplePerson2, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        Person compareTest = personDao.find("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void findAllPass() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        ArrayList<Person> expected = new ArrayList<>();
        expected.add(samplePerson1);
        expected.add(samplePerson2);

        ArrayList<Person> compareTest = personDao.findAll(samplePerson2.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(expected, compareTest);
    }

    @Test
    public void findAllFail() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        ArrayList<Person> compareTest = personDao.findAll("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        personDao.insert(samplePerson1);
        personDao.insert(samplePerson2);
        personDao.clear();
        Person compareTest1 = personDao.find(samplePerson1.getPersonId());
        Person compareTest2 = personDao.find(samplePerson1.getPersonId());
        assertNull(compareTest1);
        assertNull(compareTest2);
    }
}