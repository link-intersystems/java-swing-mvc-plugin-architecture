package com.link_intersystems.mvc;

import com.link_intersystems.mvc.editor.FileController;
import com.link_intersystems.mvc.main.AppInitAction;
import com.link_intersystems.mvc.main.AppInitView;
import com.link_intersystems.mvc.main.ToolbarController;

public class FileEditorApp {

    public static void main(String[] args) {
        AppInitView appInitView = new AppInitView() {
            public <T> T getController(ControllerQualifier<T> controllerQualifier) {
                if (controllerQualifier.getType().equals(ToolbarController.class)) {
                    return (T) new ToolbarController();
                } else if (controllerQualifier.getType().equals(FileController.class)) {
                    return (T) new FileController();
                }
                return null;
            }
        };
        appInitView.setAppInitController(new AppInitAction());
        appInitView.open();
    }
}
