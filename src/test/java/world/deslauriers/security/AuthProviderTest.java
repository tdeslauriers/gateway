package world.deslauriers.security;

import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.http.*;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class AuthProviderTest {

    @Inject
    @Client("/")
    RxHttpClient client;

    private static final String VALID_USERNAME = "darth.vader@empire.com";
    private static final String VALID_PASSWORD = "Now-This-Is-Pod-Racing!!!";
    private static final String USERROLE_1 = "sith";
    private static final String USERROLE_2 = "starpilot";

    @Test
    void testLoginSuccess() throws ParseException {

        var request = HttpRequest
                .create(HttpMethod.POST, "/login")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .body(new UsernamePasswordCredentials(VALID_USERNAME, VALID_PASSWORD));

        HttpResponse<BearerAccessRefreshToken> response = client
                .toBlocking()
                .exchange(request, BearerAccessRefreshToken.class);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertNotNull(response.body().getAccessToken());
        assertTrue(JWTParser.parse(response.body().getAccessToken()) instanceof SignedJWT);

        assertEquals(VALID_USERNAME, response.body().getUsername());
        var role1 = response.body().getRoles()
                .stream()
                .filter(s -> s.equals(USERROLE_1))
                .findFirst();
        assertTrue(role1.isPresent());
        assertEquals(role1.get(), USERROLE_1);

        var role2 = response.body().getRoles()
                .stream()
                .filter(s -> s.equals(USERROLE_2))
                .findFirst();
        assertTrue(role2.isPresent());
        assertEquals(role2.get(), USERROLE_2);
    }

    @Test
    void testLoginFailure(){

        //bad creds-
        var request = HttpRequest
                .create(HttpMethod.POST, "/login")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .body(new UsernamePasswordCredentials(VALID_USERNAME,"No, I am your father."));

        assertThrows(HttpClientResponseException.class, () -> {
            client.
                    toBlocking()
                    .exchange(request, BearerAccessRefreshToken.class);
        });
        // auth failure status + message
        try {
           var response = client.toBlocking().exchange(request, BearerAccessRefreshToken.class);
        } catch (HttpClientResponseException exception) {
            assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
            assertEquals("Credentials Do Not Match", exception.getMessage());
        }
    }
}
