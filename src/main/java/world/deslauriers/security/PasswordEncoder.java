package world.deslauriers.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface PasswordEncoder {
    String encode(@NotNull @NotBlank String clearPassword);

    Boolean matches(@NotNull @NotBlank String clearPassword,
                    @NotNull @NotBlank String encodedPassword);
}
