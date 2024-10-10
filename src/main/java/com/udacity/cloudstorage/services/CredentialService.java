package com.udacity.cloudstorage.services;

import java.util.List;
import java.util.Base64;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.domain.Credential;
import com.udacity.cloudstorage.mapper.CredentialMapper;

@Service
public class CredentialService {
    private final Logger logger = LoggerFactory.getLogger(CredentialService.class);

    @Autowired
    private EncryptionService encryptor;

    @Autowired
    private CredentialMapper credentials;

    public String decryptCredential(Credential credential) {
        var found = credentials.find(credential);
        return encryptor.decryptValue(found.getPassword(), found.getKey());
    }

    public List<Credential> allBy(String UID) {
        return credentials.allFrom(UID);
    }

    public void remove(Credential credential) {
        credentials.delete(credential);
    }

    public void add(Credential credential) {
        if (credential.getId() == null) {
            var key = generateKey();
            var encryptedPassword = encryptor.encryptValue(credential.getPassword(), key);

            credentials.insert(
                new Credential(
                    null,
                    key,
                    credential.getUrl(),
                    credential.getUsername(),
                    encryptedPassword,
                    credential.getUserId()
                )
            );

            return;
        }

        var key = credentials.find(credential).getKey();
        var rawPassword = credential.getPassword();
        credential.setPassword(encryptor.encryptValue(rawPassword, key));

        credentials.update(credential);
    }

    private String generateKey() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            return Base64.getEncoder().encodeToString(key);

        } catch (Exception exception) {
            logger.info("Error generating key: {}", exception.getMessage());
        }

        return null;
    }

}
