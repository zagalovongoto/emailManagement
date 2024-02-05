package org.mambey.emailservices.service;

import org.mambey.emailservices.domain.User;

public interface UserService {
    User saveUser(User user);
    Boolean verifyToken(String token);
}
