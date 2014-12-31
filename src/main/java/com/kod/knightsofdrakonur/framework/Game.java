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

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();

    public int getRotation();

    public void setLocale(Locale locale);

    public Locale getLocale();

    public void finishGame();

    public Player getCurrentPlayer();

    public void setCurrentPlayer(Player player);
}
