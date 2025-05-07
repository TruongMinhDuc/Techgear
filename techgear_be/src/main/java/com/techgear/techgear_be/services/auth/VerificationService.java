package com.techgear.techgear_be.services.auth;

import com.techgear.techgear_be.dtos.authentication.ResetPasswordRequest;
import com.techgear.techgear_be.dtos.authentication.registration.RegistrationRequest;
import com.techgear.techgear_be.dtos.authentication.user.UserRequest;

public interface VerificationService {

    Long generateTokenVerify(UserRequest userRequest);

    void resendRegistrationToken(Long userId);

    void confirmRegistration(RegistrationRequest registration);

    void changeRegistrationEmail(Long userId, String emailUpdate);

    void forgetPassword(String email);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

}
