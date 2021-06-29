package world.deslauriers.service;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class UserServiceTest {

    @Inject
    private final UserService userService;

    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    // pre populated in test db
    public static String TEST_USER_1_FIRSTNAME = "Darth";
    public static String TEST_USER_1_LASTNAME = "Vader";
    public static String TEST_USER_1_EMAIL = "darth.vader@empire.com";
    public static String TEST_USER_1_ROLE_1 = "sith";
    public static String TEST_USER_1_ROLE_2 = "starpilot";

    public static String TEST_USER_2_FIRSTNAME = "Luke";
    public static String TEST_USER_2_LASTNAME = "Skywalker";
    public static String TEST_USER_2_EMAIL = "luke.skywalker@rebels.com";

    @Test
    void testUserServiceCrud(){

        var vader = userService.findByUserName(TEST_USER_1_EMAIL);
        assertTrue(vader.isPresent());

        var luke = userService.findByUserName(TEST_USER_2_EMAIL);
        assertTrue(luke.isEmpty());
    }
}
