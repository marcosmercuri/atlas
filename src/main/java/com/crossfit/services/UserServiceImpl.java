package com.crossfit.services;

import com.crossfit.exceptions.DuplicatedUsernameException;
import com.crossfit.exceptions.UserNotFoundException;
import com.crossfit.model.User;
import com.crossfit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicatedUsernameException(user.getUsername());
        }

        userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}
