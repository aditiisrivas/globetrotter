package com.example.globetrotter.repository;

import com.example.globetrotter.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<User, String>
{
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByInviteCode(String inviteCode);
}
