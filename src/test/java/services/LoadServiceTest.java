package services;

import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoadRequest;
import results.LoadResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {
    private LoadService loadService;
    private LoadRequest passRequest1;
    private LoadRequest passRequest2;

    @BeforeEach
    public void setUp() {
        loadService = new LoadService();
        passRequest1 = generateRequest1();
        passRequest2 = generateRequest2();
    }

    @AfterEach
    public void tearDown() {
        new ClearService().clear();
    }

    @Test
    public void loadPass1() {
        LoadResult result = loadService.load(passRequest1);
        assertTrue(result.isSuccess());
        assertEquals("Successfully added 2 users, 1 persons, and 1 events to the database.",
                result.getMessage());
    }

    @Test
    public void loadPass2() {
        LoadResult result = loadService.load(passRequest2);
        assertTrue(result.isSuccess());
        assertEquals("Successfully added 1 users, 1 persons, and 2 events to the database.",
                result.getMessage());
    }

    private LoadRequest generateRequest1() {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();

        users.add(new User("Bean123", "cocoPowPow33", "bean123@email.com",
                "Bob", "Henderson", "m", "Bob123A"));
        users.add(new User("Bacon456", "sizzle345", "bacon456@email.com",
                "Janis", "Jones", "f", "Janis789B"));

        persons.add(new Person("Bob123A", "Gale", "Bob",
                "Henderson", "m"));

        events.add(new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));

        return new LoadRequest(users, persons, events);
    }

    private LoadRequest generateRequest2() {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();

        users.add(new User("Bean123", "cocoPowPow33", "bean123@email.com",
                "Bob", "Henderson", "m", "Bob123A"));

        persons.add(new Person("Bob123A", "Gale", "Bob",
                "Henderson", "m"));

        events.add(new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016));
        events.add(new Event("Skiing_456A", "Gale", "Gale123A",
                47.2f, 120.3f, "USA", "Tucson",
                "Skiing_Downhill", 2023));

        return new LoadRequest(users, persons, events);
    }
}

