package com.link_intersystems.fileeditor.services.login;

public class LoginServiceMock extends LoginService{

    @Override
    public LoginResponseModel login(String username, String password) {
        LoginResponseModel loginResponseModel = new LoginResponseModel(username);
        loginResponseModel.setLoginSuccessful(username.equals(password));
        return loginResponseModel;
    }
}