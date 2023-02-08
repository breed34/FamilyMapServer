package services;

import daos.Database;
import daos.EventDao;
import exceptions.DataAccessException;
import models.Authtoken;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventsRequest;
import result.EventsResult;
import utilities.TestExtensions;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventsServiceTest {
    private EventsService eventsService;
    private EventsRequest sampleRequest;
    private EventsRequest failRequestInvalidAuthtoken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        eventsService = new EventsService();

        Authtoken validAuthtoken = TestExtensions.addAuthtoken();
        sampleRequest = new EventsRequest(validAuthtoken);

        Authtoken invalidAuthtoken = new Authtoken("Wrong", "SomeUser");
        failRequestInvalidAuthtoken = new EventsRequest(invalidAuthtoken);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        TestExtensions.removeAuthtoken();
    }

    @Test
    public void getEventsPass() throws DataAccessException {
        addEvents();

        ArrayList<Event> expected = new ArrayList<>();
        expected.add(new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023));
        expected.add(new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));

        EventsResult result =  eventsService.getEvents(sampleRequest);
        assertTrue(result.isSuccess());
        assertEquals(expected, result.getData());

        removeEvents();
    }

    @Test
    public void getEventsFailInvalidAuthtoken() {
        EventsResult result = eventsService.getEvents(failRequestInvalidAuthtoken);

        assertFalse(result.isSuccess());
        assertEquals("Error: The authtoken provided was not valid.", result.getMessage());
    }

    @Test
    public void getEventsFailPersonNotFound() {
        EventsResult result = eventsService.getEvents(sampleRequest);

        assertFalse(result.isSuccess());
        assertEquals("Error: The desired events were not found.", result.getMessage());
    }

    private void addEvents() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        Event event1 = new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023);
        Event event2 = new Event("Biking_123A", "Gale", "Gale123A",
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
        db.closeConnection(true);
    }
}
