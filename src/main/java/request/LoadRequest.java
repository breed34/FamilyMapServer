package request;

import models.Event;
import models.Person;
import models.User;

import java.util.ArrayList;

/**
 * The request object for loading data directly into the database.
 */
public class LoadRequest {
    /**
     * The list of users to add to the database.
     */
    private ArrayList<User> users;

    /**
     * The list of persons to add to the database.
     */
    private ArrayList<Person> persons;

    /**
     * The list of events to add to the database.
     */
    private ArrayList<Event> events;

    /**
     * Creates a load request.
     *
     * @param users the list of users to add to the database.
     * @param persons the list of persons to add to the database.
     * @param events the list of events to add to the database.
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
