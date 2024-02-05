package org.mambey.emailservices.repository;

import org.mambey.emailservices.domain.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation findByToken(String token);
    Boolean existsByEmail(String email);
}
