package com.kod.knightsofdrakonur.framework;

import android.content.Context;
import android.graphics.Paint;
import android.widget.EditText;

/**
 * Created by GreenyNeko on 19.11.2014.
 */
public interface Graphics
{
    public static enum ImageFormat
    {
        ARGB8888, ARGB4444, RGB565
    }

    public Image newImage(String fileName, ImageFormat format);

    public void clearScreen(int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawImage(Image image, int x, int y);

    public void drawImage(Image image, int x, int y, int srcX, int srcY,
                          int srcWidth, int srcHeight);

    public void drawScaledImage(Image image, int x, int y, int width, int height, int srcX,
                                int srcY, int srcWidth, int srcHeight);

    void drawString(String text, int x, int y, Paint paint);

    void drawTextEdit(EditText editText, int x, int y, int width, int height, Paint textStyle);

    public int getWidth();

    public int getHeight();

    public void drawARGB(int r, int g, int b, int a);
}
