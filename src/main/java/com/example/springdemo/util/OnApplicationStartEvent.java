package com.example.springdemo.util;

import com.example.springdemo.model.User;
import com.example.springdemo.model.UserType;
import com.example.springdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OnApplicationStartEvent implements ApplicationListener<ApplicationReadyEvent> {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        if (!userRepository.findByEmail("admin@mail.com").isPresent()) {
            userRepository.save(User.builder()
                    .email("admin@mail.com")
                    .password(passwordEncoder.encode("admin"))
                    .surname("admin")
                    .name("admin")
                    .userType(UserType.ADMIN)
                    .build());
        }
    }

}