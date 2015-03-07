package com.kod.knightsofdrakonur.framework;

import java.util.Locale;

import src.entity.Player;

/**
 * Created by GreenyNeko on 19.11.2014.
 */
public interface Game
{
    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Screen getCurrentScreen();

    public Screen getInitScreen();

    public int getRotation();

    public Locale getLocale();

    public Player getCurrentPlayer();

    public Player getCurrentPlayer2();

    public void setLocale(Locale locale);

    public void setScreen(Screen screen);

    public void setCurrentPlayer(Player player);

    public void setCurrentPlayer2(Player player);
}
