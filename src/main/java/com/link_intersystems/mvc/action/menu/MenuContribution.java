package com.link_intersystems.mvc.action.menu;

import com.link_intersystems.mvc.View;

import javax.swing.*;

public interface MenuContribution {
    String getMenuPath();

    Action getAction(View parentView);
}
