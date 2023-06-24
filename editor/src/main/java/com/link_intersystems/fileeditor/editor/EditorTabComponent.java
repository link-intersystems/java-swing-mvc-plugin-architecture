package com.link_intersystems.fileeditor.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

class EditorTabComponent extends JPanel {

    private final JLabel titleLabel;
    private List<ActionListener> actionListeners = new ArrayList<>();

    private TabModel tabModel = new TabModel() {
        @Override
        public String getTitle() {
            return "";
        }
    };

    EditorTabComponent() {
        setLayout(new GridBagLayout());

        setOpaque(false);

        titleLabel = new JLabel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        add(titleLabel, gbc);

        JButton closeButton = new TabCloseButton();
        closeButton.addActionListener(ae -> {
            ActionListener closeActionListener = tabModel.getCloseActionListener();
            if (closeActionListener != null) {
                closeActionListener.actionPerformed(ae);
            }
            actionListeners.forEach(l -> l.actionPerformed(ae));
        });

        gbc.gridx++;
        gbc.ipadx = 0;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 5, 0, 0);
        add(closeButton, gbc);
    }

    public void setModel(TabModel tabModel) {
        this.tabModel = requireNonNull(tabModel);
        onTabModelChanged();
    }

    private void onTabModelChanged() {
        titleLabel.setText(tabModel.getTitle());
    }

    public TabModel getModel() {
        return tabModel;
    }

    public void addCloseListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }
}
