package daos;

import exceptions.DataAccessException;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

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
        Event compareTest = eventDao.find(sampleEvent1.getEventId());
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
        Event compareTest = eventDao.find(sampleEvent2.getEventId());
        assertNotNull(compareTest);
        assertEquals(sampleEvent2, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        Event compareTest = eventDao.find("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void findAllPass() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        ArrayList<Event> expected = new ArrayList<>();
        expected.add(sampleEvent1);
        expected.add(sampleEvent2);

        ArrayList<Event> compareTest = eventDao.findAll(sampleEvent2.getAssociatedUsername());
        assertNotNull(compareTest);
        assertEquals(expected, compareTest);
    }

    @Test
    public void findAllFail() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        ArrayList<Event> compareTest = eventDao.findAll("DOES_NOT_EXIST");
        assertNull(compareTest);
    }

    @Test
    public void clearPass() throws DataAccessException {
        eventDao.insert(sampleEvent1);
        eventDao.insert(sampleEvent2);
        eventDao.clear();
        Event compareTest1 = eventDao.find(sampleEvent1.getEventId());
        Event compareTest2 = eventDao.find(sampleEvent1.getEventId());
        assertNull(compareTest1);
        assertNull(compareTest2);
    }
}