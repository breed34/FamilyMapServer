package daos;

import exceptions.DataAccessException;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventDaoTest {
    private Database db;
    private Event sampleEvent1;
    private Event sampleEvent2;
    private EventDao eventDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        sampleEvent1 = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        sampleEvent2 = new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023);

        Connection conn = db.getConnection();
        eventDao = new EventDao(conn);
        eventDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        Event compareTest = eventDao.find(sampleEvent1.getEventID(), sampleEvent1.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(sampleEvent1, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        assertThrows(DataAccessException.class, () -> eventDao.insert(sampleEvent1));
    }

    @Test
    public void findPass() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        Event compareTest = eventDao.find(sampleEvent2.getEventID(), sampleEvent2.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(sampleEvent2, compareTest);
    }

    @Test
    public void findFailNoSuchEvent() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        Event compareTest = eventDao.find("DOES_NOT_EXIST", sampleEvent1.getAssociatedUsername());
        assertNull(compareTest);
    }

    @Test
    public void findFailWrongUser() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        Event compareTest = eventDao.find(sampleEvent1.getEventID(), "SomeUser");
        assertNull(compareTest);
    }

    @Test
    public void findByUserPass() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        List<Event> expected = new ArrayList<>();
        expected.add(sampleEvent1);
        expected.add(sampleEvent2);

        List<Event> compareTest = eventDao.findByUser(sampleEvent2.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(expected, compareTest);
    }

    @Test
    public void findByUserFail() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        List<Event> compareTest = eventDao.findByUser("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void findAllPass() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        List<Event> expected = new ArrayList<>();
        expected.add(sampleEvent1);
        expected.add(sampleEvent2);

        List<Event> compareTest = eventDao.findAll();
        assertNotNull(compareTest);
        assertEquals(expected, compareTest);
    }

    @Test
    public void findAllFail() throws DataAccessException {
        List<Event> compareTest = eventDao.findAll();
        assertNull(compareTest);
    }

    @Test
    public void clearWithoutDataPass() {
        assertDoesNotThrow(() -> eventDao.clear());
    }

    @Test
    public void clearWithDataPass() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        Event shouldFind1 = eventDao.find(sampleEvent1.getEventID(), sampleEvent1.getAssociatedUsername());
        Event shouldFind2 = eventDao.find(sampleEvent2.getEventID(), sampleEvent2.getAssociatedUsername());
        assertNotNull(shouldFind1);
        assertNotNull(shouldFind2);

        eventDao.clear();
        Event shouldNotFind1 = eventDao.find(sampleEvent1.getEventID(), sampleEvent1.getAssociatedUsername());
        Event shouldNotFind2 = eventDao.find(sampleEvent2.getEventID(), sampleEvent2.getAssociatedUsername());
        assertNull(shouldNotFind1);
        assertNull(shouldNotFind2);
    }

    @Test
    public void clearByUserWithoutDataPass() {
        assertDoesNotThrow(() -> eventDao.clearByUser("SomeUser"));
    }

    @Test
    public void clearByUserWithDataPass() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        List<Event> shouldFind1 = eventDao.findByUser(sampleEvent1.getAssociatedUsername());
        assertNotNull(shouldFind1);

        eventDao.clearByUser(sampleEvent1.getAssociatedUsername());
        List<Event> shouldNotFind1 = eventDao.findByUser(sampleEvent1.getAssociatedUsername());
        assertNull(shouldNotFind1);
    }
}