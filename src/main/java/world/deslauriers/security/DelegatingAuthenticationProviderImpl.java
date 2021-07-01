package world.deslauriers.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;
import world.deslauriers.Application;
import world.deslauriers.model.User;
import world.deslauriers.service.UserService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Singleton
public class DelegatingAuthenticationProviderImpl implements AuthenticationProvider {

    @Inject
    protected final UserService userService;

    @Inject
    protected final PasswordEncoder passwordEncoder;

    @Inject
    protected final Scheduler scheduler;

    public DelegatingAuthenticationProviderImpl(UserService userService,
                                                PasswordEncoder passwordEncoder,
                                                @Named(TaskExecutors.IO) ExecutorService executorService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.scheduler = Schedulers.from(executorService);
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authenticationRequest) {

         return Flowable.create(emitter -> {
            var user = fetchUser(authenticationRequest);
            var authenticationFailed = validate(user, authenticationRequest);
            if (authenticationFailed.isPresent()){
                emitter.onError(new AuthenticationException(authenticationFailed.get()));
            } else {
                emitter.onNext(createSuccessfulAuthenticationResponse(authenticationRequest, user.get()));
            }
            emitter.onComplete();
        }, BackpressureStrategy.ERROR); //.subscriberOn
    }


    protected Optional<User> fetchUser(AuthenticationRequest authenticationRequest){

        final String username = authenticationRequest.getIdentity().toString();
        return userService.findByUserName(username);
    }

    protected Optional<AuthenticationFailed> validate(Optional<User> user, AuthenticationRequest authenticationRequest){

        AuthenticationFailed authenticationFailed = null;

        if (user.isEmpty()) {
            authenticationFailed = new AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND);
        } else if (!user.get().getEnabled()) {
            authenticationFailed = new AuthenticationFailed(AuthenticationFailureReason.USER_DISABLED);
        } else if (user.get().getIsAccountExpired()){
            authenticationFailed = new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_EXPIRED);
        } else if (user.get().getIsAccountLocked()){
            authenticationFailed = new AuthenticationFailed(AuthenticationFailureReason.ACCOUNT_LOCKED);
        } else if (user.get().getIsPasswordExpired()){
            authenticationFailed = new AuthenticationFailed(AuthenticationFailureReason.PASSWORD_EXPIRED);
        } else if (!passwordEncoder.matches(authenticationRequest.getSecret().toString(), user.get().getPassword())){
            authenticationFailed = new AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
        }

        return Optional.ofNullable(authenticationFailed);
    }

    protected AuthenticationResponse createSuccessfulAuthenticationResponse(
            AuthenticationRequest authenticationRequest, User user) {

        List<String> roles = new ArrayList<>();
        user.getUserRoles().forEach(userRole -> {
            roles.add(userRole.getRole().getRole());
        });
        return new UserDetails(user.getUsername(), roles);
    }
}
