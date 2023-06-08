package com.link_intersystems.mvc;

public interface ViewFactory {

    public String getName();

    public View createView(View parentView);
}
