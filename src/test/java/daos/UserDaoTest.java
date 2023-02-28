package daos;

import exceptions.DataAccessException;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private Database db;
    private User sampleUser1;
    private User sampleUser2;
    private UserDao userDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        sampleUser1 = new User("Bean123", "cocoPowPow33", "bean123@email.com",
                "Bob", "Henderson", "m", "Bob123A");
        sampleUser2 = new User("Bacon456", "sizzle345", "bacon456@email.com",
                "Janis", "Jones", "f", "Janis789B");

        Connection conn = db.getConnection();
        userDao = new UserDao(conn);
        userDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        userDao.insert(sampleUser1);
        User compareTest = userDao.find(sampleUser1.getUsername());
        assertNotNull(compareTest);
        assertEquals(sampleUser1, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        userDao.insert(sampleUser1);
        assertThrows(DataAccessException.class, () -> userDao.insert(sampleUser1));
    }

    @Test
    public void findPass() throws DataAccessException {
        userDao.insert(sampleUser1);
        userDao.insert(sampleUser2);
        User compareTest = userDao.find(sampleUser2.getUsername());
        assertNotNull(compareTest);
        assertEquals(sampleUser2, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        userDao.insert(sampleUser1);
        userDao.insert(sampleUser2);
        User compareTest = userDao.find("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void findAllPass() throws DataAccessException {
        userDao.insert(sampleUser1);
        userDao.insert(sampleUser2);
        List<User> expected = new ArrayList<>();
        expected.add(sampleUser1);
        expected.add(sampleUser2);

        List<User> compareTest = userDao.findAll();
        assertNotNull(compareTest);
        assertEquals(expected, compareTest);
    }

    @Test
    public void findAllFail() throws DataAccessException {
        List<User> compareTest = userDao.findAll();
        assertNull(compareTest);
    }

    @Test
    public void clearWithoutDataPass() {
        assertDoesNotThrow(() -> userDao.clear());
    }

    @Test
    public void clearWithDataPass() throws DataAccessException {
        userDao.insert(sampleUser1);
        userDao.insert(sampleUser2);
        User shouldFind1 = userDao.find(sampleUser1.getUsername());
        User shouldFind2 = userDao.find(sampleUser2.getUsername());
        assertNotNull(shouldFind1);
        assertNotNull(shouldFind2);

        userDao.clear();
        User shouldNotFind1 = userDao.find(sampleUser1.getUsername());
        User shouldNotFind2 = userDao.find(sampleUser2.getUsername());
        assertNull(shouldNotFind1);
        assertNull(shouldNotFind2);
    }

    @Test
    public void removeUserWithoutDataPass() {
        assertDoesNotThrow(() -> userDao.removeUser("SomeUser"));
    }

    @Test
    public void removeUserWithDataPass() throws DataAccessException {
        userDao.insert(sampleUser1);
        User shouldFind1 = userDao.find(sampleUser1.getUsername());
        assertNotNull(shouldFind1);

        userDao.removeUser(sampleUser1.getUsername());
        User shouldNotFind1 = userDao.find(sampleUser1.getUsername());
        assertNull(shouldNotFind1);
    }
}