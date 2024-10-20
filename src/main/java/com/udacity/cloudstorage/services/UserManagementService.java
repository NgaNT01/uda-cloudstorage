package com.udacity.cloudstorage.services;

import com.udacity.cloudstorage.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.mapper.UserMapper;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserManagementService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HashService hashingService;

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int registerUser(User user) {
        String salt = generateSalt();
        String hashedPassword = hashingService.getHashedValue(user.getPassword(), salt);

        User newUser = new User(
                null,
                user.getUsername(),
                salt,
                hashedPassword,
                user.getFirstName(),
                user.getLastName()
        );

        return userMapper.insert(newUser);
    }

    public User retrieveUser(String username) {
        return userMapper.getUser(username);
    }

    private String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
