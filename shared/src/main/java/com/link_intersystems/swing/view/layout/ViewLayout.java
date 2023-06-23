package com.link_intersystems.swing.view.layout;

import com.link_intersystems.swing.view.View;

public interface ViewLayout {

    void install(String viewSiteName, View view);

    void remove(String viewSiteName);
}
