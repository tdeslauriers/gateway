package world.deslauriers.security;

import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.http.*;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class AuthProviderTest {

    @Inject
    @Client("/")
    HttpClient client;

    private static String VALID_USERNAME = "darth.vader@empire.com";
    private static String VALID_PASSWORD = "NowThisIsPod-Racing!!!";
    private static String USERROLE_1 = "sith";
    private static String USERROLE_2 = "starpilot";

    @Test
    void testLoginSuccess(){

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
}
