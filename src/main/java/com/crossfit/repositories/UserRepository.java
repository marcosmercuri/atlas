package com.crossfit.repositories;

import java.util.Optional;

import com.crossfit.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
}
