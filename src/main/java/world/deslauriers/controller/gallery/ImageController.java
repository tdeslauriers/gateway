package world.deslauriers.controller.gallery;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import world.deslauriers.client.gallery.ImageFetcher;
import world.deslauriers.model.gallery.Image;

@Controller("/gallery/images/")
public class ImageController {

    private final ImageFetcher imageFetcher;

    public ImageController(ImageFetcher imageFetcher) {
        this.imageFetcher = imageFetcher;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Produces(MediaType.APPLICATION_JSON)
    @Get("/{album}")
    public Flowable<Image> getByAlbum(
            @Header("Authorization") String authorization,
            String album){

        return imageFetcher.getByAlbum(authorization, album);
    }
}
