package world.deslauriers.client.gallery;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import world.deslauriers.model.gallery.Image;

@Client(id = "gallery")
public interface ImageClient extends ImageFetcher {

    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    @Get("/images/{album}")
    Flowable<Image> getByAlbum(
            @Header("Authorization") String authorization,
            String album);
}
