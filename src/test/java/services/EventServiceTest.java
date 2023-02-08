package services;

import daos.Database;
import daos.EventDao;
import exceptions.DataAccessException;
import models.Authtoken;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventRequest;
import result.EventResult;
import utilities.TestExtensions;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest {
    private EventService eventService;
    private Authtoken validAuthtoken;
    private Authtoken invalidAuthtoken;
    private EventRequest passRequest;
    private EventRequest failRequest;

    @BeforeEach
    public void setUp() throws DataAccessException {
        eventService = new EventService();

        validAuthtoken = TestExtensions.addAuthtoken();
        passRequest = new EventRequest(validAuthtoken, "Skiing_456A");
        failRequest = new EventRequest(validAuthtoken, "SomeOtherEvent");
        invalidAuthtoken = new Authtoken("Wrong", "SomeUser");

        addEvent();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.removeAuthtoken();
        removeEvent();
    }

    @Test
    public void getPersonPass() {
        Event expected = new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023);

        EventResult result = eventService.getEvent(passRequest);
        Event actual = extractEventFromResult(result);

        assertTrue(result.isSuccess());
        assertEquals(expected, actual);
    }

    @Test
    void getPersonFailInvalidAuthtoken() {
        EventResult result = eventService.getEvent(passRequest);

        assertFalse(result.isSuccess());
        assertEquals("Error: The authtoken provided was not valid.", result.getMessage());
    }

    @Test
    void getPersonFailPersonNotFound() {
        EventResult result = eventService.getEvent(failRequest);

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

    private Event extractEventFromResult(EventResult result) {
        return new Event(result.getEventId(),
                result.getAssociatedUsername(),
                result.getPersonId(),
                result.getLatitude(),
                result.getLongitude(),
                result.getCountry(),
                result.getCity(),
                result.getEventType(),
                result.getYear());
    }
}
