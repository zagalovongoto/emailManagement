package org.mambey.emailservices.service.impl;

import lombok.RequiredArgsConstructor;
import org.mambey.emailservices.domain.Confirmation;
import org.mambey.emailservices.domain.User;
import org.mambey.emailservices.repository.ConfirmationRepository;
import org.mambey.emailservices.repository.UserRepository;
import org.mambey.emailservices.service.EmailService;
import org.mambey.emailservices.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;
    @Override
    public User saveUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw  new RuntimeException("User already exists");
        }
        user.setEnabled(false);
        User savedUser = userRepository.save(user);
        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);
        /*TODO send email to user with token*/
        //emailService.sendSimpleMailMessage(user.getName(), user.getEmail(),confirmation.getToken());
        //emailService.sendMimeMessageWithAttachments(user.getName(), user.getEmail(),confirmation.getToken());
        emailService.sendMimeMessageWithEmbeddedImages(user.getName(), user.getEmail(),confirmation.getToken());

        return  savedUser;
    }

    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        if(confirmation == null){
            return Boolean.FALSE;
        }
        User user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        confirmationRepository.delete(confirmation);
        return Boolean.TRUE;
    }
}
