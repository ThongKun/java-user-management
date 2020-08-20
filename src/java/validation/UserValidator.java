/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import util.RegexConstants;

/**
 *
 * @author ThongLV
 */
public class UserValidator {

    public static boolean validateEmail(String email) {
        return email.matches(RegexConstants.EMAIL_PATTERN);
    }

    public static boolean validatePassword(String password) {
        return password.matches(RegexConstants.PASSWORD_PATTERN);
    }

    public static boolean validateName(String name) {
        return name.matches(RegexConstants.NAME_PATTERN);
    }

}
