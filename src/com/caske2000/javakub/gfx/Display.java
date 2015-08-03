package com.caske2000.javakub.gfx;

import com.caske2000.javakub.Window;

import java.awt.*;

/**
 * Created by Caske2000 on 03/08/2015.
 */
public class Display extends Canvas
{
    public Display(Window window)
    {
        setBounds(0, 0, window.WIDTH, window.HEIGHT);
    }
}
