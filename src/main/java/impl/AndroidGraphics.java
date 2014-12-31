package impl;

/**
 * Created by GreenyNeko on 27.11.2014.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.util.Xml;

import com.kod.knightsofdrakonur.framework.FileIO;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Image;

import org.xmlpull.v1.XmlPullParser;

public class AndroidGraphics implements Graphics
{
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect destRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer)
    {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public Image newImage(String fileName, ImageFormat format)
    {
        Config config = null;
        if(format == ImageFormat.RGB565)
        {
            config = Config.RGB_565;
        }
        else if(format == ImageFormat.ARGB4444)
        {
            config = Config.ARGB_4444;
        }
        else
        {
            config = Config.ARGB_8888;
        }

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try
        {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if(bitmap == null)
            {
                throw new RuntimeException("Couldn\'t load bitmap from asset \'" + fileName + "\'");
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException("Couldn\'t load bitmap from asset \'" + fileName + "\'");
        }
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch(IOException e)
                {

                }
            }
        }

        if(bitmap.getConfig() == Config.RGB_565)
        {
            format = ImageFormat.RGB565;
        }
        else if(bitmap.getConfig() == Config.ARGB_4444)
        {
            format = ImageFormat.ARGB4444;
        }
        else
        {
            format = ImageFormat.ARGB8888;
        }

        return new AndroidImage(bitmap, format);
    }

    @Override
    public void clearScreen(int color)
    {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, (color & 0x0000FF));
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color)
    {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color)
    {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    @Override
    public void drawARGB(int a, int r, int g, int b)
    {
        paint.setStyle(Style.FILL);
        canvas.drawARGB(a, r, g, b);
    }

    @Override
    public void drawRawString(String text, int x, int y, Paint paint)
    {
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void drawString(Context context, String id, int x, int y, Paint paint)
    {
        Locale locale = paint.getTextLocale();
        FileIO lang = new AndroidFileIO(context);
        String text = id;

        try
        {
            InputStream in = lang.readAsset("lang/" + locale.getDisplayName() + ".lang");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null)
            {
                if(line.substring(0, line.indexOf('=')).contentEquals(id))
                {
                    text = line.substring(line.indexOf('=') + 1);
                }
            }
        }
        catch(IOException e)
        {
            // IO Error
            Log.e("KOD", "IOException occurred while trying to load language string \'" + id
                    + "\' from language file \'lang/" + locale.getDisplayName() + ".lang\'");
            text = id;
        }
        catch(NullPointerException e)
        {
            // No matching entry in the language file.
            Log.e("KOD", "Couldn't find a matching entry for \'" + id + "\' from the file\'lang/"
                    + locale.getDisplayName() + ".lang\'");
            text = id;
        }
        catch(Exception e)
        {
            // If anything goes wrong just draw the raw string.
            Log.e("KOD", "An error occurred while trying to load language string \'" + id
                    + "\' from language file \'lang/" + locale.getDisplayName() + ".lang\'");
            text = id;
        }

        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void drawImage(Image image, int x, int y, int srcX, int srcY,
                          int srcWidth, int srcHeight)
    {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        destRect.left = x;
        destRect.top = y;
        destRect.right = x + srcWidth;
        destRect.bottom = y + srcHeight;

        canvas.drawBitmap(((AndroidImage)image).bitmap, srcRect, destRect, null);
    }

    @Override
    public void drawImage(Image image, int x, int y) {
        canvas.drawBitmap(((AndroidImage)image).bitmap, x, y, null);
    }

    @Override
    public void drawScaledImage(Image image, int x, int y, int width, int height, int srcX,
                                int srcY, int srcWidth, int srcHeight)
    {
        srcRect.left =  srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        destRect.left = x;
        destRect.top = y;
        destRect.right = x + width;
        destRect.bottom = y + height;

        canvas.drawBitmap(((AndroidImage)image).bitmap, srcRect, destRect, null);
    }

    @Override
    public int getWidth()
    {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight()
    {
        return frameBuffer.getHeight();
    }

    public void switchSizes()
    {
        frameBuffer = Bitmap.createBitmap(frameBuffer.getHeight(), frameBuffer.getWidth(), Config.RGB_565);
    }
}
