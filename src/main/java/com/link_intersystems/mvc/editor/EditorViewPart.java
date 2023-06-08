package com.link_intersystems.mvc.editor;

import javax.swing.*;
import java.awt.*;

public interface EditorViewPart {
    void setEditorComponent(Component component);

    void setCloseAction(Action closeAction);
}
