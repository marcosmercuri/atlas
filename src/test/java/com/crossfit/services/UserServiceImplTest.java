package com.crossfit.services;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.crossfit.exceptions.DuplicatedUsernameException;
import com.crossfit.exceptions.UserNotFoundException;
import com.crossfit.model.User;
import com.crossfit.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;

public class UserServiceImplTest {
    private UserServiceImpl service;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        service = new UserServiceImpl(userRepository);
    }

    @Test
    public void given_a_user_when_save_is_called_then_user_is_saved_in_repository() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        User userToBeSaved = createUser(Optional.empty());

        service.saveUser(userToBeSaved);

        verify(userRepository).save(userToBeSaved);
    }

    @Test(expected = DuplicatedUsernameException.class)
    public void given_a_user_with_duplicated_id_when_save_is_called_then_an_exception_is_called() {
        String duplicatedUsername = "duplicated-username";
        when(userRepository.findByUsername(duplicatedUsername)).thenReturn(Optional.of(mock(User.class)));

        User user = createUser(Optional.of(duplicatedUsername));

        service.saveUser(user);
    }

    private User createUser(Optional<String> username) {
        return new User(Optional.of("id"), username.orElse("username"), "providerId", "providerUserId", "name", "last name", "www.example.com");
    }

    @Test
    public void given_a_saved_user_when_get_is_called_then_the_user_is_returned() {
        String username = "user name";
        User expectedUser = createUser(Optional.of(username));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        User user = service.getUser(username);

        assertThat(user, is(expectedUser));
    }

    @Test(expected = UserNotFoundException.class)
    public void given_no_user_saved_when_get_is_called_then_an_exception_is_thrown() {
        String username = "user name";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        service.getUser(username);
    }
}
