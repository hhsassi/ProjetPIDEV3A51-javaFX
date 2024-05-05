package com.alphadev.artemisjvfx.gui;

public class gui {
    private static gui gui;
    private final ViewFactory viewFactory;


    private gui()
    {
        this.viewFactory = new ViewFactory();

    }

    public static synchronized gui getInstance()
    {
        if( gui == null)
        {
            gui = new gui();

        }
        return gui;

    }

    public ViewFactory getViewFactory()
        {
            return viewFactory;
        }
}
