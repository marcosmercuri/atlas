package com.crossfit.security;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.crossfit.controller.AbstractControllerTest;
import com.crossfit.model.User;
import com.crossfit.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SignupControllerTest extends AbstractControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_successful_user_save() throws Exception {
        User expectedUser = createUser("uniqueUser");
        mockMvc.perform(
              post("/signup")
              .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, buildTestProviderSignInAttempt(expectedUser))
        ).andExpect(status().isOk());

        Optional<User> maybeUser = userRepository.findByUsername(expectedUser.getUsername());
        assertTrue(maybeUser.isPresent());
        assertThat(maybeUser.get(), is(expectedUser));
    }

    @Test
    public void test_duplicated_user() throws Exception {
        User savedUser = createUser("duplicatedUser");
        userRepository.save(savedUser);

        mockMvc.perform(
              post("/signup")
                    .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, buildTestProviderSignInAttempt(savedUser))
        ).andExpect(status().isBadRequest());
    }

    private User createUser(String username) {
        return new User(username, "twitter", "123424", "myName", "myLastName", "www.google.com/image.jpg");
    }

    private TestProviderSignInAttempt buildTestProviderSignInAttempt(User expectedUser) {
        ConnectionData connectionData = new ConnectionData(expectedUser.getProviderId(),
              expectedUser.getProviderUserId(),
              expectedUser.getUsername(),
              "profileUrl",
              expectedUser.getImageUrl(),
              "accessToken",
              "secret",
              "refreshToken",
              1000L);

        UserProfile userProfile = new UserProfileBuilder()
              .setEmail("email@mail.com")
              .setUsername(expectedUser.getUsername())
              .setId(expectedUser.getProviderUserId())
              .setFirstName(expectedUser.getName())
              .setLastName(expectedUser.getLastName())
              .build();

        Connection connection = new TestConnection(connectionData, userProfile);

        return new TestProviderSignInAttempt(connection);
    }
}
