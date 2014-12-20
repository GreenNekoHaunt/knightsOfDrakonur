package com.kod.knightsofdrakonur.framework;

import com.kod.knightsofdrakonur.framework.Graphics.ImageFormat;

/**
 * Created by GreenyNeko on 19.11.2014.
 */
public interface Image
{
    public int getWidth();

    public int getHeight();

    public ImageFormat getFormat();

    public void dispose();
}
