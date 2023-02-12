package requests;

/**
 * The request object for populating the database with
 * generated data for a specified user.
 */
public class FillRequest {
    /**
     * The username of the user for whom to generate data.
     */
    private String username;

    /**
     * The number of generations to generate.
     */
    private int generations;

    /**
     * Creates a fill request object without a specified
     * number of generations. The number of generations is
     * set to the default value of 4.
     *
     * @param username the username of the user for whom to generate data.
     */
    public FillRequest(String username) {
        this.username = username;
        this.generations = 4;
    }

    /**
     * Creates a fill request object with a specified number
     * of generations.
     *
     * @param username the username of the user for whom to generate data.
     * @param generations the number of generations to generate.
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return  username != null && !username.isBlank() && generations >= 0;
    }

    public String getUsername() {
        return username;
    }

    public int getGenerations() {
        return generations;
    }
}
