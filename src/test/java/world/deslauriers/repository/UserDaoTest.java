package world.deslauriers.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class UserDaoTest {

    @Inject
    private final UserRepository userRepository;


    public UserDaoTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // pre populated in test db
    public static final String TEST_USER_1_FIRSTNAME = "Darth";
    public static final String TEST_USER_1_LASTNAME = "Vader";
    public static final String TEST_USER_1_EMAIL = "darth.vader@empire.com";
    public static final String TEST_USER_1_ROLE_1 = "sith";
    public static final String TEST_USER_1_ROLE_2 = "starpilot";

    public static final String TEST_USER_2_FIRSTNAME = "Luke";
    public static final String TEST_USER_2_LASTNAME = "Skywalker";
    public static final String TEST_USER_2_EMAIL = "luke.skywalker@rebels.com";

    @Test
    void testUserCRUD(){

        var vader = userRepository.findByUsername(TEST_USER_1_EMAIL);
        assertTrue(vader.isPresent());
        assertEquals(TEST_USER_1_FIRSTNAME, vader.get().getFirstname());
        assertEquals(TEST_USER_1_LASTNAME, vader.get().getLastname());
        assertEquals(2, vader.get().getUserRoles().size());
        vader.get().getUserRoles().forEach(userRole -> System.out.println(userRole.getRole().getRole()));

    }
}
