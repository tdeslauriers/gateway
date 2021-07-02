package world.deslauriers.security;

import io.micronaut.http.*;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class AuthProviderTest {

    @Inject
    @Client("/")
    HttpClient client;

    private static String VALID_USERNAME = "darth.vader@empire.com";
    private static String VALID_PASSWORD = "NowThisIsPod-Racing!!!";

    @Test
    void testLoginSuccess(){

        var request = HttpRequest
                .create(HttpMethod.POST, "/login")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .body(new UsernamePasswordCredentials(VALID_USERNAME, VALID_PASSWORD));

        HttpResponse<AccessRefreshToken> response = client
                .toBlocking()
                .exchange(request, AccessRefreshToken.class);

        assertEquals(HttpStatus.OK, response.getStatus());
    }
}
