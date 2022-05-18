package com.example.PAP2022.services;

import com.example.PAP2022.enums.ApplicationUserRole;
import com.example.PAP2022.exceptions.EmailNotValidException;
import com.example.PAP2022.exceptions.EmailTakenException;
import com.example.PAP2022.exceptions.UserNotFoundException;
import com.example.PAP2022.models.ApplicationUser;
import com.example.PAP2022.models.EmailToken;
import com.example.PAP2022.payload.ApplicationUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final ApplicationUserDetailsService applicationUserService;
    private final EmailTokenService emailTokenService;
    private final EmailService emailService;
    private final ImageService imageService;

    public void register(ApplicationUserRequest request) throws
            EmailNotValidException,
            EmailTakenException, IOException {

        String token = applicationUserService.signUpUser(
                new ApplicationUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        imageService.convertToImage(request.getImage()),
                        ApplicationUserRole.USER
                )
        );

        String link = "http://localhost:3000/confirmation?token=" + token;
        String subject = "Confirm your email";
        emailService.send(
                request.getEmail(),
                subject,
                buildConfirmationEmail(request.getFirstName(), link));
    }

    @Transactional
    public void confirmToken(String token) throws EmailNotValidException {
        if (emailTokenService.getEmailToken(token).isPresent()) {
            EmailToken registrationToken = emailTokenService.getEmailToken(token).get();
            LocalDateTime expiringTime = registrationToken.getExpiringTime();

            if (registrationToken.getConfirmationTime() != null || expiringTime.isBefore(LocalDateTime.now())) {
                throw new EmailNotValidException("Email is not valid");

            } else {
                emailTokenService.setConfirmationTime(token);
            }
        } else {
            throw new EmailNotValidException("Email is not valid");
        }
    }

    public void sendResetPasswordEmail(String email) {
        if (applicationUserService.loadApplicationUserByEmail(email).isPresent()) {
            ApplicationUser applicationUser = applicationUserService.loadApplicationUserByEmail(email).get();
            String token = UUID.randomUUID().toString();

            EmailToken emailToken = new EmailToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    applicationUser
            );

            String link = "http://localhost:3000/resetPasswordEditor?token=" + token;
            String subject = "Reset your password";
            emailService.send(
                    email,
                    subject,
                    buildResetPasswordEmail(applicationUser.getFirstName(), link));

            emailTokenService.saveEmailToken(emailToken);
        }
    }

    public boolean confirmApplicationUser(String token) throws EmailNotValidException {
        confirmToken(token);
        applicationUserService.enableApplicationUser(emailTokenService.getEmailToken(token).get()
                .getApplicationUser().getEmail());
        return true;
    }

    public Long getApplicationUserByResetPasswordToken(String token) throws EmailNotValidException {
        confirmToken(token);
        return emailTokenService.getEmailToken(token).get().getApplicationUser().getId();
    }

    public void resetApplicationUserPassword(String password, Long id) throws UserNotFoundException {
        applicationUserService.resetPassword(password, id);
    }

    private String buildConfirmationEmail(String name, String link) {
        return "<div style=\"font-family:Cabin,Arial,sans-serif;font-size:16px;margin:0;color:#6E64F7\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#6E64F7;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#6E64F7\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#6E64F7\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Cabin,Arial,sans-serif;font-weight:700;color:#f9f9f9;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#bdcef3\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Cabin,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#3D3D3D\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#3D3D3D\">Thank you for registering. Please click on the below link to activate your account. </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #3D3D3D;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#3D3D3D\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote> <p style=\"color:#3D3D3D\">\n Link will expire in 15 minutes. </p> <p style=\"color:#3D3D3D\">See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    private String buildResetPasswordEmail(String name, String link) {
        return "<div style=\"font-family:Cabin,Arial,sans-serif;font-size:16px;margin:0;color:#6E64F7\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#6E64F7;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#6E64F7\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#6E64F7\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Cabin,Arial,sans-serif;font-weight:700;color:#f9f9f9;text-decoration:none;vertical-align:top;display:inline-block\">Reset your password</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#bdcef3\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Cabin,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#3D3D3D\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#3D3D3D\">To reset your password please click on the below link. </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #3D3D3D;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#3D3D3D\"> <a href=\"" + link + "\">Reset Password</a> </p></blockquote> <p style=\"color:#3D3D3D\">\n Link will expire in 15 minutes. </p> <p style=\"color:#3D3D3D\">See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
