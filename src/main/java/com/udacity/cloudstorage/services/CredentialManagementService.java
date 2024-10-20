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
public class CredentialManagementService {
    private final Logger log = LoggerFactory.getLogger(CredentialManagementService.class);

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private CredentialMapper credentialMapper;

    public String retrieveDecryptedPassword(Credential credential) {
        var existingCredential = credentialMapper.getCredential(credential);
        return encryptionService.decryptValue(existingCredential.getPassword(), existingCredential.getKey());
    }

    public List<Credential> getAllCredentialsByUserId(String userId) {
        return credentialMapper.findAllByUserId(userId);
    }

    public void deleteCredential(Credential credential) {
        credentialMapper.removeCredential(credential);
    }

    public void saveCredential(Credential credential) {
        if (credential.getId() == null) {
            var newKey = createEncryptionKey();
            var encryptedPassword = encryptionService.encryptValue(credential.getPassword(), newKey);

            credentialMapper.addCredential(
                    new Credential(
                            null,
                            newKey,
                            credential.getUrl(),
                            credential.getUsername(),
                            encryptedPassword,
                            credential.getUserId()
                    )
            );
            return;
        }

        var existingKey = credentialMapper.getCredential(credential).getKey();
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), existingKey));

        credentialMapper.updateCredential(credential);
    }

    private String createEncryptionKey() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] keyBytes = new byte[16];
            secureRandom.nextBytes(keyBytes);
            return Base64.getEncoder().encodeToString(keyBytes);
        } catch (Exception e) {
            log.info("Key generation failed: {}", e.getMessage());
        }

        return null;
    }
}
