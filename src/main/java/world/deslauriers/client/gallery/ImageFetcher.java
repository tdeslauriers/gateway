package world.deslauriers.client.gallery;

import io.micronaut.http.annotation.Header;
import io.reactivex.Flowable;
import world.deslauriers.model.gallery.Image;

public interface ImageFetcher {

    Flowable<Image> getByAlbum(
            @Header("Authorization") String authorization,
            String album);
}
