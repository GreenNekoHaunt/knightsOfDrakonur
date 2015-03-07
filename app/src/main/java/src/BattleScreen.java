package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import android.graphics.Paint;
import android.util.Log;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;
import com.kod.knightsofdrakonur.framework.Screen;

import java.util.List;
import java.util.Random;

import src.entity.ComputerPlayer;
import src.entity.Entity;
import src.entity.Player;
import src.entity.Role;
import src.mechanic.StatUnlock;
import src.skills.Skill;
import util.LocaleStringBuilder;
import util.Math;

public class BattleScreen extends Screen
{
    private int ticks = 0;
    private Random random = new Random();
    private Player player;
    private ComputerPlayer[] dungeonEnemies = new ComputerPlayer[10];
    private Entity enemy;
    private int currentEnemy = 0;
    private int currentRound = 0;
    private int prevRound = 0;
    private int mode = 0;

    public BattleScreen(Game game, int mode)
    {
        super(game);
        this.mode = mode;
        player = game.getCurrentPlayer();
        switch(mode)
        {
            case 1:
                this.enemy = game.getCurrentPlayer2();
                break;
            default:
                dungeonEnemies[0] = new ComputerPlayer("lang.npc.rat.name",
                        1 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[0].setSkillSlot(0, Skill.scratch.getCopy());
                dungeonEnemies[0].readySkills("");
                dungeonEnemies[1] = new ComputerPlayer("lang.npc.rat.name",
                        1 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[1].setSkillSlot(0, Skill.scratch.getCopy());
                dungeonEnemies[1].readySkills("");
                dungeonEnemies[2] = new ComputerPlayer("lang.npc.bat.name",
                        1 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[2].setSkillSlot(0, Skill.bite.getCopy());
                dungeonEnemies[2].readySkills("");
                dungeonEnemies[3] = new ComputerPlayer("lang.npc.rat.name",
                        1 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[3].setSkillSlot(0, Skill.scratch.getCopy());
                dungeonEnemies[3].readySkills("");
                dungeonEnemies[4] = new ComputerPlayer("lang.npc.rat.name",
                        2 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[4].setSkillSlot(0, Skill.scratch.getCopy());
                dungeonEnemies[4].setSkillSlot(1, Skill.bite.getCopy());
                dungeonEnemies[4].readySkills("");
                dungeonEnemies[5] = new ComputerPlayer("lang.npc.rat.name",
                        1 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[5].setSkillSlot(0, Skill.scratch.getCopy());
                dungeonEnemies[5].readySkills("");
                dungeonEnemies[6] = new ComputerPlayer("lang.npc.bat.name",
                        1 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[6].setSkillSlot(0, Skill.bite.getCopy());
                dungeonEnemies[6].readySkills("");
                dungeonEnemies[7] = new ComputerPlayer("lang.npc.rat.name",
                        2 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[7].setSkillSlot(0, Skill.scratch.getCopy());
                dungeonEnemies[7].setSkillSlot(1, Skill.bite.getCopy());
                dungeonEnemies[7].readySkills("");
                dungeonEnemies[8] = new ComputerPlayer("lang.npc.bat.name",
                        2 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[8].setSkillSlot(0, Skill.bite.getCopy());
                // dungeonEnemies[8].setSkillSlot(1, Skill.lifeDrain.getCopy());
                dungeonEnemies[8].readySkills("");
                dungeonEnemies[9] = new ComputerPlayer("lang.npc.tarantula.name",
                        2 + player.getAscensions(), Role.SCOUT);
                dungeonEnemies[9].setSkillSlot(0, Skill.bite.getCopy());
                dungeonEnemies[9].readySkills("");
                for(int i = 0; i < dungeonEnemies.length; i++)
                {
                    dungeonEnemies[i].registerName(game);
                }
                currentEnemy = 0;
                enemy = dungeonEnemies[currentEnemy];
        }
        player.readySkills("");
        this.currentRound = random.nextInt(2);
    }

    @Override
    /* Called whenever the activity updates.
     *
     * @param float deltaTime - the time that has passed.
     */
    public void update(float deltaTime)
    {
        Random random = new Random();
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        this.prevRound = this.currentRound;

        if(this.currentRound % 2 == 0 || this.mode == 1)
        {
            if(this.mode == 1)
            {
                this.player = game.getCurrentPlayer();
                this.enemy = game.getCurrentPlayer2();
                if(currentRound % 2 == 1)
                {
                    this.player = game.getCurrentPlayer2();
                    this.enemy = game.getCurrentPlayer();
                }
            }
            if(this.player.canCast())
            {
                handlePlayerInput(touchEvents);
            }
            else
            {
                this.currentRound++;
            }
        }
        else
        {
            if(enemy instanceof ComputerPlayer)
            {
                if(enemy.canCast())
                {
                    ((ComputerPlayer) enemy).chooseSkill(currentRound, player);
                }
            }
            this.currentRound++;
        }


        player.onUpdate(this.currentRound, enemy, this.prevRound != this.currentRound);
        enemy.onUpdate(this.currentRound, player, this.prevRound != this.currentRound);
        // Update Player 2 level according to player 1 level.
        while(player.getLevel() > game.getCurrentPlayer2().getLevel())
        {
            game.getCurrentPlayer2().onLevelUp();
            game.getCurrentPlayer2().onUpdate(this.currentRound, enemy,
                    this.prevRound != this.currentRound);
        }
        ticks++;

        if(player.isDead())
        {
            player.revive();
            StatUnlock.increaseDeaths();
            game.setScreen(new MenuScreen(game));
        }
        if(enemy.isDead())
        {
            if(this.mode == 0)
            {
                StatUnlock.increaseKills();
                if (this.currentEnemy >= 9)
                {
                    game.setScreen(new MenuScreen(game));
                    game.getCurrentPlayer().setAscensions(player.getAscensions() + 1);
                    StatUnlock.increaseAscensions();
                }
                else
                {
                    enemy = dungeonEnemies[++this.currentEnemy];
                }
            }
            else if(this.mode == 1)
            {
                game.getCurrentPlayer().revive();
                game.getCurrentPlayer2().revive();
                game.setScreen(new MenuScreen(game));
            }
            else
            {
                StatUnlock.increaseKills();
            }
        }
        StatUnlock.updateUnlocks(game);
    }

    @Override
    /* Called whenever the screen is updated.
     *
     * @param float deltaTime - the time that has passed since the last update.
     */
    public void paint(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        Graphics graphics = game.getGraphics();

        graphics.clearScreen(0x459AFF);

        // Draw background
        graphics.drawScaledImage(Assets.bg_example, 0, 0, screenW, screenH,
                0, 0, 700, 1270);

        enemy.drawAsOpponent(game, graphics);
        player.drawAsPlayer(game, graphics, currentRound);
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
        player.removeAllBuffs();
        game.setScreen(new MenuScreen(game));
    }

    public void handlePlayerInput(List<TouchEvent> touchEvents)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();

        for (int i = 0; i < touchEvents.size(); i++)
        {
            TouchEvent touchEvent = touchEvents.get(i);
            if (touchEvent.type == TouchEvent.TOUCH_UP)
            {
                for (int j = 0; j < 6; j++)
                {
                    if (Math.inBoundary(touchEvent,
                            (screenW / 2) - Assets.ui_skillSlot.getWidth() * (3 - j),
                            (int) (screenH * 0.87), 128, 128))
                    {
                        if (this.player.getSkillOnSlot(j).isReady()
                                && this.player.hasEnoughManaFor(this.player.getSkillOnSlot(j)))
                        {
                            this.player.cast(j, currentRound, this.enemy);
                            this.currentRound++;
                        }
                    }
                }
            }
        }
    }
}
