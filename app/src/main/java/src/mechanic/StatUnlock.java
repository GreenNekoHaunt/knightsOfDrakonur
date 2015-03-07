package src.mechanic;

import com.kod.knightsofdrakonur.framework.Game;

import src.entity.Player;
import src.entity.Role;
import src.skills.Skill;

/**
 * Created by GreenyNeko on 06.02.2015.
 */
public class StatUnlock
{
    private static int fights;
    private static int kills;
    private static int deaths;
    private static int level;
    private static int ascensions;

    public static void increaseLevelCounter()
    {
        StatUnlock.level++;
    }

    public static void increaseFightCounter()
    {
        StatUnlock.fights++;
    }

    public static void increaseAscensions()
    {
        StatUnlock.ascensions++;
    }

    public static void increaseKills()
    {
        StatUnlock.kills++;
    }

    public static void increaseDeaths()
    {
        StatUnlock.deaths++;
    }

    public static void updateUnlocks(Game game)
    {
        Player player;
        for(int i = 0; i < 2; i++)
        {
            if(i == 1)
            {
                player = game.getCurrentPlayer2();
            }
            else
            {
                player = game.getCurrentPlayer();
            }

            if(player.getLevel() == 2)
            {
                player.learnSkill(Skill.sandwich);
                player.learnSkill(Skill.clearMind);
            }
            else if(player.getLevel() == 4)
            {
                if(!player.hasLearnedSkill(Skill.heavyStrike)) player.learnSkill(Skill.heavyStrike);
                if(!player.hasLearnedSkill(Skill.arrowShot)) player.learnSkill(Skill.arrowShot);
                if(!player.hasLearnedSkill(Skill.fireball)) player.learnSkill(Skill.fireball);
                if(!player.hasLearnedSkill(Skill.icicles)) player.learnSkill(Skill.icicles);
                if(!player.hasLearnedSkill(Skill.boltCharge)) player.learnSkill(Skill.boltCharge);
                if(!player.hasLearnedSkill(Skill.throwRocks)) player.learnSkill((Skill.throwRocks));
            }
            else if(player.getLevel() == 6)
            {
                player.learnSkill(Skill.aimedShot);
            }
            else if(player.getLevel() == 7)
            {
                player.learnSkill(Skill.tripleShot);
            }
            else if(player.getLevel() == 9)
            {
                player.learnSkill(Skill.entanglement);
            }

            if(player.getAscensions() == 1)
            {
                player.learnSkill(Skill.scratch);
                player.learnSkill(Skill.scrollOfAir);
            }
            else if(player.getAscensions() == 2)
            {
                player.learnSkill(Skill.bite);
                player.learnSkill(Skill.scrollOfEarth);
            }
            else if(player.getAscensions() == 3)
            {
                player.learnSkill(Skill.scrollOfWater);
            }
            else if(player.getAscensions() == 4)
            {
                player.learnSkill(Skill.scrollOfFire);
            }
        }
    }
}
