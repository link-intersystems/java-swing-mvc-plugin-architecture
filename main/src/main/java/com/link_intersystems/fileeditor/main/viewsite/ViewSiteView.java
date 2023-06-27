package com.link_intersystems.fileeditor.main.viewsite;

import com.link_intersystems.swing.view.View;
import com.link_intersystems.swing.view.ViewContent;
import com.link_intersystems.swing.view.ViewSite;

import javax.swing.*;
import java.awt.*;

import static java.util.Objects.*;

public class ViewSiteView implements View {

    private final String name;

    private JPanel panel;
    private ViewSite viewSite;

    public ViewSiteView(String name) {
        this.name = requireNonNull(name);
    }

    @Override
    public void install(ViewSite viewSite) {
        this.viewSite = viewSite;

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel jLabel = new JLabel(name);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(jLabel, gbc);

        panel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));

        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(panel);
    }

    @Override
    public void uninstall() {
        if (viewSite == null) {
            return;
        }

        ViewContent viewContent = viewSite.getViewContent();
        viewContent.setComponent(null);

        viewSite = null;
    }
}
