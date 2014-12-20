package impl;

/**
 * Created by GreenyNeko on 11.12.2014.
 */


import android.graphics.Bitmap;

import com.kod.knightsofdrakonur.framework.Image;
import com.kod.knightsofdrakonur.framework.Graphics.ImageFormat;

public class AndroidImage implements Image
{
    Bitmap bitmap;
    ImageFormat format;

    public AndroidImage(Bitmap bitmap, ImageFormat imageFormat)
    {
        this.bitmap = bitmap;
        this.format = imageFormat;
    }

    @Override
    public int getWidth()
    {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight()
    {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat()
    {
        return format;
    }

    @Override
    public void dispose()
    {
        bitmap.recycle();
    }
}
