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
        Assets.unused = graphics.newImage("gfx/skills/unused.png", ImageFormat.RGB565);
        Assets.ui_skillSlot = graphics.newImage("gfx/ui/skillSlot.png", ImageFormat.ARGB4444);
        Assets.ui_skillSlotLeft = graphics.newImage("gfx/ui/skillSlotLeft.png", ImageFormat.ARGB4444);
        Assets.ui_skillSlotRight = graphics.newImage("gfx/ui/skillSlotRight.png", ImageFormat.ARGB4444);
        Assets.skill_fireball = graphics.newImage("gfx/skills/fireball.png", ImageFormat.RGB565);

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