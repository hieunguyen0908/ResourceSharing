/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieunnm.dtos;

/**
 *
 * @author PC
 */
public class UserErrDTO {

    private String userIDError;
    private String passwordError;
    private String confirmPasswordError;
    private String userNameError;
    private String phoneError;
    private String addressError;
    private String emailError;

    public UserErrDTO() {
    }

    public UserErrDTO(String userIDError, String passwordError, String confirmPasswordError, String userNameError, String phoneError, String addressError, String emailError) {
        this.userIDError = userIDError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.userNameError = userNameError;
        this.phoneError = phoneError;
        this.addressError = addressError;
        this.emailError = emailError;
    }

    public String getUserIDError() {
        return userIDError;
    }

    public void setUserIDError(String userIDError) {
        this.userIDError = userIDError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getConfirmPasswordError() {
        return confirmPasswordError;
    }

    public void setConfirmPasswordError(String confirmPasswordError) {
        this.confirmPasswordError = confirmPasswordError;
    }

    public String getUserNameError() {
        return userNameError;
    }

    public void setUserNameError(String userNameError) {
        this.userNameError = userNameError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }
    
    
}
