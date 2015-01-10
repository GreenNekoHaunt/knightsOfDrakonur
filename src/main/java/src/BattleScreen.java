package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.content.Context;
import android.graphics.Paint;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;
import com.kod.knightsofdrakonur.framework.Screen;

import java.util.List;
import java.util.Random;

import src.entity.ComputerPlayer;
import src.entity.Player;
import src.entity.Role;
import src.skills.Skill;
import util.Math;

public class BattleScreen extends Screen
{
    public int ticks = 0;

    private Random random = new Random();
    private Player player;
    private ComputerPlayer[] dungeonEnemies = new ComputerPlayer[10];
    private ComputerPlayer enemy;
    private int currentEnemy = 0;
    private int currentRound = 0;
    private int prevRound = 0;

    public BattleScreen(Game game)
    {
        super(game);
        player = game.getCurrentPlayer();
        dungeonEnemies[0] = new ComputerPlayer("lang.npc.rat.name", 1, Role.SCOUT);
        dungeonEnemies[0].setSkillSlot(0, Skill.scratch.getCopy());
        dungeonEnemies[0].readySkills("");
        dungeonEnemies[1] = new ComputerPlayer("lang.npc.rat.name", 1, Role.SCOUT);
        dungeonEnemies[1].setSkillSlot(0, Skill.scratch.getCopy());
        dungeonEnemies[1].readySkills("");
        dungeonEnemies[2] = new ComputerPlayer("lang.npc.bat.name", 1, Role.SCOUT);
        dungeonEnemies[2].setSkillSlot(0, Skill.bite.getCopy());
        dungeonEnemies[2].readySkills("");
        dungeonEnemies[3] = new ComputerPlayer("lang.npc.rat.name", 1, Role.SCOUT);
        dungeonEnemies[3].setSkillSlot(0, Skill.scratch.getCopy());
        dungeonEnemies[3].readySkills("");
        dungeonEnemies[4] = new ComputerPlayer("lang.npc.rat.name", 2, Role.SCOUT);
        dungeonEnemies[4].setSkillSlot(0, Skill.scratch.getCopy());
        dungeonEnemies[4].setSkillSlot(1, Skill.bite.getCopy());
        dungeonEnemies[4].readySkills("");
        dungeonEnemies[5] = new ComputerPlayer("lang.npc.rat.name", 1, Role.SCOUT);
        dungeonEnemies[5].setSkillSlot(0, Skill.scratch.getCopy());
        dungeonEnemies[5].readySkills("");
        dungeonEnemies[6] = new ComputerPlayer("lang.npc.bat.name", 1, Role.SCOUT);
        dungeonEnemies[6].setSkillSlot(0, Skill.bite.getCopy());
        dungeonEnemies[6].readySkills("");
        dungeonEnemies[7] = new ComputerPlayer("lang.npc.rat.name", 2, Role.SCOUT);
        dungeonEnemies[7].setSkillSlot(0, Skill.scratch.getCopy());
        dungeonEnemies[7].setSkillSlot(1, Skill.bite.getCopy());
        dungeonEnemies[7].readySkills("");
        dungeonEnemies[8] = new ComputerPlayer("lang.npc.bat.name", 2, Role.SCOUT);
        dungeonEnemies[8].setSkillSlot(0, Skill.bite.getCopy());
        // dungeonEnemies[8].setSkillSlot(1, Skill.lifeDrain.getCopy());
        dungeonEnemies[8].readySkills("");
        dungeonEnemies[9] = new ComputerPlayer("lang.npc.terantula.name", 2, Role.SCOUT);
        //dungeonEnemies[9].setSkillSlot(0, Skill.stickyWeb.getCopy());
        dungeonEnemies[9].setSkillSlot(1, Skill.bite.getCopy());
        dungeonEnemies[9].readySkills("");
        currentEnemy = 0;
        enemy = dungeonEnemies[currentEnemy];
        player.readySkills("");
    }

    @Override
    public void update(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        this.prevRound = currentRound;
        if(currentRound % 2 == 0)
        {
            for (int i = 0; i < touchEvents.size(); i++)
            {
                TouchEvent touchEvent = touchEvents.get(i);
                if (touchEvent.type == TouchEvent.TOUCH_UP)
                {
                    if (Math.inBoundary(touchEvent,
                            (screenW / 2) - Assets.ui_skillSlot.getWidth() * 3,
                            (int) (screenH * 0.87), 128, 128))
                    {
                        if(player.getSkillOnSlot(0).isReady()
                                && player.hasEnoughManaFor(player.getSkillOnSlot(0)))
                        {
                            player.cast(0, currentRound, enemy);
                            this.currentRound++;
                        }

                    }
                    else if (Math.inBoundary(touchEvent,
                            (screenW / 2) - Assets.ui_skillSlot.getWidth() * 2,
                            (int) (screenH * 0.87), 128, 128))
                    {
                        if(player.getSkillOnSlot(1).isReady()
                                && player.hasEnoughManaFor(player.getSkillOnSlot(1)))
                        {
                            player.cast(1, currentRound, enemy);
                            this.currentRound++;
                        }
                    }
                    else if (Math.inBoundary(touchEvent,
                            (screenW / 2) - Assets.ui_skillSlot.getWidth(),
                            (int) (screenH * 0.87), 128, 128))
                    {
                        if(player.getSkillOnSlot(2).isReady()
                                && player.hasEnoughManaFor(player.getSkillOnSlot(2)))
                        {
                            player.cast(2, currentRound, enemy);
                            this.currentRound++;
                        }
                    }
                    else if (Math.inBoundary(touchEvent,
                            (screenW / 2),
                            (int) (screenH * 0.87), 128, 128))
                    {
                        if(player.getSkillOnSlot(3).isReady()
                                && player.hasEnoughManaFor(player.getSkillOnSlot(3)))
                        {
                            player.cast(3, currentRound, enemy);
                            this.currentRound++;
                        }
                    }
                    else if (Math.inBoundary(touchEvent,
                            (screenW / 2) + Assets.ui_skillSlot.getWidth(),
                            (int) (screenH * 0.87), 128, 128))
                    {
                        if(player.getSkillOnSlot(4).isReady()
                                && player.hasEnoughManaFor(player.getSkillOnSlot(4)))
                        {
                            player.cast(4, currentRound, enemy);
                            this.currentRound++;
                        }
                    }
                    else if (Math.inBoundary(touchEvent,
                            (screenW / 2) + Assets.ui_skillSlot.getWidth() * 2,
                            (int) (screenH * 0.87), 128, 128))
                    {
                        if(player.getSkillOnSlot(5).isReady()
                                && player.hasEnoughManaFor(player.getSkillOnSlot(5)))
                        {
                            player.cast(5, currentRound, enemy);
                            this.currentRound++;
                        }
                    }
                }
            }
        }
        else
        {
            enemy.chooseSkill(currentRound, player);
            this.currentRound++;
        }

        // enemy defeated
        player.onUpdate(this.currentRound, enemy, this.prevRound != this.currentRound);
        enemy.onUpdate(this.currentRound, player, this.prevRound != this.currentRound);
        ticks++;

        if(player.isDead())
        {
            player.revive();
            game.setScreen(new MenuScreen(game));
        }
        if(enemy.isDead())
        {
            enemy = dungeonEnemies[++this.currentEnemy];
        }
    }

    @Override
    public void paint(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();

        graphics.clearScreen(0x459AFF);

        // Draw background
        if(enemy.getLevel() % 10 == 0)
        {
            graphics.drawScaledImage(Assets.bg_exampleBoss, 0, 0, screenW, screenH,
                    0, 0, 700, 1270);
        }
        else
        {
            graphics.drawScaledImage(Assets.bg_example, 0, 0, screenW, screenH,
                    0, 0, 700, 1270);
        }

        // Enemy Health Bar
        graphics.drawImage(Assets.ui_barRect,
                (screenW / 2) - Assets.ui_barRect.getWidth() / 2,
                (int)(screenH * 0.05));
        // Health Bar
        graphics.drawScaledImage(Assets.ui_barKnife,
                (int)(screenW * 0.02), (int)(screenH * 0.83) - 84,
                400, 48, 0, 0, 500, 96);
        // Mana Bar
        graphics.drawScaledImage(Assets.ui_barRect,
                (int)(screenW * 0.02), (int)(screenH * 0.83) - 36,
                400, 36, 0, 0, 500, 96);
        // Xp Bar
        graphics.drawScaledImage(Assets.ui_barRect,
                (int)(screenW * 0.02), (int)(screenH * 0.83),
                500, 27, 0, 0, 500, 96);

        // Fill Enemy Health
        for(int i = 0;
            i < (int)(((double)enemy.getHealth() / (double)enemy.getTotalHealth()) * 480); i++)
        {
            graphics.drawImage(Assets.ui_barFill,
                    (screenW / 2) - Assets.ui_barRect.getWidth() / 2 + 10 + i,
                    (int)(screenH * 0.05) + 10,
                    7, 0, 3, 76);
        }

        // Fill Player Health
        for (int i = 0;
                i < (int)(((double)player.getHealth() / (double)player.getTotalHealth()) * 395);
                i++)
        {
            int height = 36;
            int mod = 0;
            if(i > 300)
            {
                height -= (i - 300) * 0.4;
                mod += (i - 300) * 0.4;
            }
            graphics.drawScaledImage(Assets.ui_barFill,
                    (int)(screenW * 0.02) + 8 + i, (int)(screenH * 0.83) - 78 + mod,
                    1, height, 6, 0, 3, 76);
        }
        // Fill Mana Bar
        for(int i = 0;
            i < (int)(((double)player.getMana() / (double)player.getTotalMana()) * 380); i++)
        {
            graphics.drawScaledImage(Assets.ui_barFill,
                    (int)(screenW * 0.02) + 12 + i, (int)(screenH * 0.83) - 30,
                    1, 24, 1, 0, 3, 76);
        }
        // Fill Xp Bar
        for(int i = 0; i < (int)(((double)player.getXp() / (double)player.getReqXp()) * 480); i++)
        {
            graphics.drawScaledImage(Assets.ui_barFill,
                    (int)(screenW * 0.02) + 10 + i, (int)(screenH * 0.83) + 6,
                    1, 15, 3, 0, 1, 76);
        }

        // Strings
        Paint textStyle = new Paint();
        textStyle.setARGB(250, 0, 0, 0);
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(36.0f);
        textStyle.setTextLocale(game.getLocale());
        // Enemy Name and Level
        if(enemy.getLevel() % 10 == 0)
        {
            graphics.drawString((Context)game,
                    enemy.getNameId() + String.valueOf(enemy.getLevel()), screenW / 2,
                    (int) (screenH * 0.035), textStyle);
        }
        else
        {
            graphics.drawString((Context)game, enemy.getNameId() + String.valueOf(enemy.getLevel()),
                    screenW / 2, (int) (screenH * 0.035), textStyle);
        }
        //     Enemy Health
        graphics.drawRawString(
                String.valueOf(enemy.getHealth()) + "/" + String.valueOf(enemy.getTotalHealth()),
                screenW / 2, (int) (screenH * 0.05) + 60, textStyle);


        //     Player Xp
        textStyle.setTextSize(15);
        graphics.drawRawString(
                String.valueOf(player.getXp()) + "/" + String.valueOf(player.getReqXp()) + "XP",
                (int) (screenW * 0.02 + Assets.ui_barRect.getWidth() / 2),
                (int) (screenH * 0.83) + 20, textStyle);
        //     Player Mana
        textStyle.setTextSize(24);
        graphics.drawRawString(
                String.valueOf(player.getMana()) + "/" + String.valueOf(player.getTotalMana()),
                (int) (screenW * 0.02) + 12 + 200, (int) (screenH * 0.83) - 12, textStyle);
        //     Player Health
        textStyle.setTextSize(36);
        graphics.drawRawString(
                String.valueOf(player.getHealth()) + "/" + String.valueOf(player.getTotalHealth()),
                (int)(screenW * 0.02) + (Assets.ui_barKnife.getWidth() / 2) - 64,
                (int)(screenH * 0.83) - 48, textStyle);
        //     Player Level
        textStyle.setTextSize(36);
        textStyle.setTextAlign(Paint.Align.LEFT);
        graphics.drawString((Context)game, "lang.battle.ui.level", (int) (screenW * 0.08),
                (int) (screenH * 0.75), textStyle);
        graphics.drawRawString(String.valueOf(player.getLevel()),
                (int)(screenW * 0.08) + 36 * 6,
                (int)(screenH * 0.75), textStyle);

        // Skill Interface
        graphics.drawImage(Assets.ui_skillSlotLeft,
                (screenW / 2) - Assets.ui_skillSlot.getWidth() * 3,
                (int)(screenH * 0.87));
        for(int i = 0; i < 4; i++)
        {
            int offset = i - 2;
            graphics.drawImage(Assets.ui_skillSlot,
                    (screenW / 2) + Assets.ui_skillSlot.getWidth() * offset,
                    (int)(screenH * 0.87));
        }
        graphics.drawImage(Assets.ui_skillSlotRight,
                (screenW / 2) + Assets.ui_skillSlot.getWidth() * 2,
                (int)(screenH * 0.87));

        for(int i = 0; i < 6; i++)
        {
            int offset = i - 3;
            player.getSkillOnSlot(i).draw(game, graphics,
                    (screenW / 2) + Assets.ui_skillSlot.getWidth() * offset + 8,
                    (int)(screenH * 0.87) + 8, this.currentRound);
        }
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
    public void onBackPressed()
    {
        game.setScreen(new MenuScreen(game));
    }
}
