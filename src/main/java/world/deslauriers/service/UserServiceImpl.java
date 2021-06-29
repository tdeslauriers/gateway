package world.deslauriers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import world.deslauriers.model.User;
import world.deslauriers.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class UserServiceImpl implements UserService {

    static private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Inject
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUserName(@NotBlank @NotNull String username){

        return userRepository.findByUsername(username);
    }
}
