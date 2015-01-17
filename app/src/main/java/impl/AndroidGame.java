package impl;

/**
 * Created by GreenyNeko on 27.11.2014.
 */

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.kod.knightsofdrakonur.framework.Audio;
import com.kod.knightsofdrakonur.framework.FileIO;
import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Input;
import com.kod.knightsofdrakonur.framework.Screen;

import java.util.Locale;

import src.entity.Player;

public abstract class AndroidGame extends Activity implements Game
{
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    Locale lang;
    Player player;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boolean isPortrait =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 800 : 1280;
        int frameBufferHeight = isPortrait ? 1280 : 800;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight,
                Config.RGB_565);
        Rect rectSize = new Rect();
        getWindowManager().getDefaultDisplay().getRectSize(rectSize);
        float scaleX = (float)frameBufferWidth / rectSize.width();
        float scaleY = (float)frameBufferHeight / rectSize.height();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();
        setContentView(renderView);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        renderView.pause();
        screen.pause();

        if(isFinishing())
        {
            screen.dispose();
        }
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        screen.onBackPressed();
    }

    @Override
    public Input getInput()
    {
        return input;
    }

    @Override
    public FileIO getFileIO()
    {
        return fileIO;
    }

    @Override
    public Graphics getGraphics()
    {
        return graphics;
    }

    @Override
    public Audio getAudio()
    {
        return audio;
    }

    @Override
    public void setScreen(Screen screen)
    {
        if(screen == null)
        {
            throw new IllegalArgumentException("Screen must not be null");
        }

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public void setLocale(Locale locale)
    {
        this.lang = locale;
    }

    @Override
    public Locale getLocale()
    {
        if(this.lang == null)
        {
            return Locale.ENGLISH;
        }
        return this.lang;
    }

    public Screen getCurrentScreen()
    {
        return screen;
    }

    public int getRotation()
    {
        return getResources().getConfiguration().orientation;
    }

    @Override
    public void finishGame()
    {
        this.finish();
    }

    @Override
    public Player getCurrentPlayer()
    {
        return this.player;
    }

    @Override
    public void setCurrentPlayer(Player player)
    {
        this.player = player;
    }
}
