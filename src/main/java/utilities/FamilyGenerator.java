package utilities;

import com.google.gson.Gson;
import daos.EventDao;
import daos.PersonDao;
import exceptions.DataAccessException;
import models.Event;
import models.Person;
import utilities.models.Location;
import utilities.models.Locations;
import utilities.models.Names;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

/**
 * The utility object used for adding fake family history
 * data for a given user to the database.
 */
public class FamilyGenerator {
    /**
     * The object containing a list of male first names.
     */
    private final Names maleNames;

    /**
     * The object containing a list of female first names.
     */
    private final Names femaleNames;

    /**
     * The object containing a list of surnames.
     */
    private final Names surnames;

    /**
     * The object containing a list of locations.
     */
    private final Locations locations;

    /**
     * Creates an object used for adding fake family history
     * data for a given user to the database.
     *
     * @throws FileNotFoundException if the required json files can not be opened.
     */
    public FamilyGenerator() throws FileNotFoundException {
        Gson gson = new Gson();
        maleNames = gson.fromJson(new FileReader("json/mnames.json"), Names.class);
        femaleNames = gson.fromJson(new FileReader("json/fnames.json"), Names.class);
        surnames = gson.fromJson(new FileReader("json/snames.json"), Names.class);
        locations = gson.fromJson(new FileReader("json/locations.json"), Locations.class);
    }

    /**
     * Recursively adds fake family history data for a given user to the database.
     *
     * @param conn the database connection.
     * @param person the current person to add data for.
     * @param username the username associated with this fake data.
     * @param birthYear the birth year of the current person.
     * @param numberOfGenerations the number of generations to generate for the current person.
     * @throws DataAccessException if an error occurs in a database transaction.
     * @throws FileNotFoundException if the required json files can not be opened.
     */
    public static void generateGenerations(Connection conn, Person person, String username, int birthYear,
                                           int numberOfGenerations) throws DataAccessException, FileNotFoundException {
        Person father = null;
        Person mother = null;
        FamilyGenerator generator = new FamilyGenerator();

        if (numberOfGenerations > 0) {
            // Generate parent details
            father = generator.generatePerson(username, "m");
            mother = generator.generatePerson(username, "f");

            int fatherBirthYear = generator.generateParentBirthYear(birthYear);
            int motherBirthYear = generator.generateParentBirthYear(birthYear);
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            generator.addParentEvents(conn, father.getPersonID(),
                    mother.getPersonID(), username, fatherBirthYear, motherBirthYear);

            generateGenerations(conn, father, username, fatherBirthYear, numberOfGenerations - 1);
            generateGenerations(conn, mother, username, motherBirthYear, numberOfGenerations - 1);
        }

        // Add personal data for the individual
        generator.addBirthEvent(conn, person.getPersonID(), username, birthYear);
        if (father != null && mother != null) {
            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());
        }

        new PersonDao(conn).insert(person);
    }

    /**
     * Generates a fake birth year for a user 18-60 years before today's date.
     *
     * @return the fake birth year.
     */
    public static int generateUserBirthYear() throws FileNotFoundException {
        FamilyGenerator generator = new FamilyGenerator();
        return LocalDate.now().getYear() - generator.getRandomIntInRange(18, 60);
    }

    private int getRandomIntInRange(int min, int max) {
        return new Random().nextInt((max - min)) + min;
    }

    private Person generatePerson(String username, String gender) {
        String personID = UUID.randomUUID().toString();
        assert gender.equals("m") || gender.equals("f");

        String firstName;
        if (gender.equals("m")) {
            firstName = maleNames.getRandomName();
        }
        else {
            firstName = femaleNames.getRandomName();
        }

        return new Person(personID,
                username,
                firstName,
                surnames.getRandomName(),
                gender);
    }

    private void addParentEvents(Connection conn, String fatherID, String motherID, String username,
                                             int fatherBirthYear, int motherBirthYear) throws DataAccessException {
        int marriageYear = generateMarriageYear(fatherBirthYear, motherBirthYear);
        int fatherDeathYear = generateDeathYear(fatherBirthYear);
        int motherDeathYear = generateDeathYear(motherBirthYear);

        addMarriageEvent(conn, fatherID, motherID, username, marriageYear);
        addDeathEvent(conn, fatherID, username, fatherDeathYear);
        addDeathEvent(conn, motherID, username, motherDeathYear);
    }

    private void addBirthEvent(Connection conn, String personID, String username,
                               int birthYear) throws DataAccessException {
        Location location = locations.getRandomLocation();
        String eventID = UUID.randomUUID().toString();
        Event birthEvent = new Event(eventID,
                username,
                personID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "Birth",
                birthYear);

        new EventDao(conn).insert(birthEvent);
    }

    private void addMarriageEvent(Connection conn, String fatherID, String motherID, String username,
                                  int marriageYear) throws DataAccessException {
        Location location = locations.getRandomLocation();
        String fatherEventId = UUID.randomUUID().toString();
        String motherEventId = UUID.randomUUID().toString();

        Event fatherMarriage = new Event(fatherEventId,
                username,
                fatherID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "Marriage",
                marriageYear);
        Event motherMarriage = new Event(motherEventId,
                username,
                motherID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "Marriage",
                marriageYear);

        new EventDao(conn).insert(fatherMarriage);
        new EventDao(conn).insert(motherMarriage);
    }

    private void addDeathEvent(Connection conn, String personID, String username,
                               int deathYear) throws DataAccessException {
        Location location = locations.getRandomLocation();
        String eventID = UUID.randomUUID().toString();
        Event deathEvent = new Event(eventID,
                username,
                personID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "Death",
                deathYear);

        new EventDao(conn).insert(deathEvent);
    }

    private int generateParentBirthYear(int childBirthYear) {
        return childBirthYear - getRandomIntInRange(18, 30);
    }

    private int generateMarriageYear(int fatherBirthYear, int motherBirthYear) {
        // Identifies the youngest parent
        int youngestParentBirthYear = fatherBirthYear >= motherBirthYear ? fatherBirthYear : motherBirthYear;

        return youngestParentBirthYear + getRandomIntInRange(18, 25);
    }

    private int generateDeathYear(int birthYear) {
        return birthYear + getRandomIntInRange(60, 90);
    }
}
