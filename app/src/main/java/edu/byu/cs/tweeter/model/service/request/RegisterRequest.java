package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final String imageUrl;

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public RegisterRequest(String firstName, String lastName, String username, String password, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user to be logged in by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return password;
    }
    public String getLastName() {
        return password;
    }
    public String getImageUrl() { return  imageUrl; }

}
