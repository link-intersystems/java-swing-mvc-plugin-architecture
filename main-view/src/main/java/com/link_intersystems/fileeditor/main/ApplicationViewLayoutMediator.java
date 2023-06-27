package com.link_intersystems.fileeditor.main;

import com.link_intersystems.swing.action.concurrent.TaskActionListener;
import com.link_intersystems.swing.view.layout.ViewLayout;
import com.link_intersystems.swing.view.layout.ViewLayoutContribution;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

        public class ApplicationViewLayoutMediator implements TaskActionListener<List<ViewLayoutContribution>, Void> {

            private final ViewLayout viewLayout;

            private final List<ViewLayoutContribution> viewLayoutContributions = new ArrayList<>();

            public ApplicationViewLayoutMediator(ViewLayout viewLayout) {
                this.viewLayout = requireNonNull(viewLayout);
            }

            @Override
            public void done(List<ViewLayoutContribution> result) {
                result.forEach(this::installView);
            }

            private void installView(ViewLayoutContribution viewLayoutContribution) {
                viewLayoutContribution.install(viewLayout);
                viewLayoutContributions.add(viewLayoutContribution);
            }

            public void dispose() {
                viewLayoutContributions.forEach(vc -> vc.uninstall(viewLayout));
            }
        }
