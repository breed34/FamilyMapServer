package daos;

import exceptions.DataAccessException;
import models.Authtoken;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AuthtokenDaoTest {
    private Database db;
    private Authtoken sampleToken1;
    private Authtoken sampleToken2;
    private AuthtokenDao authtokenDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        sampleToken1 = new Authtoken("123456789", "Bean123");
        sampleToken2 = new Authtoken("987654321", "Bacon456");

        Connection conn = db.getConnection();
        authtokenDao = new AuthtokenDao(conn);
        authtokenDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        Authtoken compareTest = authtokenDao.findByToken(sampleToken1.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(sampleToken1, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        assertThrows(DataAccessException.class, () -> authtokenDao.insert(sampleToken1));
    }

    @Test
    public void findByTokenPass() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        Authtoken compareTest = authtokenDao.findByToken(sampleToken2.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(sampleToken2, compareTest);
    }

    @Test
    public void findByTokenFail() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        Authtoken compareTest = authtokenDao.findByToken("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void findByUserPass() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        Authtoken compareTest = authtokenDao.findByUser(sampleToken2.getUsername());
        assertNotNull(compareTest);
        assertEquals(sampleToken2, compareTest);
    }

    @Test
    public void findByUserFail() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        Authtoken compareTest = authtokenDao.findByUser("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void clearWithoutDataPass() {
        assertDoesNotThrow(() -> authtokenDao.clear());
    }

    @Test
    public void clearWithDataPass() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        Authtoken shouldFind1 = authtokenDao.findByToken(sampleToken1.getAuthtoken());
        Authtoken shouldFind2 = authtokenDao.findByToken(sampleToken2.getAuthtoken());
        assertNotNull(shouldFind1);
        assertNotNull(shouldFind2);

        authtokenDao.clear();
        Authtoken shouldNotFind1 = authtokenDao.findByToken(sampleToken1.getAuthtoken());
        Authtoken shouldNotFind2 = authtokenDao.findByToken(sampleToken2.getAuthtoken());
        assertNull(shouldNotFind1);
        assertNull(shouldNotFind2);
    }

    @Test
    public void clearByUserWithoutDataPass() {
        assertDoesNotThrow(() -> authtokenDao.clearByUser("SomeUser"));
    }

    @Test
    public void clearByUserWithDataPass() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        Authtoken shouldFind1 = authtokenDao.findByToken(sampleToken1.getAuthtoken());
        assertNotNull(shouldFind1);

        authtokenDao.clearByUser(sampleToken1.getUsername());
        Authtoken shouldNotFind1 = authtokenDao.findByToken(sampleToken1.getAuthtoken());
        assertNull(shouldNotFind1);
    }
}