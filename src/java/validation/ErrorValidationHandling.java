/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.io.Serializable;

/**
 *
 * @author ThongLV
 */
public class ErrorValidationHandling implements Serializable {

    private String emailError;
    private String passwordError;
    private String phoneError;
    private String nameError;
    private String addressError;
    private String duplicateEmailError;

    private String questionError;
    private String choiceAError;
    private String choiceBError;
    private String choiceCError;
    private String choiceDError;
    private String answerChoiceError;
    private String answerContentError;
    private String subjectError;

    public ErrorValidationHandling() {
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getDuplicateEmailError() {
        return duplicateEmailError;
    }

    public void setDuplicateEmailError(String duplicateEmailError) {
        this.duplicateEmailError = duplicateEmailError;
    }

    public String getQuestionError() {
        return questionError;
    }

    public void setQuestionError(String questionError) {
        this.questionError = questionError;
    }

    public String getChoiceAError() {
        return choiceAError;
    }

    public void setChoiceAError(String choiceAError) {
        this.choiceAError = choiceAError;
    }

    public String getChoiceBError() {
        return choiceBError;
    }

    public void setChoiceBError(String choiceBError) {
        this.choiceBError = choiceBError;
    }

    public String getChoiceCError() {
        return choiceCError;
    }

    public void setChoiceCError(String choiceCError) {
        this.choiceCError = choiceCError;
    }

    public String getChoiceDError() {
        return choiceDError;
    }

    public void setChoiceDError(String choiceDError) {
        this.choiceDError = choiceDError;
    }

    public String getAnswerChoiceError() {
        return answerChoiceError;
    }

    public void setAnswerChoiceError(String answerChoiceError) {
        this.answerChoiceError = answerChoiceError;
    }

    public String getAnswerContentError() {
        return answerContentError;
    }

    public void setAnswerContentError(String answerContentError) {
        this.answerContentError = answerContentError;
    }

    public String getSubjectError() {
        return subjectError;
    }

    public void setSubjectError(String subjectError) {
        this.subjectError = subjectError;
    }

}
