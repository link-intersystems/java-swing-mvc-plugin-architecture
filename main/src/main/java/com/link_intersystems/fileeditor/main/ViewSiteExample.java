package com.link_intersystems.fileeditor.main;

import javax.swing.*;
import java.awt.*;

public class ViewSiteExample {

    public static void main(String[] args) {
        JFrame frame = new JFrame("ViewSite Example");
        frame.setSize(640, 480);

        frame.setLocationRelativeTo(null);

        Container contentPane = frame.getContentPane();

        contentPane.add(createViewSiteComponent("ViewSite north"), BorderLayout.NORTH);
        contentPane.add(createViewSiteComponent("ViewSite center"), BorderLayout.CENTER);
        contentPane.add(createViewSiteComponent("ViewSite west"), BorderLayout.WEST);
        contentPane.add(createViewSiteComponent("ViewSite east"), BorderLayout.EAST);
        contentPane.add(createViewSiteComponent("ViewSite south"), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static Component createViewSiteComponent(String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel jLabel = new JLabel(name);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(jLabel, gbc);

        panel.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        return panel;
    }
}
