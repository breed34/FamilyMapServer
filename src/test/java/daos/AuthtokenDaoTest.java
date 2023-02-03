package daos;

import exceptions.DataAccessException;
import models.Authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

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
        Authtoken compareTest = authtokenDao.find(sampleToken1.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(sampleToken1, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        assertThrows(DataAccessException.class, () -> authtokenDao.insert(sampleToken1));
    }

    @Test
    public void findPass() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        Authtoken compareTest = authtokenDao.find(sampleToken2.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(sampleToken2, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        Authtoken compareTest = authtokenDao.find("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void clearDoesNotThrowException() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        authtokenDao.clear();
    }

    @Test
    public void clearDataIsRemovedFromDatabase() throws DataAccessException {
        authtokenDao.insert(sampleToken1);
        authtokenDao.insert(sampleToken2);
        Authtoken shouldFind1 = authtokenDao.find(sampleToken1.getAuthtoken());
        Authtoken shouldFind2 = authtokenDao.find(sampleToken2.getAuthtoken());
        assertNotNull(shouldFind1);
        assertNotNull(shouldFind2);

        authtokenDao.clear();
        Authtoken shouldNotFind1 = authtokenDao.find(sampleToken1.getAuthtoken());
        Authtoken shouldNotFind2 = authtokenDao.find(sampleToken2.getAuthtoken());
        assertNull(shouldNotFind1);
        assertNull(shouldNotFind2);
    }
}