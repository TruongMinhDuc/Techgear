package com.techgear.techgear_be.services.auth;

import com.techgear.techgear_be.constants.ApplicationConst;
import com.techgear.techgear_be.dtos.authentication.ResetPasswordRequest;
import com.techgear.techgear_be.dtos.authentication.registration.RegistrationRequest;
import com.techgear.techgear_be.dtos.authentication.user.UserRequest;
import com.techgear.techgear_be.models.authentication.Role;
import com.techgear.techgear_be.models.authentication.User;
import com.techgear.techgear_be.models.authentication.Verification;
import com.techgear.techgear_be.models.authentication.VerificationType;
import com.techgear.techgear_be.models.customer.Customer;
import com.techgear.techgear_be.models.customer.CustomerGroup;
import com.techgear.techgear_be.models.customer.CustomerResource;
import com.techgear.techgear_be.models.customer.CustomerStatus;
import com.techgear.techgear_be.exceptions.ExpiredTokenException;
import com.techgear.techgear_be.exceptions.VerificationException;
import com.techgear.techgear_be.mappers.authentication.UserMapper;
import com.techgear.techgear_be.repositories.authentication.UserRepository;
import com.techgear.techgear_be.repositories.authentication.VerificationRepository;
import com.techgear.techgear_be.repositories.customer.CustomerRepository;
import com.techgear.techgear_be.services.email.EmailSenderService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = VerificationException.class, dontRollbackOn = ExpiredTokenException.class)
public class VerificationServiceImpl implements VerificationService {

    private UserRepository userRepository;
    private VerificationRepository verificationRepository;
    private UserMapper userMapper;
    private EmailSenderService emailSenderService;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Long generateTokenVerify(UserRequest userRequest) {
        if (userRepository.existsUserByUsername(userRequest.getUsername())) {
            throw new VerificationException("Username is existing");
        }

        if (userRepository.existsUserByEmail(userRequest.getEmail())) {
            throw new VerificationException("Email is existing");
        }

        User user = userMapper.requestToEntity(userRequest);
        user.setStatus(2);
        user.setRoles(Set.of((Role) new Role().setId(3L)));

        userRepository.save(user);

        Verification verification = new Verification();
        String token = generateVerificationToken();

        verification.setUser(user);
        verification.setToken(token);
        verification.setExpiredAt(Instant.now().plus(5, ChronoUnit.MINUTES));
        verification.setType(VerificationType.REGISTRATION);

        verificationRepository.save(verification);

        Map<String, Object> attributes = Map.of(
                "token", token,
                "link", MessageFormat.format("{0}/signup?userId={1}", ApplicationConst.FRONTEND_HOST, user.getId()));
        emailSenderService.sendVerificationToken(user.getEmail(), attributes);

        return user.getId();
    }

    @Override
    public void resendRegistrationToken(Long userId) {
        Optional<Verification> verifyOpt = verificationRepository.findByUserId(userId);

        if (verifyOpt.isPresent()) {
            Verification verification = verifyOpt.get();
            String token = generateVerificationToken();

            verification.setToken(token);
            verification.setExpiredAt(Instant.now().plus(5, ChronoUnit.MINUTES));

            verificationRepository.save(verification);

            Map<String, Object> attributes = Map.of(
                    "token", token,
                    "link", MessageFormat.format("{0}/signup?userId={1}", ApplicationConst.FRONTEND_HOST, userId));
            emailSenderService.sendVerificationToken(verification.getUser().getEmail(), attributes);
        } else {
            throw new VerificationException("User ID is invalid. Please try again!");
        }
    }

    @Override
    public void confirmRegistration(RegistrationRequest registration) {
        Optional<Verification> verifyOpt = verificationRepository.findByUserId(registration.getUserId());

        if (verifyOpt.isPresent()) {
            Verification verification = verifyOpt.get();

            boolean validVerification = verification.getToken().equals(registration.getToken())
                    && verification.getExpiredAt().isAfter(Instant.now())
                    && verification.getType().equals(VerificationType.REGISTRATION);

            if (validVerification) {
                User user = verification.getUser();
                user.setStatus(1);
                userRepository.save(user);
                verificationRepository.delete(verification);

                Customer customer = new Customer();
                customer.setUser(user);
                customer.setCustomerGroup((CustomerGroup) new CustomerGroup().setId(1L));
                customer.setCustomerStatus((CustomerStatus) new CustomerStatus().setId(1L));
                customer.setCustomerResource((CustomerResource) new CustomerResource().setId(1L));
                customerRepository.save(customer);
            }

            boolean tokenIsExpired = verification.getToken().equals(registration.getToken())
                    && !verification.getExpiredAt().isAfter(Instant.now())
                    && verification.getType().equals(VerificationType.REGISTRATION);

            if (tokenIsExpired) {
                String token = generateVerificationToken();

                verification.setToken(token);
                verification.setExpiredAt(Instant.now().plus(5, ChronoUnit.MINUTES));

                verificationRepository.save(verification);

                Map<String, Object> attributes = Map.of(
                        "token", token,
                        "link", MessageFormat.format("{0}/signup?userId={1}", ApplicationConst.FRONTEND_HOST, registration.getUserId()));
                emailSenderService.sendVerificationToken(verification.getUser().getEmail(), attributes);

                throw new ExpiredTokenException("Token is expired, please check your email to get new token!");
            }

            if (!verification.getToken().equals(registration.getToken())) {
                throw new VerificationException("Invalid token");
            }
        } else {
            throw new VerificationException("User does not exist");
        }
    }

    @Override
    public void changeRegistrationEmail(Long userId, String emailUpdate) {
        Optional<Verification> verifyOpt = verificationRepository.findByUserId(userId);

        if (verifyOpt.isPresent()) {
            Verification verification = verifyOpt.get();

            User user = verification.getUser();
            user.setEmail(emailUpdate);
            userRepository.save(user);

            String token = generateVerificationToken();
            verification.setToken(token);
            verification.setExpiredAt(Instant.now().plus(5, ChronoUnit.MINUTES));
            verificationRepository.save(verification);

            Map<String, Object> attributes = Map.of(
                    "token", token,
                    "link", MessageFormat.format("{0}/signup?userId={1}", ApplicationConst.FRONTEND_HOST, userId));
            emailSenderService.sendVerificationToken(verification.getUser().getEmail(), attributes);
        } else {
            throw new VerificationException("User does not exist");
        }
    }

    @Override
    public void forgetPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Email doesn't exist"));

        if (user.getStatus() == 1) {
            String token = RandomString.make(10);
            user.setResetPasswordToken(token);
            userRepository.save(user);

            String link = MessageFormat.format("{0}/change-password?token={1}&email={2}", ApplicationConst.FRONTEND_HOST, token, email);
            emailSenderService.sendForgetPasswordToken(user.getEmail(), Map.of("link", link));
        } else {
            throw new VerificationException("Account is not activated");
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository
                .findByEmailAndResetPasswordToken(resetPasswordRequest.getEmail(), resetPasswordRequest.getToken())
                .orElseThrow(() -> new RuntimeException("Email and/or token are invalid"));
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userRepository.save(user);
    }

    private String generateVerificationToken() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

}
