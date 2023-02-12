package services;

import daos.Database;
import daos.EventDao;
import exceptions.DataAccessException;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventRequest;
import result.EventResult;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    private EventService eventService;
    private EventRequest passRequest;
    private EventRequest failRequestWrongUser;
    private EventRequest failRequestNoSuchEvent;

    @BeforeEach
    public void setUp() throws DataAccessException {
        eventService = new EventService();
        passRequest = new EventRequest("Skiing_456A", "Gale");
        failRequestWrongUser = new EventRequest("Skiing_456A", "Bob");
        failRequestNoSuchEvent = new EventRequest("SomeOtherEvent", "Gale");

        addEvent();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        removeEvent();
    }

    @Test
    public void getEventPass() {
        Event expected = new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023);

        EventResult result = eventService.getEvent(passRequest);
        Event actual = createEventFromResult(result);

        assertTrue(result.isSuccess());
        assertEquals(expected, actual);
    }

    @Test
    void getEventFailWrongUser() {
        EventResult result = eventService.getEvent(failRequestWrongUser);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired event was not found.", result.getMessage());
    }

    @Test
    void getEventFailNoSuchEvent() {
        EventResult result = eventService.getEvent(failRequestNoSuchEvent);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired event was not found.", result.getMessage());
    }

    private void addEvent() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        Event event = new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023);
        new EventDao(db.getConnection()).insert(event);
        db.closeConnection(true);
    }

    private void removeEvent() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        new EventDao(db.getConnection()).clearByUser("Gale");
        db.closeConnection(true);
    }

    private Event createEventFromResult(EventResult result) {
        return new Event(result.getEventID(),
                result.getAssociatedUsername(),
                result.getPersonID(),
                result.getLatitude(),
                result.getLongitude(),
                result.getCountry(),
                result.getCity(),
                result.getEventType(),
                result.getYear());
    }
}
