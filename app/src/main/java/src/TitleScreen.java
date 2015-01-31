package src;

/**
 * Created by GreenyNeko on 11.12.2014.
 */

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.widget.EditText;

import com.kod.knightsofdrakonur.framework.Game;
import com.kod.knightsofdrakonur.framework.Graphics;
import com.kod.knightsofdrakonur.framework.Screen;
import com.kod.knightsofdrakonur.framework.Input.TouchEvent;

import src.entity.Player;
import src.entity.Role;
import util.LocaleStringBuilder;
import util.Math;

public class TitleScreen extends Screen
{
    public int ticks = 0;
    private String labelTapScreen;
    private String labelLogin;
    private String labelRegister;
    private String labelServer;
    private String labelUsername;
    private String labelPassword;
    private EditText textFieldServer;
    private EditText textFieldUsername;
    private EditText textFieldPassword;
    private boolean showLogin;
    private boolean showRegister;
    private Role selectedRole;

    public TitleScreen(Game game)
    {
        super(game);
        selectedRole = Role.WARRIOR;
        labelTapScreen = (new LocaleStringBuilder(game)).addLocaleString("lang.title.ui.tapScreen")
                        .finalizeString();
        labelRegister = (new LocaleStringBuilder(game)).addLocaleString("lang.ui.register")
                .finalizeString();
        labelLogin = (new LocaleStringBuilder(game)).addLocaleString("lang.ui.login")
                .finalizeString();
        labelServer = (new LocaleStringBuilder(game)).addLocaleString("lang.ui.server")
                .addString(":").finalizeString();
        labelUsername = (new LocaleStringBuilder(game)).addLocaleString("lang.ui.username")
                .addString(":").finalizeString();
        labelPassword = (new LocaleStringBuilder(game)).addLocaleString("lang.ui.password")
                .addString(":").finalizeString();
        // Try preparing the looper
        try
        {
            Looper.prepare();
        }
        catch(RuntimeException e)
        {
            // Looper is already prepared. Continue.
        }
        textFieldServer = new EditText((Context)game);
        textFieldUsername = new EditText((Context)game);
        textFieldPassword = new EditText((Context)game);

    }

    @Override
    /* Called whenever the activity updates.
     *
     * @param float deltaTime - the time that has passed.
     */
    public void update(float deltaTime)
    {
        int screenH = game.getGraphics().getHeight(), screenW = game.getGraphics().getWidth();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(int i = 0; i < touchEvents.size(); i++)
        {
            TouchEvent touchEvent = touchEvents.get(i);
            if(touchEvent.type == TouchEvent.TOUCH_UP)
            {
                if(!this.showRegister && !this.showLogin)
                {
                    if (Math.inBoundary(touchEvent, 0, 0, screenW, screenH))
                    {
                        this.showLogin = true;
                    }
                }
                else if(this.showLogin)
                {
                    /*            graphics.drawString(this.labelRegister, screenW / 4, (int)(screenH * 0.9), textStyle);
            graphics.drawString(this.labelLogin, screenW - screenW / 4, (int)(screenH * 0.9),*/
                    if(Math.inBoundary(touchEvent, screenW / 4, (int)(screenH * 0.9),
                            Math.getTextWidth(this.labelRegister, 32.0f), 32))
                    {
                        this.showLogin = false;
                        this.showRegister = true;
                    }
                    else if(Math.inBoundary(touchEvent, screenW - screenW / 4, (int)(screenH * 0.9),
                            Math.getTextWidth(this.labelLogin, 32.0f), 32))
                    {
                        Player player = new Player(Role.WARRIOR, "Player1");
                        Player player2 = new Player(Role.WARRIOR, "Player2");
                        game.setCurrentPlayer(player);
                        game.setCurrentPlayer2(player2);
                        game.setScreen(new MenuScreen(game));
                    }
                }
                else if(this.showRegister)
                {
                    if(Math.inBoundary(touchEvent, screenW / 4, (int)(screenH * 0.9),
                            Math.getTextWidth(this.labelLogin, 32.0f), 32))
                    {
                        this.showLogin = true;
                        this.showRegister = false;
                    }
                    else if(Math.inBoundary(touchEvent, screenW - screenW / 4, (int)(screenH * 0.9),
                            Math.getTextWidth(this.labelRegister, 32.0f), 32))
                    {
                        Player player = new Player(selectedRole, "Player1");
                        Player player2 = new Player(selectedRole, "Player2");
                        game.setCurrentPlayer(player);
                        game.setCurrentPlayer2(player2);
                        game.setScreen(new MenuScreen(game));
                    }
                    else if(Math.inBoundary(touchEvent, (int)(screenW * 0.1), (int)(screenH * 0.6),
                            Assets.ui_shield.getWidth(), Assets.ui_shield.getHeight()))
                    {
                        selectedRole = Role.WARRIOR;
                    }
                    else if(Math.inBoundary(touchEvent, (int)(screenW * 0.4), (int)(screenH * 0.6),
                            Assets.ui_shield.getWidth(), Assets.ui_shield.getHeight()))
                    {
                        selectedRole = Role.SCOUT;
                    }
                    else if(Math.inBoundary(touchEvent, (int)(screenW * 0.7), (int)(screenH * 0.6),
                            Assets.ui_shield.getWidth(), Assets.ui_shield.getHeight()))
                    {
                        selectedRole = Role.MAGE;
                    }
                }
            }
        }
        ticks++;
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

        Paint textStyle = new Paint();
        textStyle.setARGB(250, 250, 250, 0);
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(32.0f);
        textStyle.setTextLocale(game.getLocale());
        if(!this.showLogin && !this.showRegister)
        {
            if(ticks % 100 < 50)
            {
                graphics.drawString(this.labelTapScreen, (int) (screenW * 0.5), (int) (screenH * 0.9),
                        textStyle);
            }
        }
        else if(this.showLogin)
        {
           // TODO: Draw Window
           // TODO: Database access
            textStyle.setARGB(255, 255, 255, 255);
            graphics.drawString(this.labelServer, screenW / 3, (int)(screenH * 0.2), textStyle);
            // Draw textbox
            graphics.drawTextEdit(textFieldServer, screenW / 2, (int)(screenH * 0.35), 180, 32, textStyle);
            graphics.drawString(this.labelUsername, screenW / 3, (int)(screenH * 0.3), textStyle);
            // Draw textbox
            graphics.drawString(this.labelPassword, screenW / 3, (int)(screenH * 0.4), textStyle);
            // Draw textbox
            graphics.drawString(this.labelRegister, screenW / 4, (int)(screenH * 0.9), textStyle);
            graphics.drawString(this.labelLogin, screenW - screenW / 4, (int)(screenH * 0.9),
                    textStyle);
        }
        else if(this.showRegister)
        {
            // TODO: Draw Window
            // TODO: Character creation
            // TODO: Database access
            textStyle.setARGB(255, 255, 255, 255);
            graphics.drawString(this.labelServer, screenW / 3, (int)(screenH * 0.2), textStyle);
            // Draw textbox
            graphics.drawString(this.labelUsername, screenW / 3, (int)(screenH * 0.3), textStyle);
            // Draw textbox
            graphics.drawString(this.labelPassword, screenW / 3, (int)(screenH * 0.4), textStyle);
            // Draw textbox

            // Choose your class
            graphics.drawString("Warrior", (int)(screenW * 0.2), (int)(screenH * 0.58), textStyle);
            graphics.drawString("Scout", (int)(screenW * 0.5), (int)(screenH * 0.58), textStyle);
            graphics.drawString("Mage", (int)(screenW * 0.8), (int)(screenH * 0.58), textStyle);
            graphics.drawImage(Assets.ui_shield, (int)(screenW * 0.1), (int)(screenH * 0.6));
            graphics.drawImage(Assets.ui_shield, (int)(screenW * 0.4), (int)(screenH * 0.6));
            graphics.drawImage(Assets.ui_shield, (int)(screenW * 0.7), (int)(screenH * 0.6));
            if(selectedRole == Role.WARRIOR)
            {

            }
            else if(selectedRole == Role.SCOUT)
            {

            }
            else if(selectedRole == Role.MAGE)
            {

            }

            graphics.drawString(this.labelLogin, screenW / 4, (int)(screenH * 0.9), textStyle);
            graphics.drawString(this.labelRegister, screenW - screenW / 4, (int)(screenH * 0.9),
                    textStyle);
        }
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
