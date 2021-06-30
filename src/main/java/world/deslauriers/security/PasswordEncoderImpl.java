package world.deslauriers.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Singleton
public class PasswordEncoderImpl implements PasswordEncoder {

    org.springframework.security.crypto.password.PasswordEncoder delegate = new BCryptPasswordEncoder();

    @Override
    public String encode(@NotNull @NotBlank String clearPassword){

        return delegate.encode(clearPassword);
    }

    @Override
    public Boolean matches(@NotNull @NotBlank String clearPassword,
                           @NotNull @NotBlank String encodedPassword){

        return delegate.matches(clearPassword, encodedPassword);
    }

}
