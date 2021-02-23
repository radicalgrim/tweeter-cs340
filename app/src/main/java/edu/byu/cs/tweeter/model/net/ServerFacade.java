package edu.byu.cs.tweeter.model.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    private static ServerFacade instance;
    public static ServerFacade getInstance(){
        if(instance == null){
            instance = new ServerFacade();
        }
        return instance;
    }
    public static void setInstance(ServerFacade instance) {
        ServerFacade.instance = instance;
    }

    public ServerFacade(){}

    static HashMap<String, User> aliasToUser = new HashMap<>();
    static HashMap<String, String> aliasToPassword = new HashMap<>();
    static HashMap<User, AuthToken> userToAuthToken = new HashMap<>();
    static private User currentUser;
    static private AuthToken userAuthToken;
    // This is the hard coded followee data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final String password1 = "randomPass1";
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final String password2 = "randomPass2";
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final String password3 = "randomPass3";
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final String password4 = "randomPass4";
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final String password5 = "randomPass5";
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final String password6 = "randomPass6";
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final String password7 = "randomPass7";
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final String password8 = "randomPass8";
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final String password9 = "randomPass9";
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final String password10 = "randomPass10";
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);


    private final Status status1 = new Status("Hello World 1", "@user1", "google.com", "Feb. 2, 2021 1:00", user1);
    private final Status status2 = new Status("Hello World 2", "@user2", "google.com", "Feb. 2, 2021 2:00", user1);
    private final Status status3 = new Status("Hello World 3", "@user3", "google.com", "Feb. 2, 2021 3:00", user1);
    private final Status status4 = new Status("Hello World 4", "@user4", "google.com", "Feb. 2, 2021 4:00", user1);
    private final Status status5 = new Status("Hello World 5", "@user5", "google.com", "Feb. 2, 2021 5:00", user1);
    private final Status status6 = new Status("Hello World 6", "@user6", "google.com", "Feb. 2, 2021 6:00", user1);
    private final Status status7 = new Status("Hello World 7", "@user7", "google.com", "Feb. 2, 2021 7:00", user1);
    private final Status status8 = new Status("Hello World 8", "@user8", "google.com", "Feb. 2, 2021 8:00", user1);
    private final Status status9 = new Status("Hello World 9", "@user9", "google.com", "Feb. 2, 2021 9:00", user1);
    private final Status status10 = new Status("Hello World 10", "@user10", "google.com", "Feb. 2, 2021 10:00", user1);
    private final Status status11 = new Status("Hello World 11", "@user11", "google.com", "Feb. 2, 2021 11:00", user1);
    private final Status status12 = new Status("Hello World 12", "@user12", "google.com", "Feb. 2, 2021 12:00", user1);
    private final Status status13 = new Status("Hello World 13", "@user13", "google.com", "Feb. 2, 2021 13:00", user1);
    private final Status status14 = new Status("Hello World 14", "@user14", "google.com", "Feb. 2, 2021 14:00", user1);
    private final Status status15 = new Status("Hello World 15", "@user15", "google.com", "Feb. 2, 2021 15:00", user1);
    private final Status status16 = new Status("Hello World 16", "@user16", "google.com", "Feb. 2, 2021 16:00", user1);
    private final Status status17 = new Status("Hello World 17", "@user17", "google.com", "Feb. 2, 2021 17:00", user1);
    private final Status status18 = new Status("Hello World 18", "@user18", "google.com", "Feb. 2, 2021 18:00", user1);
    private final Status status19 = new Status("Hello World 19", "@user19", "google.com", "Feb. 2, 2021 19:00", user1);
    private final Status status20 = new Status("Hello World 20", "@user20", "google.com", "Feb. 2, 2021 20:00", user1);


    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        fillDummyArrays(getDummyFollowees(), getDummyPasswords());

        if (checkUsername(request.getUsername()) && checkPassword(request.getUsername(), request.getPassword())) {
            User userToReturn = aliasToUser.get(request.getUsername());
            currentUser = userToReturn;
            AuthToken authTokenToReturn = new AuthToken();
            userAuthToken = authTokenToReturn;
            userToAuthToken.put(userToReturn, authTokenToReturn);
            return new LoginResponse(userToReturn, authTokenToReturn);
//            User user = new User("Test", "User",
//                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//            return new LoginResponse(user, new AuthToken());
        } else {
            return new LoginResponse("couldn't find the user");
        }
    }

    public RegisterResponse register(RegisterRequest request) throws IOException {
        User userToReturn = new User(request.getFirstName(), request.getLastName(), request.getUsername(),
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"/*request.getImageUrl()*/);
        currentUser = userToReturn;
        AuthToken authTokenToReturn = new AuthToken();
        userAuthToken = authTokenToReturn;
        userToAuthToken.put(userToReturn, authTokenToReturn);
        return new RegisterResponse(userToReturn, authTokenToReturn);
    }

    public LogoutResponse logout(LogoutRequest request){
        if((userToAuthToken.get(request.getUser())) == null){
            return new LogoutResponse("failed to logout user");
        }
        else {
            userToAuthToken.remove(request.getUser());
            currentUser = null;
            userAuthToken = null;
            return new LogoutResponse(true);
        }
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {

        // Used in place of assert statements because Android does not support them
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);

            for (int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFolloweeAlias the alias of the last followee that was returned in the previous
     *                          request or null if there was no previous request.
     * @param allFollowees      the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if (lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if (lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }
        return followeesIndex;
    }

    /**
     * Returns the list of dummy followee data. This is written as a separate method to allow
     * mocking of the followees.
     *
     * @return the followees.
     */
    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }


    /**
     * Returns the statuses that the user specified in the request has posted. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose statuses are to be returned and any
     *                other information required to satisfy the request.
     * @return the story response.
     */
    public StoryResponse getStatuses(StoryRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }
            if(request.getUserAlias() == null) {
                throw new AssertionError();
            }
        }

        List<Status> dummyStatuses = getDummyStatuses();
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int statusIndex = getStatusesStartingIndex(request.getLastTimestamp(), dummyStatuses);

            for (int limitCount = 0; statusIndex < dummyStatuses.size() && limitCount < request.getLimit(); statusIndex++, limitCount++) {
                responseStatuses.add(dummyStatuses.get(statusIndex));
            }

            hasMorePages = statusIndex < dummyStatuses.size();
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    private int getStatusesStartingIndex(String lastStatusTimestamp, List<Status> statuses) {

        int statusIndex = 0;

        if (lastStatusTimestamp != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < statuses.size(); i++) {
                if (lastStatusTimestamp.equals(statuses.get(i).getTimestamp())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                    break;
                }
            }
        }
        return statusIndex;
    }

    /**
     * Returns the dummy story. This is written as a separate method to allow
     * mocking of the statuses.
     *
     * @return the story.
     */
    List<Status> getDummyStatuses() {
        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7,
                status8, status9, status10, status11, status12, status13, status14, status15,
                status16, status17, status18, status19, status20);
    }

  
    List<String> getDummyPasswords() {
        return Arrays.asList(password1, password2, password3, password4, password5, password6, password7, password8,
                password9, password10);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static AuthToken getUserAuthToken() {
        return userAuthToken;
    }

    void fillDummyArrays(List<User> users, List<String> passwords) {
        for (int i = 0; i < passwords.size(); i++) {
            aliasToUser.put(users.get(i).getAlias(), users.get(i));
            aliasToPassword.put(users.get(i).getAlias(), passwords.get(i));
        }
    }

    Boolean checkUsername(String username) {
        return aliasToUser.get(username) != null;
    }

    Boolean checkPassword(String alias, String password){
        return aliasToPassword.get(alias).equals(password);
    }

    public void invalidateAuthToken(){
        userAuthToken = null;
    }

}
