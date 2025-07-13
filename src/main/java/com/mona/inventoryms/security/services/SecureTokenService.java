package com.mona.inventoryms.security.services;

import com.mona.inventoryms.security.models.SecureToken;

public interface SecureTokenService {

    SecureToken createSecureToken();

    void saveSecureToken(SecureToken secureToken);

    SecureToken findByToken(String token);

    void removeToken(SecureToken token);

}
