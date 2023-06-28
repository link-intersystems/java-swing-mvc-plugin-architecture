package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.services.login.LoginResponseModel;
import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.swing.action.ActionTrigger;
import com.link_intersystems.swing.action.concurrent.DirectTaskExecutor;
import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.swing.text.BadLocationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginActionTest {

    private LoginAction loginAction;

    @BeforeEach
    void setUp() {
        LoginService loginService = new LoginService();
        loginService.registerUser("name", "pass");
        loginAction = new LoginAction(loginService);
        loginAction.setTaskExecutor(new DirectTaskExecutor());
    }

    @Test
    void perform() throws BadLocationException {
        LoginModel loginModel = loginAction.getLoginModel();
        loginModel.getUsernameDocument().insertString(0, "name", null);
        loginModel.getPasswordDocument().insertString(0, "pass", null);

        TaskActionListener taskActionListener = mock(TaskActionListener.class);
        loginAction.setTaskActionListener(taskActionListener);

        ActionTrigger.performAction(this, loginAction);

        ArgumentCaptor<LoginResponseModel> responseModelCaptor = ArgumentCaptor.forClass(LoginResponseModel.class);
        verify(taskActionListener).done(responseModelCaptor.capture());
        LoginResponseModel responseModel = responseModelCaptor.getValue();

        assertEquals("name", responseModel.getUsername());
        assertTrue(responseModel.isLoginSuccessful());
    }

}