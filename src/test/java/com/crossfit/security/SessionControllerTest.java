package com.crossfit.security;

import static com.crossfit.security.SessionControllerTest.USERNAME_IN_SESSION;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.crossfit.controller.AbstractControllerTest;
import com.crossfit.model.User;
import com.crossfit.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@WithMockUser(USERNAME_IN_SESSION)
public class SessionControllerTest extends AbstractControllerTest {
    static final String USERNAME_IN_SESSION = "username";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_successful_user_retrieval() throws Exception {
        User savedUser = createUser(USERNAME_IN_SESSION);
        userRepository.save(savedUser);

        mockMvc.perform(
              get("/api/session")
        ).andExpect(status().isOk())
          .andExpect(jsonPath("$.username", is(savedUser.getUsername())))
          .andExpect(jsonPath("$.name", is(savedUser.getName())));
    }

    private User createUser(String username) {
        return new User(username, "twitter", "123424", "myName", "myLastName", "www.google.com/image.jpg");
    }

    @Test
    @WithMockUser("non-existent-user")
    public void test_no_saved_user() throws Exception {
        mockMvc.perform(
              get("/api/session")
        ).andExpect(status().isBadRequest());
    }
}
