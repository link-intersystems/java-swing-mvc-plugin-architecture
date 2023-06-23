package com.link_intersystems.fileeditor.login;

import com.link_intersystems.fileeditor.services.login.LoginService;
import com.link_intersystems.fileeditor.services.login.LoginServiceMock;
import com.link_intersystems.swing.view.RootViewSite;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;
import com.link_intersystems.util.context.Context;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class LoginViewTest {

    private LoginService loginService;
    private TestableLoginView loginView;
    private ViewSite viewSite;
    private ScheduledExecutorService scheduledExecutorService;

    @BeforeEach
    void setUp() {
        loginService = new LoginServiceMock();
        loginView = new TestableLoginView();

        viewSite = new RootViewSite() {
            @Override
            public ViewContent getViewContent() {
                return ViewContent.nullInstance();
            }

        };


        scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        ScheduledExecutorService executorServiceMock = Mockito.mock(ScheduledExecutorService.class);
        Context viewContext = viewSite.getViewContext();
        viewContext.put(ScheduledExecutorService.class, executorServiceMock);

        Mockito.doAnswer((Answer<Object>) invocation -> {
            ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule((Runnable) invocation.getArgument(0), 0, invocation.getArgument(2));
            scheduledFuture.get();
            return scheduledFuture;
        }).when(executorServiceMock).schedule(Mockito.any(Runnable.class), Mockito.anyLong(), Mockito.any(TimeUnit.class));

        viewContext.put(LoginService.class, loginService);
    }

    @AfterEach
    void tearDown(){
        scheduledExecutorService.shutdown();
    }

    @Test
    void login() {
        loginView.install(viewSite);

        loginView.setUsername("name");
        loginView.setPassword("name");

        loginView.performLogin();

        loginView.uninstall();
    }

    @Test
    void loginFailed() {
        loginView.install(viewSite);

        loginView.setUsername("name");
        loginView.setPassword("");

        loginView.performLogin();

        loginView.uninstall();
    }

}