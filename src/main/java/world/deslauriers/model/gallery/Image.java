package world.deslauriers.model.gallery;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class Image implements Serializable {

    @Serial
    private static final long serialVersionUID = -6736018091894166705L;

    private Long id;
    private String filename;
    private String title;
    private String description;
    private LocalDate date;
    private Boolean published;

}
