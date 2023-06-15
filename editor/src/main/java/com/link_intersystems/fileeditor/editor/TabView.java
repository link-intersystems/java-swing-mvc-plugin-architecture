package com.link_intersystems.fileeditor.editor;

import com.link_intersystems.swing.view.AbstractView;
import com.link_intersystems.swing.view.ViewSite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.util.Objects.*;

class TabView extends AbstractView {

    private TabModel tabModel = new TabModel() {

        @Override
        public String getTitle() {
            return "";
        }

        @Override
        public ActionListener getCloseActionListener() {
            return null;
        }
    };

    public void setTabModel(TabModel tabModel) {
        this.tabModel = requireNonNull(tabModel);
    }

    @Override
    protected void doInstall(ViewSite viewSite) {
        ActionListener closeActionListener = tabModel.getCloseActionListener();

        if (closeActionListener != null) {
            JPanel tabComponent = new JPanel(new GridBagLayout());
            tabComponent.setOpaque(false);

            JLabel titleLabel = new JLabel(tabModel.getTitle());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            tabComponent.add(titleLabel, gbc);

            JButton closeButton = createCloseButton(closeActionListener);

            gbc.gridx++;
            gbc.ipadx = 0;
            gbc.weightx = 0;
            gbc.insets = new Insets(0, 5, 0, 0);
            tabComponent.add(closeButton, gbc);

            viewSite.setComponent(tabComponent);
        }
    }

    private JButton createCloseButton(ActionListener closeActionListener) {
        JButton closeButton = new TabCloseButton();
        closeButton.addActionListener(closeActionListener);
        return closeButton;
    }

    @Override
    protected void doUninstall(ViewSite viewSite) {
        viewSite.setComponent(null);
    }
}
