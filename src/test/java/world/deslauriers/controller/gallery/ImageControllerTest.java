package world.deslauriers.controller.gallery;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import world.deslauriers.model.gallery.Image;

import javax.inject.Inject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class ImageControllerTest {

    @Inject
    @Client("/")
    RxHttpClient client;

    // test data from test auth db + test image db
    private static final String VALID_USERNAME = "darth.vader@empire.com";
    private static final String VALID_PASSWORD = "Now-This-Is-Pod-Racing!!!";
    private static final String PIC_FILE_NAME = "522d748b-de61-11eb-b22e-dc41a9582465";
    private static final String IMAGE_CLIENT_URI = "http://localhost:8081/images";

    @Test
    void testImageClientAuthenticated(){

        var creds = new UsernamePasswordCredentials(VALID_USERNAME, VALID_PASSWORD);
        var request = HttpRequest.POST("/login", creds);

        var bearer = client.toBlocking().retrieve(request, BearerAccessRefreshToken.class);
        assertNotNull(bearer);
        assertEquals(VALID_USERNAME, bearer.getUsername());
        assertTrue(bearer.getRoles().contains("sith"));

        List<Image> images = client
                .toBlocking()
                .retrieve(HttpRequest.GET(IMAGE_CLIENT_URI + "/2021")
                    .header(
                            "Authorization", "Bearer " + bearer.getAccessToken()),
                        Argument.of(List.class, Image.class));
        assertNotNull(images);
        assertEquals(PIC_FILE_NAME, images.get(0).getFilename());
        assertEquals(2021, images.get(0).getDate().getYear());
        assertTrue(images.get(0).getPublished());
    }

    @Test
    void testNoAuth(){

        HttpClientResponseException thrown = assertThrows(
                HttpClientResponseException.class, () -> {
                    client.toBlocking().exchange(HttpRequest.GET(IMAGE_CLIENT_URI + "/2021"));
                }
        );
    }

    @Test
    void testBadBearerTokenSignature(){

        // incorrect signature
        var creds = new UsernamePasswordCredentials(VALID_USERNAME, VALID_PASSWORD);
        var request = HttpRequest.POST("/login", creds);
        var bearer = client.toBlocking().retrieve(request, BearerAccessRefreshToken.class);

        var badToken = bearer.getAccessToken().substring(0, bearer.getAccessToken().length() - 4);
        HttpClientResponseException thrown = assertThrows(
                HttpClientResponseException.class, () -> {
                    client
                    .toBlocking()
                    .retrieve(HttpRequest.GET(IMAGE_CLIENT_URI + "/2021")
                            .header("Authorization", "Bearer " + badToken), Image.class);
                }
        );
    }
}
