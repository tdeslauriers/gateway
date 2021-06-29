package world.deslauriers.service;

import world.deslauriers.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(@NotBlank @NotNull String username);
}
