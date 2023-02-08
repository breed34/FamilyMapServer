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

public class EventServiceTest {
    private EventService eventService;
    private EventRequest passRequest;
    private EventRequest failRequestEventNotFound;
    private EventRequest failRequestWrongUsername;
    private EventRequest failRequestInvalidAuthtoken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        eventService = new EventService();

        Authtoken validAuthtoken = TestExtensions.addAuthtoken();
        passRequest = new EventRequest(validAuthtoken, "Skiing_456A");
        failRequestEventNotFound = new EventRequest(validAuthtoken, "SomeOtherEvent");
        failRequestWrongUsername = new EventRequest(validAuthtoken, "Biking_123A");

        Authtoken invalidAuthtoken = new Authtoken("Wrong", "SomeUser");
        failRequestInvalidAuthtoken = new EventRequest(invalidAuthtoken, "Skiing_456A");

        addEvents();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.removeAuthtoken();
        removeEvents();
    }

    @Test
    public void getEventPass() {
        Event expected = new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023);

        EventResult result = eventService.getEvent(passRequest);
        Event actual = extractEventFromResult(result);

        assertTrue(result.isSuccess());
        assertEquals(expected, actual);
    }

    @Test
    public void getEventFailInvalidAuthtoken() {
        EventResult result = eventService.getEvent(failRequestInvalidAuthtoken);

        assertFalse(result.isSuccess());
        assertEquals("Error: The authtoken provided was not valid.", result.getMessage());
    }

    @Test
    public void getEventFailPersonNotFound() {
        EventResult result = eventService.getEvent(failRequestEventNotFound);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired event was not found.", result.getMessage());
    }

    @Test
    public void getEventFailWrongUsername() {
        EventResult result = eventService.getEvent(failRequestWrongUsername);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired event was not found.", result.getMessage());
    }

    private void addEvents() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        Event event1 = new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023);
        Event event2 = new Event("Biking_123A", "James12", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        new EventDao(db.getConnection()).insert(event1);
        new EventDao(db.getConnection()).insert(event2);

        db.closeConnection(true);
    }

    private void removeEvents() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        new EventDao(db.getConnection()).clearByUser("Gale");
        new EventDao(db.getConnection()).clearByUser("James12");
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
