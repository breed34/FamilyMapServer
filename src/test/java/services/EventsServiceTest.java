package services;

import daos.Database;
import daos.EventDao;
import exceptions.DataAccessException;
import models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventsRequest;
import request.PersonsRequest;
import result.EventsResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EventsServiceTest {
    private EventsService eventsService;
    private EventsRequest passRequest;
    private EventsRequest failRequest;

    @BeforeEach
    public void setUp() throws DataAccessException {
        eventsService = new EventsService();
        passRequest = new EventsRequest("Gale");
        failRequest = new EventsRequest("Bob");

        addEvents();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        removeEvents();
    }

    @Test
    public void getAllEventsPass() {
        ArrayList<Event> expected = new ArrayList<>();
        expected.add(new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023));
        expected.add(new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));

        EventsResult result = eventsService.getAllEvents(passRequest);

        assertTrue(result.isSuccess());
        assertEquals(expected, result.getData());
    }

    @Test
    void getAllEventsFail() {
        EventsResult result = eventsService.getAllEvents(failRequest);

        assertFalse(result.isSuccess());
        assertEquals("Error: No events were found.", result.getMessage());
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
