package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Screen;
import com.kod.knightsofdrakonur.framework.Graphics.ImageFormat;

import src.mechanic.Buff;
import src.skills.Skill;

public class LoadResources extends Screen
{
    public LoadResources(Game game)
    {
        super(game);
    }

    @Override
    /* Called whenever the activity updates.
     *
     * @param float deltaTime - the time that has passed.
     */
    public void update(float deltaTime)
    {
        Graphics graphics = game.getGraphics();

        Assets.ui_skillSlot = graphics.newImage("gfx/ui/skillSlot.png", ImageFormat.ARGB8888);
        Assets.ui_skillSlotLeft = graphics.newImage("gfx/ui/skillSlotLeft.png", ImageFormat.ARGB8888);
        Assets.ui_skillSlotRight = graphics.newImage("gfx/ui/skillSlotRight.png", ImageFormat.ARGB8888);

        Assets.ui_shield = graphics.newImage("gfx/ui/menuShield.png", ImageFormat.ARGB8888);
        Assets.ui_iconAttributes = graphics.newImage("gfx/ui/iconAttributes.png", ImageFormat.ARGB8888);
        Assets.ui_iconSkills = graphics.newImage("gfx/ui/iconSkills.png", ImageFormat.ARGB8888);
        Assets.ui_iconEquip = graphics.newImage("gfx/ui/iconEquip.png", ImageFormat.ARGB8888);

        Assets.ui_iconCooldown = graphics.newImage("gfx/ui/iconCooldown.png", ImageFormat.ARGB8888);
        Assets.ui_iconMana = graphics.newImage("gfx/ui/iconMana.png", ImageFormat.ARGB8888);

        Assets.ui_skillBar = graphics.newImage("gfx/ui/skillBar.png", ImageFormat.ARGB8888);
        Assets.ui_scrollBar = graphics.newImage("gfx/ui/scrollBar.png", ImageFormat.RGB565);
        Assets.ui_scrollBarArrows = graphics.newImage("gfx/ui/scrollBarArrows.png", ImageFormat.RGB565);
        Assets.ui_scrollBarCursor = graphics.newImage("gfx/ui/scrollBarCursor.png", ImageFormat.RGB565);

        Assets.ui_upgradeArrows = graphics.newImage("gfx/ui/upgradeArrows.png", ImageFormat.RGB565);

        Assets.ui_barFill = graphics.newImage("gfx/ui/barFill.png", ImageFormat.RGB565);
        Assets.ui_barRect = graphics.newImage("gfx/ui/barRect.png", ImageFormat.ARGB8888);
        Assets.ui_barKnife = graphics.newImage("gfx/ui/barKnife.png", ImageFormat.ARGB8888);

        Assets.bg_example = graphics.newImage("gfx/bg/example.png", ImageFormat.RGB565);
        Assets.bg_exampleBoss = graphics.newImage("gfx/bg/exampleBoss.png", ImageFormat.RGB565);

        // Give graphics to Skill and Buff to initialize all skill and buff graphics
        Skill.initializeGraphics(graphics);
        Buff.initializeGraphics(graphics);

        game.setScreen(new TitleScreen(game));
    }

    @Override
    /* Called whenever the screen is updated.
     *
     * @param float deltaTime - the time that has passed since the last update.
     */
    public void paint (float deltaTime)
    {
    }

    @Override
    /* Called when the activity is paused. */
    public void pause()
    {
    }

    @Override
    /* Called when the activity is resumed. */
    public void resume()
    {
    }

    @Override
    /* Called when the activity is disposed. */
    public void dispose()
    {
    }

    @Override
    /* When the back button of the smartphone is pressed. */
    public void onBackPressed()
    {
    }
}