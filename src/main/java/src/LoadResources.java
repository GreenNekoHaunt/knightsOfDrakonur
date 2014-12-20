package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Screen;
import com.kod.knightsofdrakonur.framework.Graphics.ImageFormat;

public class LoadResources extends Screen
{
    public LoadResources(Game game)
    {
        super(game);
    }

    @Override
    public void update(float deltaTime)
    {
        Graphics graphics = game.getGraphics();

        game.setScreen(new TitleScreen(game));
    }

    @Override
    public void paint (float deltaTime)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void backButton()
    {
    }
}