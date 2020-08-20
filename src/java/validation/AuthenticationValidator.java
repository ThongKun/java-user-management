/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package validation;

import java.io.Serializable;
import util.RegexConstants;

/**
 *
 * @author ThongLV
 */
public class AuthenticationValidator implements Serializable{
    public static boolean check(String email, String password) {
        return validateEmail(email) && validatePassword(password);
    }
    public static boolean validateEmail(String email) {
        return email.matches(RegexConstants.EMAIL_PATTERN);
    }

    public static boolean validatePassword(String password) {
        return password.matches(RegexConstants.PASSWORD_PATTERN);
    }

}
