package org.mambey.emailservices.utils;

public class EmailUtils {
    public static String getEmailMessage(String name, String host, String token){
        return "Hello "+ name + ",\n\n Your new account has been created. Please click the link below to verifiy your account \n\n" +
                getVerificationUrl(host, token) + "\n\n The support team.";
    }

    private static String getVerificationUrl(String host, String token) {
        return host + "/api/user?token=" + token;
    }
}
