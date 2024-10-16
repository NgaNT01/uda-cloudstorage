package com.udacity.cloudstorage.services;

import com.udacity.cloudstorage.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.mapper.UserMapper;

import java.util.Base64;
import java.security.SecureRandom;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HashService hashService;

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        String encodedSalt = createSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        return userMapper.insert(
            new User(
                null,
                user.getUsername(),
                encodedSalt,
                hashedPassword,
                user.getFirstName(),
                user.getLastName()
            )
        );
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    private String createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

}
