package edu.byu.cs.tweeter.model.net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    // This is the hard coded followee data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
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

    private final Status status1 = new Status("Hello World 1", "user1", MALE_IMAGE_URL);
    private final Status status2 = new Status("Hello World 2", "user2", MALE_IMAGE_URL);
    private final Status status3 = new Status("Hello World 3", "user3", MALE_IMAGE_URL);
    private final Status status4 = new Status("Hello World 4", "user4", MALE_IMAGE_URL);
    private final Status status5 = new Status("Hello World 5", "user5", MALE_IMAGE_URL);
    private final Status status6 = new Status("Hello World 6", "user6", MALE_IMAGE_URL);
    private final Status status7 = new Status("Hello World 7", "user7", MALE_IMAGE_URL);
    private final Status status8 = new Status("Hello World 8", "user8", MALE_IMAGE_URL);
    private final Status status9 = new Status("Hello World 9", "user9", MALE_IMAGE_URL);
    private final Status status10 = new Status("Hello World 10", "user10", MALE_IMAGE_URL);
    private final Status status11 = new Status("Hello World 11", "user11", MALE_IMAGE_URL);
    private final Status status12 = new Status("Hello World 12", "user12", MALE_IMAGE_URL);
    private final Status status13 = new Status("Hello World 13", "user13", MALE_IMAGE_URL);
    private final Status status14 = new Status("Hello World 14", "user14", MALE_IMAGE_URL);
    private final Status status15 = new Status("Hello World 15", "user15", MALE_IMAGE_URL);
    private final Status status16 = new Status("Hello World 16", "user16", MALE_IMAGE_URL);
    private final Status status17 = new Status("Hello World 17", "user17", MALE_IMAGE_URL);
    private final Status status18 = new Status("Hello World 18", "user18", MALE_IMAGE_URL);
    private final Status status19 = new Status("Hello World 19", "user19", MALE_IMAGE_URL);
    private final Status status20 = new Status("Hello World 20", "user20", MALE_IMAGE_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken());
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
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowerAlias() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFolloweeAlias(), allFollowees);

            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
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
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(String lastFolloweeAlias, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFolloweeAlias != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFolloweeAlias.equals(allFollowees.get(i).getAlias())) {
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
    public StoryResponse getStory(StoryRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getLastTimestamp() == null) {
                throw new AssertionError();
            }
        }

        TreeMap<String, Status> dummyStatuses = getDummyStory().getStatusMap();
        Story responseStory = new Story(getDummyStory().getUserAlias());

        boolean hasMorePages = true;

        if(request.getLimit() > 0) {

            String lastTimestamp = request.getLastTimestamp();

            for(int i = 0; i < request.getLimit(); i++) {
                responseStory.addStatus(lastTimestamp, dummyStatuses.get(lastTimestamp));
                if (dummyStatuses.higherKey(lastTimestamp) == null) {
                    hasMorePages = false;
                    break;
                }
                else {
                    lastTimestamp = dummyStatuses.higherKey(lastTimestamp);
                }
            }
        }

        return new StoryResponse(responseStory, hasMorePages);
    }

    /**
     * Returns the dummy story. This is written as a separate method to allow
     * mocking of the statuses.
     *
     * @return the story.
     */
    Story getDummyStory() {

        Story story = new Story("dummy");
        List<Status> statuses = Arrays.asList(status1, status2, status3, status4, status5, status6, status7,
                status8, status9, status10, status11, status12, status13, status14, status15,
                status16, status17, status18, status19, status20);

        for (int i = 0; i < statuses.size(); i++) {
            story.addStatus(Integer.toString(i), statuses.get(i));
        }

        return story;
    }
}
