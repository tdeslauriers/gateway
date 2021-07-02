package world.deslauriers.security;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import world.deslauriers.service.UserService;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class PasswordEncoderTest {

    @Inject
    private final PasswordEncoder passwordEncoder;

    @Inject
    private final UserService userService;

    public PasswordEncoderTest(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public static final String TEST_PASSWORD_1 = "admin"; // lol
    public static final String TEST_PASSWORD_2 = "secure"; // irony
    public static final String TEST_PASSWORD_3 = "8ZmWG},s4[2@u<CLwkKF";  // ok

    @Test
    void testPwEncoderMethods(){

        var admin = passwordEncoder.encode(TEST_PASSWORD_1);
        var theyMatch = passwordEncoder.matches(TEST_PASSWORD_1, admin);
        assertTrue(theyMatch);

        theyMatch = passwordEncoder.matches(TEST_PASSWORD_2, passwordEncoder.encode(TEST_PASSWORD_2));
        assertTrue(theyMatch);

        assertTrue(passwordEncoder.matches(TEST_PASSWORD_3, passwordEncoder.encode(TEST_PASSWORD_3)));
    }
}
