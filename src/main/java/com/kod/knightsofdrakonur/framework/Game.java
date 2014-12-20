package com.kod.knightsofdrakonur.framework;

/**
 * Created by GreenyNeko on 19.11.2014.
 */
public interface Game
{
    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();

    public int getRotation();
}
