package requests;

import models.Event;
import models.Person;
import models.User;

import java.util.List;

/**
 * The request object for loading data directly into the database.
 */
public class LoadRequest {
    /**
     * The list of users to add to the database.
     */
    private List<User> users;

    /**
     * The list of persons to add to the database.
     */
    private List<Person> persons;

    /**
     * The list of events to add to the database.
     */
    private List<Event> events;

    /**
     * Creates a load request.
     *
     * @param users the list of users to add to the database.
     * @param persons the list of persons to add to the database.
     * @param events the list of events to add to the database.
     */
    public LoadRequest(List<User> users, List<Person> persons, List<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return users != null && persons != null && events != null;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Event> getEvents() {
        return events;
    }
}
