package com.link_intersystems.fileeditor.services.login;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LoginService {

    private static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(username, user.username) && Objects.equals(password, user.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, password);
        }
    }

    private Set<User> registeredUsers = new HashSet<>();

    public LoginResponseModel login(String username, String password) {
        User user = new User(username, password);

        LoginResponseModel loginResponseModel = new LoginResponseModel(username);
        loginResponseModel.setLoginSuccessful(registeredUsers.contains(user));
        return loginResponseModel;
    }

    public void registerUser(String username, String password) {
        User user = new User(username, password);
        registeredUsers.add(user);
    }
}
