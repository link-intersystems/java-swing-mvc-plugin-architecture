package com.link_intersystems.fileeditor.services.login;

public class LoginService {

    public LoginResponseModel login(String username, String password) {
        LoginResponseModel loginResponseModel = new LoginResponseModel(username);

        loginResponseModel.setLoginSuccessful("rene".equals(username) && "link".equals(password));

        return loginResponseModel;
    }
}
